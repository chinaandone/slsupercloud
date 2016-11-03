package com.clever.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.*;
import com.clever.common.service.*;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.DateTime;
import com.clever.common.view.AjaxResult;
import com.clever.common.view.PaginationView;
import com.clever.web.resource.SessionManager;
import com.clever.web.view.v10.RollPublishView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/rollPublish")
public class RollPublishManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RollPublishManageController.class);

    @Autowired
    private RollPublishManageService rollPublishManageService;

    @Autowired
    private RollMainManageService rollMainManageService;

    @Autowired
    private OrgManageService orgManageService;

    /**
     * 木爷发布过的活动列表
     * @param paginationView
     * @param clientId
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list(@ModelAttribute("paginationView") PaginationView paginationView,
                                   @RequestParam(value = "clientId", required = false) Long clientId,
                                   @RequestParam(value = "orgId", required = false) Long orgId){
        paginationView.loadFilter().put("clientId", clientId);
        paginationView.loadFilter().put("orgId", orgId);
        paginationView.setDomain(SqlDomainNames.ROLLPUBLISH_LIST);
        rollPublishManageService.listInPage(paginationView);
        return AjaxResult.success(paginationView);
    }

    /**
     * 删除木爷发布过的活动
     * @param rollMainId：资源活动ID
     * @param title：标题
     * @param clientId：品牌ID
     * @param orgId：餐厅ID
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam(value = "rollPublishId", required = false) Long rollPublishId,
                             @RequestParam(value = "rollMainId") Long rollMainId,
                             @RequestParam(value = "title") String title,
                             @RequestParam(value = "publishTime") Long publishTime,
                             @RequestParam(value = "clientId", required = false) Long clientId,
                             @RequestParam(value = "orgId", required = false) Long orgId){
//        List<RollPublish> rollPublishList = rollPublishManageService.getEntities(new RollPublish(clientId, orgId, rollMainId, title, DateTime.toDate(publishTime)));
//        for(RollPublish rp : rollPublishList){
            rollPublishManageService.removeEntity(new RollPublish(rollPublishId, clientId, orgId, rollMainId, title, DateTime.toMillis(publishTime), null, null, null,null));
//        }
        return AjaxResult.success();
    }

    /**
     * 木爷活动发布接口
     * @param value：发布的资源集合json字符串数组
     * @param clientId：发布到品牌ID
     * @param orgId：发布到餐厅ID
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(@RequestParam(value = "value") String value,
                          @RequestParam(value = "clientId", required = false) Long clientId,
                          @RequestParam(value = "orgId", required = false) Long orgId){
        User user = getUserInfo();
        List<RollPublishView> rollPublishViewList = null;
        try {
            rollPublishViewList = this.toJavaObjectList(value, RollPublishView.class);
        } catch (Exception e) {
            logger.warn("---toObjectList() exception:" + e.getMessage());
            return AjaxResult.failed("json数据错误!");
        }

        if(!dataValidate(rollPublishViewList, clientId, orgId)){
            return AjaxResult.failed("排序、上架时间、下架时间必输;下架不能小于上架时间且一次不能发布超过五个以上资源!");
        }
        List<Org> orgList = orgManageService.getEntities(new Org(clientId, orgId));
        if(orgList == null || (orgList != null && orgList.size() == 0)){
            return AjaxResult.failed("没有此商家或餐厅!");
        }
        //循环验证每个餐厅下面的发布数量不超过5个
        for(Org org : orgList) {
            PaginationView paginationView = new PaginationView();
            paginationView.loadFilter().put("clientId", org.getClientId());
            paginationView.loadFilter().put("orgId", org.getOrgId());
            int existCount = rollPublishManageService.countExist(paginationView);
            if(existCount >= 5){
                return AjaxResult.failed("当前品牌'"+org.getName()+"'店铺下发布数量不能超过5个，请删除当前品牌某店铺下的推送记录后再推送!");
            }
        }
        Date publishTime = new Date();
        try {
            for(Org org : orgList){
                for(RollPublishView rpv : rollPublishViewList){
                    rollPublishManageService.addEntityBySeq(new RollPublish(null, org.getClientId(),
                            org.getOrgId(), rpv.getRollMainId(), rpv.getTitle(), publishTime,
                            rpv.getOrderSeq(), DateTime.toMillis(rpv.getStartTime()), DateTime.toMillis(rpv.getEndTime()),user));
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return AjaxResult.failed("出错");
        }
        return AjaxResult.success();
    }

    /**
     * 验证发布的资源文件，
     * 规则：
     * 1.不能为空；
     * 2.资源数量不能大于五；
     * 3.标题、排序、开始时间、结束时间不能为空且开始时间不能大于结束时间；
     * 4.在数据库有此记录
     * 5.某餐厅有此记录则删掉重新插入
     * @param rollPublishViewList
     * @return
     */
    boolean dataValidate(List<RollPublishView> rollPublishViewList, Long clientId, Long orgId){
        boolean flag = true;
        if(rollPublishViewList == null){
            flag = false;
        }
        if(rollPublishViewList.size() == 0 || rollPublishViewList.size() > 5){
            flag = false;
        }
        for(RollPublishView rpv : rollPublishViewList){
            if(rpv == null){
                flag = false;
                break;
            }
            if(rpv.getTitle() == null || rpv.getOrderSeq() == null || rpv.getStartTime() == null || rpv.getEndTime() == null){
                flag = false;
                break;
            }
            if(DateTime.toMillis(rpv.getEndTime()).compareTo(DateTime.toMillis(rpv.getStartTime())) < 0){
                flag = false;
                break;
            }
            if(rpv.getRollMainId() == null){
                flag = false;
                break;
            }
            RollMain rm = (RollMain)rollMainManageService.getEntity(new RollMain(null, null, GlobalConstant.ISMUYERESOURCE, null, rpv.getRollMainId(), null));
            if(rm == null){
                flag = false;
                break;
            }
            PaginationView paginationView = new PaginationView();
            paginationView.loadFilter().put("clientId", clientId);
            paginationView.loadFilter().put("orgId", orgId);
            paginationView.loadFilter().put("rollMainId", rpv.getRollMainId());
            paginationView.loadFilter().put("title", rpv.getTitle());
            paginationView.setDomain(SqlDomainNames.ROLLPUBLISH_LIST);
            int existCount = rollPublishManageService.count(paginationView);
            if(existCount > 0){
                flag = false;
                break;
//                rollPublishManageService.removeEntity(new RollPublish(clientId, orgId, rpv.getRollMainId(), rpv.getTitle(), null, null, null, null));//重复发布的资源是否需要删除
            }
        }
        return flag;
    }

    /**
     * 转换json为对象
     * @param jsonStr
     * @param clazz
     * @return
     */
    private static List<RollPublishView> toJavaObjectList(String jsonStr, Class clazz){
        List<RollPublishView> valueList = new ArrayList<RollPublishView>();
        JSONArray jsonArray = JSON.parseArray(jsonStr);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject value = (JSONObject) jsonArray.get(i);
            RollPublishView obj = null;
            try {
                obj = (RollPublishView)JSON.parseObject(value.toString(), clazz);
                valueList.add(obj);
            } catch (Exception e) {
                logger.warn("---toJavaObjectList() exception:" + e.getMessage());
            }
        }
        return valueList;
    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }
}
