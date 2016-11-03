package com.clever.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clever.common.client.view.MaterialView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.*;
import com.clever.common.service.*;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.DateTime;
import com.clever.common.view.AjaxResult;
import com.clever.common.view.PaginationView;
import com.clever.web.resource.SessionManager;
import com.clever.web.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Info: clever
 * User: chay@clever-m.com
 * Date: 2016-04-20
 * Time: 18:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/materialPublish")
public class MaterialPublishManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(MaterialPublishManageController.class);

    @Autowired
    private MaterialBusinessManageService materialBusinessManageService;

    @Autowired
    private MaterialPublishManageService materialPublishManageService;

    @Autowired
    private OrgManageService orgManageService;

    /**
     * 木爷视频发布接口
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
        List<MaterialView> materialViewList = null;
        try {
            materialViewList = this.toJavaObjectList(value, MaterialView.class);
        } catch (Exception e) {
            logger.warn("---toObjectList() exception:" + e.getMessage());
            return AjaxResult.failed("参数错误!");
        }

        if(!dataValidate(materialViewList, clientId, orgId)){
            return AjaxResult.failed("已经发布过，请删除后再重新发布!");
        }
        List<Org> orgList = orgManageService.getEntities(new Org(clientId, orgId));
        if(orgList == null || (orgList != null && orgList.size() == 0)){
            return AjaxResult.failed("没有此商家或餐厅!");
        }
        //循环验证每个餐厅下面的发布数量不超过30个
        for(Org org : orgList) {
            PaginationView paginationView = new PaginationView();
            paginationView.loadFilter().put("clientId", org.getClientId());
            paginationView.loadFilter().put("orgId", org.getOrgId());
            int existCount = materialPublishManageService.countExist(paginationView);
            if(existCount >= 30){
                return AjaxResult.failed("当前品牌'"+org.getName()+"'店铺下发布数量不能超过30个，请删除当前品牌某店铺下的推送记录后再推送!");
            }
        }
        Date publishTime = new Date();
        try {
            for(Org org : orgList){
                for(MaterialView vpv : materialViewList){
                    materialPublishManageService.addEntityBySeq(new MaterialPublish(null,
                            vpv.getMaterialBusinessId(), org.getClientId(), org.getOrgId(), publishTime,
                            vpv.getDescription(), null, 1, vpv.getTimeStart(), vpv.getTimeEnd(),user.getUserId(),new Date() ) );
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AjaxResult.failed("出错");
        }
        return AjaxResult.success();
    }

    /**
     * 获取木爷视频发布列表
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
        User user = getUserInfo();
        if(user == null ||  user.getOrgId() == null || user.getOrgId().equals("")){
            return AjaxResult.failed();
        }
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())){
            return AjaxResult.failed("没有权限访问");
        }
        paginationView.loadFilter().put("clientId", clientId);
        paginationView.loadFilter().put("orgId", orgId);
        paginationView.setDomain(SqlDomainNames.MATERIALPUBLISH_LIST);
        materialPublishManageService.listInPage(paginationView);

        try {
            List<MaterialPublishDTView> listData = (List<MaterialPublishDTView>)paginationView.getAaData();
            List<MaterialPublishDTView> resultData = new ArrayList<MaterialPublishDTView>();
            for(MaterialPublishDTView mb : listData){
                mb.setMaterialUse(Constants.materialUseMap.get(mb.getKind())+"");
                resultData.add(mb);
            }
            paginationView.setAaData(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.failed();
        }

        return AjaxResult.success(paginationView);
    }

    /**
     * 删除木爷视频发布的历史
     * @param materialBusinessId
     * @param description
     * @param publishTime
     * @param clientId
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam(value = "materialPublishId", required = false) Long materialPublishId,
                             @RequestParam(value = "materialBusinessId") Long materialBusinessId,
                             @RequestParam(value = "description") String description,
                             @RequestParam(value = "publishTime") Long publishTime,
                             @RequestParam(value = "clientId", required = false) Long clientId,
                             @RequestParam(value = "orgId", required = false) Long orgId){
        User user = getUserInfo();
        if(user == null ||  user.getOrgId() == null || user.getOrgId().equals("")){
            return AjaxResult.failed();
        }
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())){
            return AjaxResult.failed("没有权限访问");
        }
        materialPublishManageService.removeEntity(new MaterialPublish(materialPublishId, materialBusinessId, clientId, orgId, DateTime.toMillis(publishTime), description, null, null, null, null,user.getUserId(),null));
        return AjaxResult.success();
    }

    boolean dataValidate(MaterialView materialView){
        if(materialView == null){
            return false;
        }else if(materialView.getMaterialId() == null){
            return false;
        }else if(materialView.getTimeStart() == null || materialView.getTimeEnd() == null){
            return false;
        }else if(DateTime.toDate(materialView.getTimeEnd()).compareTo(DateTime.toDate(materialView.getTimeStart())) >= 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 转换json为对象
     * @param jsonStr
     * @param clazz
     * @return
     */
    private static List<MaterialView> toJavaObjectList(String jsonStr, Class clazz){
        List<MaterialView> valueList = new ArrayList<MaterialView>();
        JSONArray jsonArray = JSON.parseArray(jsonStr);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject value = (JSONObject) jsonArray.get(i);
            MaterialView obj = null;
            try {
                obj = (MaterialView)JSON.parseObject(value.toString(), clazz);
                valueList.add(obj);
            } catch (Exception e) {
                logger.warn("---toJavaObjectList() exception:" + e.getMessage());
            }
        }
        return valueList;
    }

    /**
     * 验证发布的资源文件，
     * 规则：
     * 1.不能为空；
     * 2.一次性发布资源数量不能大于十个；
     * 3.标题、排序、开始时间、结束时间不能为空且开始时间不能大于结束时间；
     * 4.在数据库有此记录
     * 5.某餐厅有此记录则删掉重新插入
     * @param materialPublishViewList
     * @return
     */
    boolean dataValidate(List<MaterialView> materialPublishViewList, Long clientId, Long orgId){
        boolean flag = true;
        if(materialPublishViewList == null){
            flag = false;
        }
        if(materialPublishViewList.size() == 0 || materialPublishViewList.size() > 10){
            flag = false;
        }
        for(MaterialView vpv : materialPublishViewList){
            if(vpv == null){
                flag = false;
                break;
            }
            if(vpv.getDescription() == null || vpv.getMaterialBusinessId() == null || vpv.getKind() == null){
                flag = false;
                break;
            }
            if( vpv.getTimeStart() != null && vpv.getTimeEnd() != null && DateTime.toMillis(vpv.getTimeEnd()).compareTo(DateTime.toMillis(vpv.getTimeStart())) < 0){
                flag = false;
                break;
            }
            MaterialBusinessDTView rm = (MaterialBusinessDTView)materialBusinessManageService.getEntity(new MaterialBusiness(vpv.getMaterialBusinessId(), null, GlobalConstant.ISMUYERESOURCE));
            if(rm == null){
                flag = false;
                break;
            }
            PaginationView paginationView = new PaginationView();
            paginationView.loadFilter().put("clientId", clientId);
            paginationView.loadFilter().put("orgId", orgId);
            paginationView.loadFilter().put("businessId", vpv.getMaterialBusinessId());
            paginationView.loadFilter().put("description", vpv.getDescription());
            paginationView.setDomain(SqlDomainNames.MATERIALPUBLISH_LIST);
            int existCount = materialPublishManageService.count(paginationView);
            if(existCount > 0){
                flag = false;
                break;
            }
        }
        return flag;
    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }
}
