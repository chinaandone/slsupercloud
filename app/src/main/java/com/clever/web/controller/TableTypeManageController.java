package com.clever.web.controller;

import com.clever.common.client.view.TableTypeBeanView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.*;
import com.clever.common.service.ClientManageService;
import com.clever.common.service.OrgManageService;
import com.clever.common.service.TableTypeManageService;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.redis.RedisUtil;
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

import java.util.List;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-03-17
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/tableType")
public class TableTypeManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(TableTypeManageController.class);

    @Autowired
    private TableTypeManageService tableTypeManageService;

    @Autowired
    private OrgManageService orgManageService;

    @Autowired
    private ClientManageService clientManageService;
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 新建
     * @param tableTypeView
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(@ModelAttribute("tableTypeView") TableTypeBeanView tableTypeView){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        //只有JF权限账号可以修改桌子
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                && !GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            return AjaxResult.failed("没有权限");
        }
        if(null == tableTypeView.getName()){
            return AjaxResult.failed("桌子类型名称不能为空");
        }
        if(null == tableTypeView.getClientId()){
            return AjaxResult.failed("品牌ID不能为空");
        }
        if(null == tableTypeView.getOrgId()){
            return AjaxResult.failed("店铺ID不能为空");
        }
        try{
            Org o = (Org)orgManageService.getEntity(new Org(tableTypeView.getClientId(),tableTypeView.getOrgId()));
            if(o == null){
                return AjaxResult.failed("所属店铺参数有误...");
            }

            Client c = (Client)clientManageService.getEntity(new Client(tableTypeView.getClientId(),null));
            if(c == null){
                return AjaxResult.failed("所属品牌参数有误...");
            }

            if(getTableTypeNumByName(tableTypeView.getClientId(), tableTypeView.getOrgId(), tableTypeView.getName()) > 0){
                return AjaxResult.failed("已有该名称，请更换名称");
            }
            tableTypeManageService.addEntityBySeq(new TableTypeBean(tableTypeView, user));
        } catch (Exception e) {
            logger.error("保存桌子类型信息异常", e);
            return AjaxResult.failed("保存桌子类型信息异常");
        }
        return AjaxResult.success("保存成功");
    }

    private int getTableTypeNumByName(Long clientId, Long orgId, String name) {
        PaginationView paginationView = new PaginationView();
        paginationView.loadFilter().put("clientId",clientId);
        paginationView.loadFilter().put("orgId",orgId);
        paginationView.loadFilter().put("tableTypeName",name);
        paginationView.setDomain(SqlDomainNames.TABLETYPE_LIST);
        int c = tableTypeManageService.count(paginationView);
        return c;
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@ModelAttribute("tableTypeView") TableTypeBeanView tableTypeView){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        //只有JF权限账号可以修改桌子
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                && !GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            return AjaxResult.failed("没有权限");
        }
        if(null == tableTypeView.getName()){
            return AjaxResult.failed("桌子类型名称不能为空");
        }
        if(null == tableTypeView.getTableTypeId()){
            return AjaxResult.failed("桌子类型ID不能为空");
        }
        try {
            TableTypeBean tTP= (TableTypeBean)tableTypeManageService.getEntity(new TableTypeBean(tableTypeView.getTableTypeId()));
            if(null == tTP){
                return AjaxResult.failed("要修改的桌子类型不存在");
            }
            //修改时，只有用户修改了当前对象名称时，再验证是否重名。否则认为用户没有修改当前对象的名称,不做重名验证。
            if(getTableTypeNumByName(tableTypeView.getClientId(), tableTypeView.getOrgId(), tableTypeView.getName()) > 0
                    && !tTP.getName().equals(tableTypeView.getName())){
                return AjaxResult.failed("已有该名称，请更换名称");
            }
            tableTypeManageService.updateEntity(new TableTypeBean(tableTypeView, user));
            redisUtil.delete("table:type:info:" + tableTypeView.getOrgId());
        } catch (Exception e) {
            logger.error(e.toString());
            return AjaxResult.failed("桌子类型修改失败！");
        }
        return AjaxResult.success("桌子类型修改成功");
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public AjaxResult get(@RequestParam(value = "tableTypeId") Long tableTypeId){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        //只有JF权限账号可以修改桌子电话
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                && !GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            return AjaxResult.failed("没有权限");
        }
        TableTypeBean tTP= (TableTypeBean)tableTypeManageService.getEntity(new TableTypeBean(tableTypeId));
        return AjaxResult.success(tTP);
    }


    /**
     * 分页/不分页获取tableTypeList
     * @param paginationView
     * @param clientId
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult listPage(@ModelAttribute("paginationView") PaginationView paginationView,
                           @RequestParam(value = "clientId",required = false)Long clientId,
                           @RequestParam(value = "orgId",required = false)Long orgId){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null || user.getOrgId().equals("")){
            return AjaxResult.failed();
        }
//        if(GlobalConstant.ROLL_JF.equals(user.getRoleType()) ){
//
//        }else{
//            return AjaxResult.failed("没有权限访问");
//        }
        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                || GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            //木爷权限/服务JF权限使用外部传入的clientId和orgId
        }else if(GlobalConstant.ROLL_BRANDUSER.equals(user.getRoleType())  ){
            //品牌账号使用自身的品牌clientId
            clientId = user.getClientId();
        }else if( GlobalConstant.ROLL_CLIENTUSER.equals(user.getRoleType()) ){
            //店铺账号使用自身的品牌clientId,orgId
            clientId = user.getClientId();
            orgId = user.getOrgId();
        }
        else{
            return AjaxResult.failed("没有权限访问");
        }
        paginationView.loadFilter().put("clientId", clientId);
        paginationView.loadFilter().put("orgId", orgId);
        paginationView.setIDisplayAll(paginationView.getIDisplayAll());
        paginationView.loadFilter().put("iDisplayAll", paginationView.getIDisplayAll() );
        paginationView.setDomain(SqlDomainNames.TABLETYPE_LIST);
        tableTypeManageService.listInPage(paginationView);
        return AjaxResult.success(paginationView);
    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }


//    @RequestMapping(value = "/list")
//    @ResponseBody
//    public AjaxResult list(@RequestParam(value = "clientId",required = false)Long clientId,
//                           @RequestParam(value = "orgId",required = false)Long orgId){
//        User user = getUserInfo();
//        if(user == null || user.getOrgId() == null || user.getOrgId().equals("")){
//            return AjaxResult.failed();
//        }
//        if(!GlobalConstant.ROLL_JF.equals(user.getRoleType()) ){
//            return AjaxResult.failed("没有权限访问");
//        }
//        List<TableTypeBean> tList= tableTypeManageService.getEntities(new TableTypeBean(clientId, orgId));
//
//        if(tList == null || tList.size() == 0 ){
//            return AjaxResult.failed("没有可显示的桌子类型");
//        }
//
//        return AjaxResult.success(tList,"桌子类型获取成功");
//    }
}
