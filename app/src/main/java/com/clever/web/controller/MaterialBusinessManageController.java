package com.clever.web.controller;

import com.clever.common.client.view.MaterialView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.*;
import com.clever.common.service.MaterialBusinessManageService;
import com.clever.common.service.MaterialManageService;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.DateTime;
import com.clever.common.util.MyStringUtils;
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
import java.util.List;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-04-20
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 * 视频商业属性的增删改，类似于可用于推送的商业视频资源库(视频文件id+商业属性)
 */
@Controller
@RequestMapping(value = "/materialBusiness")
public class MaterialBusinessManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(MaterialBusinessManageController.class);

    @Autowired
    private MaterialBusinessManageService materialBusinessManageService;

    @Autowired
    private MaterialManageService materialManageService;

    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(@ModelAttribute("materialView") MaterialView materialView){
        User user = getUserInfo();
        if(!dataValidate(materialView, user)){
            return AjaxResult.failed("参数错误，请检查!");
        }
        MaterialDTView material = (MaterialDTView)materialManageService.getEntity(new Material(user, materialView));
        if(material == null){
            return AjaxResult.failed("传入参数有误,没有此素材");
        }
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())){
            PaginationView paginationView = new PaginationView();
            paginationView.loadFilter().put("orgId", user.getOrgId());
            paginationView.loadFilter().put("type", GlobalConstant.NOTMUYERESOURCE);
            paginationView.setDomain(SqlDomainNames.MATERIALBUSINESS_LIST);
            if(materialBusinessManageService.count(paginationView) > 0){
                return AjaxResult.failed("只能发布一个素材，请删除后重新发布!");
            }
        }
        materialView.setType(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) ? 1 : 0);

        MaterialDTView material1 = (MaterialDTView)materialManageService.getEntity(new Material(materialView.getMaterialId()));
        if(material1.getFileKindName().equals("bin/img")){//如果是升级固件，则素材作用kind设置为100
            materialView.setKind(100);
        }
        try {
            materialBusinessManageService.addEntityBySeq(new MaterialBusiness(user, materialView));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AjaxResult.failed("出错");
        }
        return AjaxResult.success();
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@ModelAttribute("materialView") MaterialView materialView){
        User user = getUserInfo();
        if(!dataValidate(materialView, user)){
            return AjaxResult.failed("参数错误，请检查!");
        }
        if(materialView.getMaterialBusinessId() == null){
            return AjaxResult.failed("更新的ID为空");
        }
        MaterialBusinessDTView materialBusiness = (MaterialBusinessDTView)materialBusinessManageService.getEntity(new MaterialBusiness(user, materialView));
        if(materialBusiness == null){
            return AjaxResult.failed("没有需要更新的数据");
        }
        MaterialDTView material = (MaterialDTView)materialManageService.getEntity(new Material(user, materialView));
        if(material == null){
            return AjaxResult.failed("传入参数有误,没有此素材");
        }
        materialView.setType(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) ? 1 : 0);
        try {
            MaterialDTView material1 = (MaterialDTView)materialManageService.getEntity(new Material(materialView.getMaterialId()));
            if(material1.getFileKindName().equals("bin/img")){//如果是升级固件，则素材作用kind设置为100
                materialView.setKind(100);
            }
            materialBusinessManageService.updateEntity(new MaterialBusiness(user, materialView));
        } catch (Exception e) {
            logger.error(e.toString());
            return AjaxResult.failed("异常");
        }
        return AjaxResult.success();
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list(@ModelAttribute("paginationView") PaginationView paginationView,
                           @RequestParam(value = "clientId", required = false) Long clientId,
                           @RequestParam(value = "orgId", required = false) Long orgId,
                           @RequestParam(value = "isStore", required = false) Integer isStore){
        User user = getUserInfo();
        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())){
            if(isStore != null && isStore.intValue() == 1){//如果查询店铺, 则根据clientId和orgId查询相应店铺的素材，否则查询木爷素材资源
                paginationView.loadFilter().put("type", GlobalConstant.NOTMUYERESOURCE);
                paginationView.loadFilter().put("clientId", clientId);
                paginationView.loadFilter().put("orgId", orgId);
            }else{
                paginationView.loadFilter().put("type", GlobalConstant.ISMUYERESOURCE);
            }
        }else if(GlobalConstant.ROLL_BRANDUSER.equals(user.getRoleType())){
            paginationView.loadFilter().put("type", GlobalConstant.NOTMUYERESOURCE);
            paginationView.loadFilter().put("clientId", clientId);
            paginationView.loadFilter().put("orgId", orgId);
        }else if(GlobalConstant.ROLL_CLIENTUSER.equals(user.getRoleType())){
            paginationView.loadFilter().put("type", GlobalConstant.NOTMUYERESOURCE);
            paginationView.loadFilter().put("orgId", user.getOrgId());
        }else{
            return AjaxResult.failed("没有权限访问");
        }
        paginationView.setDomain(SqlDomainNames.MATERIALBUSINESS_LIST);
        materialBusinessManageService.listInPage(paginationView);
        try {
            List<MaterialBusinessDTView> listData = (List<MaterialBusinessDTView>)paginationView.getAaData();
            List<MaterialBusinessDTView> resultData = new ArrayList<MaterialBusinessDTView>();
            for(MaterialBusinessDTView mb : listData){
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

//    @RequestMapping(value = "/remove")
//    @ResponseBody
//    public AjaxResult remove(@RequestParam(value = "materialBusinessId") Long materialBusinessId){
//        MaterialView v = (MaterialView)materialBusinessManageService.getEntity(new MaterialBusiness(materialBusinessId, null, null));
//        if(v == null || (v != null && MyStringUtils.isBlank(v.getMaterialPath()))){
//            return AjaxResult.failed();
//        }
//        materialBusinessManageService.removeEntity(new MaterialBusiness(materialBusinessId));
//        return AjaxResult.success();
//    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public AjaxResult get(@RequestParam(value = "materialBusinessId") Long materialBusinessId){
        MaterialBusinessDTView v = (MaterialBusinessDTView)materialBusinessManageService.getEntity(new MaterialBusiness(materialBusinessId, null, null));
        v.setMaterialUse(Constants.materialUseMap.get(v.getKind())+"");
        return AjaxResult.success(v);
    }

    /**
     * 验证参数正确性
     * 1.素材存在
     * 2.开始时间小于结束时间
     * @param materialView
     * @return
     */
    boolean dataValidate(MaterialView materialView, User user){
        if(materialView == null){
            return false;
        }else if(materialView.getMaterialId() == null){
            return false;
        }else if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) && (materialView.getTimeStart() == null || materialView.getTimeEnd() == null)){
            return false;
        }else if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) && DateTime.toMillis(materialView.getTimeEnd()).compareTo(DateTime.toMillis(materialView.getTimeStart())) < 0){
            return false;
        }else{
            return true;
        }
    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }
}
