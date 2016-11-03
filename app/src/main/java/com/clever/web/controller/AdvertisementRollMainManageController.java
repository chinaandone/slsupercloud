package com.clever.web.controller;

import com.clever.common.client.view.AdvertisementRollMainView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.*;
import com.clever.common.service.*;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.DateTime;
import com.clever.common.view.AjaxResult;
import com.clever.common.view.PaginationView;
import com.clever.web.resource.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 *       chay.ni@clever-m.com
 * Date: 2016-01-25
 *       2016-03-02
 * Time: 15:11
 * Version: 1.0
 *          1.1
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/advertMain")
public class AdvertisementRollMainManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(AdvertisementRollMainManageController.class);

    @Autowired
    private AdvertisementRollMainManageService advertisementRollMainManageService;

    @Autowired
    private AdvertisementManageService advertisementManageService;

    @Autowired
    private OrgManageService orgManageService;

    /**
     * 根据广告名和推送时间批量删除一次群推行为
     * @param title 广告名
     * @param created 推送时间
     * @return AjaxResult
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam(value = "title") String title,
                             @RequestParam(value = "created") Long created){
        User user = getUserInfo();
        if(user == null ||  user.getOrgId() == null || user.getOrgId().equals("")){
            return AjaxResult.failed();
        }
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())){
            return AjaxResult.failed("没有权限访问");//只有木爷权限才能删除
        }
        if( title == null || title.equals("")){
            return AjaxResult.failed("广告名不能为空");
        }
        if( created == null || created.equals("") ){
            return AjaxResult.failed("推送时间不能为空");
        }
        Date createdTemp=null;
        try{
            createdTemp = DateTime.toMillis(created);
        }catch (Exception e){
            e.printStackTrace();
            AjaxResult.failed("传入创建时间数据有误");
        }

        AdvertisementRollMain advertisementRollMain = new AdvertisementRollMain(null,null,title,createdTemp);
        //因为我们查询getEntities最后是group by title和created两个关键字的，所以先查询再循环删除的方法是错误的
        //正确的方法是MySQL中直接根据广告名title和推送详细时间created直接批量删除

        advertisementRollMainManageService.removeEntity(advertisementRollMain);

        return AjaxResult.success("删除成功");
    }

    /**
     * 保存广告推送详细记录
     * @param advertisementRollMainView 前端传来的广告推送信息
     * @param clientId 公共过滤关键字1，要推送的品牌id
     * @param orgId 公共关键字2，要推送的店铺id
     * @return AjaxResult
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(@ModelAttribute("advertisementRollMainView") AdvertisementRollMainView advertisementRollMainView,
                           @RequestParam(value = "clientId",required = false)Long clientId,
                           @RequestParam(value = "orgId",required = false)Long orgId){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null || user.getOrgId().equals("")){
            return AjaxResult.failed();
        }
        if(advertisementRollMainView.getAdvertisementId() == null){
            return AjaxResult.failed("未选择广告");
        }
        if(advertisementRollMainView.getTimeEnd() == null){
            return AjaxResult.failed("未设置结束时间");
        }
        if(advertisementRollMainView.getTimeStart() == null){
            return AjaxResult.failed("未设置开始时间");
        }
        if(DateTime.toMillis(advertisementRollMainView.getTimeEnd()).compareTo(DateTime.toMillis(advertisementRollMainView.getTimeStart())) < 0){
            return AjaxResult.failed("结束时间不能小于开始时间");
        }
        if(advertisementRollMainView.getOrderSeq() == null){
            return AjaxResult.failed("未设置优先级");
        }
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())){
            return AjaxResult.failed("没有权限访问");//只有木爷权限才能推送
        }
        try{
            //先判断要推的广告是否存在，存在再推
            Advertisement advertisement = (Advertisement)advertisementManageService.getEntity(
                    new Advertisement(advertisementRollMainView.getAdvertisementId()));
            if(advertisement == null){
                return AjaxResult.failed("广告不存在...");
            }
            //判断这条广告是不是已经推送过了，推送过了，就不让推了
            PaginationView paginationView = new PaginationView();
            paginationView.loadFilter().put("clientId", clientId);
            paginationView.loadFilter().put("orgId", orgId);
            paginationView.loadFilter().put("advertisementId", advertisementRollMainView.getAdvertisementId());
            paginationView.loadFilter().put("title", advertisement.getTitle());
            paginationView.setDomain(SqlDomainNames.ADVERTISEMENTROLLMAIN_LIST);
            int count = advertisementRollMainManageService.count(paginationView);

            if(count > 0){
                return AjaxResult.failed("已经对相同店铺或品牌推送过相同标题、图片的广告了...");
            }

            /**
             * 1.若广告存在，则优先根据“店铺id”orgId判断是否要推店铺，
             * 2.若店铺id不存在，则再根据“品牌id”client是否存在来判断是否要推某品牌下的所有店铺
             * 3.若品牌id也不存在，则全推所有店铺，从表org中查询所有店铺信息
             * 综合起来，mysql语句已经会根据orgId和clientId为不为null去返回不同情况的结果，我这里已经不需要再去区分情况了
             */
            //根据以上3种情况获取推送店铺列表
            Org org1 = new Org(clientId,orgId);
            List<Org> orgList = orgManageService.getEntities(org1);
            //根据该列表结果依次插入广告推送详细记录
            if(null == orgList
                    || orgList.size() <= 0){
                AjaxResult.failed("该条件下无店铺可推...");
            }
            //对org读取到的数据列表进行orgId是否为空的校验，其实orgId是表的主键，应该不会为空
            for (Org org : orgList){
                if ( org == null
                        || org.getOrgId() <= 0){
                    return AjaxResult.failed("该条件下某店铺数据有误...");
                }
            }
            //循环验证每个餐厅下面的发布数量不超过5个
            for(Org org : orgList) {
                int existCount = advertisementRollMainManageService.countExist(paginationView);
                if(existCount >= 5){
                    return AjaxResult.failed("当前品牌'"+org.getName()+"'店铺下发布数量不能超过5个，请删除当前品牌某店铺下的推送记录后再推送!");
                }
            }
            //校验orgId都存在，进行插入广告推送详细记录
            Date bandDate = new Date();//同一批次推送的广告推送创建时间相同
            for (Org org : orgList){
                advertisementRollMainManageService.addEntityBySeq(
                        new AdvertisementRollMain(advertisementRollMainView,
                                advertisement,
                                user,
                                org.getOrgId(),
                                org.getClientId(),
                                bandDate));
            }


        } catch (Exception e) {
            logger.error("保存推送广告详细信息异常", e);
            return AjaxResult.failed("保存推送广告详细信息异常");
        }
        return AjaxResult.success("保存成功");
    }

    /**
     *
     * 查询广告推送详细记录
     * @param paginationView 前端传来的封装好的paginationView对象,含有数据
     * @param clientId 公共过滤关键字1，要推送的品牌id
     * @param orgId 公共关键字2，要推送的店铺id
     * @return AjaxResult对象，含有paginationView对象，含有数据
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list(@ModelAttribute("paginationView") PaginationView paginationView,
                           @RequestParam(value = "clientId",required = false)Long clientId,
                           @RequestParam(value = "orgId",required = false)Long orgId){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null || user.getOrgId().equals("")){
            return AjaxResult.failed();
        }
        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())){
            paginationView.loadFilter().put("clientId", clientId);
            paginationView.loadFilter().put("orgId", orgId);
        }else{
            return AjaxResult.failed("没有权限访问");
        }

        paginationView.setDomain(SqlDomainNames.ADVERTISEMENTROLLMAIN_LIST);
        advertisementRollMainManageService.listInPage(paginationView);
        return AjaxResult.success(paginationView);
    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }
}
