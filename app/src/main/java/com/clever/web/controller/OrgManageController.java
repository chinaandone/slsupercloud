package com.clever.web.controller;

import com.clever.common.client.view.OrgView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.Client;
import com.clever.common.domain.Org;
import com.clever.common.domain.User;
import com.clever.common.service.ClientManageService;
import com.clever.common.service.OrgManageService;
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
 * User: chay.ni@clever-m.com
 * Date: 2016-03-07
 * Time: 10:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/org")
public class OrgManageController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(OrgManageController.class);

    @Autowired
    private OrgManageService orgManageService;

    @Autowired
    private ClientManageService clientManageService;

    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(@ModelAttribute("orgView") OrgView orgView){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null){
            return AjaxResult.failed();
        }
        //1.只有超级用户/JF权限可以新增店铺
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                && !GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            return AjaxResult.failed("没有权限");
        }
        if(orgView.getClientId() == null || orgView.getClientId().equals("")){
            return AjaxResult.failed("店铺所属品牌不能为空");
        }
        if(orgView.getName() == null || orgView.getName().equals("")){
            return AjaxResult.failed("店铺名称不能为空");
        }
        if(orgView.getPhone() == null || orgView.getPhone().equals("")){
            return AjaxResult.failed("店铺电话不能为空");
        }
        try{
            Client c = (Client)clientManageService.getEntity(new Client(orgView.getClientId(),null));
            if(c == null){
                return AjaxResult.failed("店铺所属品牌参数有误...");
            }
            if(getOrgNumByName(orgView.getName()) > 0){
                return AjaxResult.failed("已有该名称，请更换名称");
            }
            orgView.setActive(1);//新建默认active=1
            orgManageService.addEntityBySeq(new Org(orgView,user));
        } catch (Exception e) {
            logger.error("保存店铺信息异常", e);
            return AjaxResult.failed("保存店铺信息异常");
        }
        return AjaxResult.success();
    }

    private int getOrgNumByName(String name) {
        PaginationView paginationView = new PaginationView();
        paginationView.loadFilter().put("orgName",name);
        paginationView.setDomain(SqlDomainNames.ORG_LIST);
        int c = orgManageService.count(paginationView);
        return c;
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@ModelAttribute("orgView") OrgView orgView){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null){
            return AjaxResult.failed();
        }
        //1.只有超级用户/JF权限可以修改店铺
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                && !GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            return AjaxResult.failed("没有权限");
        }
        if(orgView.getName() == null || orgView.getName().equals("")){
            return AjaxResult.failed("店铺名称不能为空");
        }
        if(orgView.getPhone() == null || orgView.getPhone().equals("")){
            return AjaxResult.failed("店铺电话不能为空");
        }
        try {
            Org oTemp = (Org)orgManageService.getEntity(new Org(null,orgView.getOrgId()));
            if(oTemp == null){
                return AjaxResult.failed("要修改的店铺信息不存在");
            }
            if(!oTemp.getClientId().equals(orgView.getClientId())){
                return AjaxResult.failed("店铺品牌信息不能修改");
            }
            //修改时，只有用户修改了当前对象名称时，再验证是否重名。否则认为用户没有修改当前对象的名称,不做重名验证。
            if(getOrgNumByName(orgView.getName()) > 0 && !oTemp.getName().equals(orgView.getName())){
                return AjaxResult.failed("已有该名称，请更换名称");
            }
//            orgView.setActive(1);//默认店铺也是资源库，一直有效，不能删除，只能修改
            orgManageService.updateEntity(new Org(orgView,user));
        } catch (Exception e) {
            logger.error(e.toString());
            return AjaxResult.failed();
        }
        return AjaxResult.success();
    }

//    @RequestMapping(value = "/remove")
//    @ResponseBody
//    public AjaxResult remove(@RequestParam(value = "orgId") Long orgId){
//        Org o = (Org)orgManageService.getEntity(new Org(null,orgId));
//        if(o == null){
//            return AjaxResult.failed();
//        }
//        //不直接删除品牌client的详细信息，只是把active设为0，表示不启用是不是更好？？？？
//        orgManageService.removeEntity(o);
//        return AjaxResult.success();
//    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public AjaxResult get(@RequestParam(value = "orgId") Long orgId){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null || user.getOrgId().equals("")){
            return AjaxResult.failed();
        }
        Long clientId = null;
        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                || GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            //木爷权限/服务JF权限使用外部传入的clientId和orgId
        }else if(GlobalConstant.ROLL_BRANDUSER.equals(user.getRoleType())){
            //品牌账号使用自身的品牌clientId
            clientId = user.getClientId();
        }else if(GlobalConstant.ROLL_CLIENTUSER.equals(user.getRoleType())){
            //店铺账号使用自身的店铺orgId
            clientId = user.getClientId();
            orgId = user.getOrgId();
        }else{
            return AjaxResult.failed("没有权限访问");
        }

        Org o = (Org)orgManageService.getEntity(new Org(clientId,orgId));

        return AjaxResult.success(o);
    }

    /**
     * 分页/不分页获取list
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

        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                || GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            //木爷权限/服务JF权限使用外部传入的clientId和orgId
        }else if(GlobalConstant.ROLL_BRANDUSER.equals(user.getRoleType())){
            //品牌账号使用自身的品牌clientId
            clientId = user.getClientId();
        }else if(GlobalConstant.ROLL_CLIENTUSER.equals(user.getRoleType())){
            //店铺账号使用自身的店铺orgId
            clientId = user.getClientId();
            orgId = user.getOrgId();
        }else{
            return AjaxResult.failed("没有权限访问");
        }

        paginationView.loadFilter().put("clientId", clientId);
        paginationView.loadFilter().put("orgId", orgId);
        paginationView.setIDisplayAll(paginationView.getIDisplayAll());
        paginationView.loadFilter().put("iDisplayAll", paginationView.getIDisplayAll() );
        paginationView.setDomain(SqlDomainNames.ORG_LIST);
        orgManageService.listInPage(paginationView);
        return AjaxResult.success(paginationView);
    }


    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }

    /**
     *
     * 根据orgId店铺ID和clientId品牌ID查询所有店铺详细信息
     * @param clientId 公共过滤关键字1，要查询的品牌id
     * @param orgId 公共关键字2，要查询的店铺id
     * @return AjaxResult对象，含有paginationView对象，含有数据
     */
//    @RequestMapping(value = "/list")
//    @ResponseBody
//    public AjaxResult list(@RequestParam(value = "clientId",required = false)Long clientId,
//                           @RequestParam(value = "orgId",required = false)Long orgId){
//        User user = getUserInfo();
//        if(user == null || user.getOrgId() == null || user.getOrgId().equals("")){
//            return AjaxResult.failed();
//        }
//
//        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
//                || GlobalConstant.ROLL_JF.equals(user.getRoleType())){
//            //木爷权限/服务JF权限使用外部传入的clientId和orgId
//        }else if(GlobalConstant.ROLL_BRANDUSER.equals(user.getRoleType())){
//            //品牌账号使用自身的品牌clientId
//            clientId = user.getClientId();
//        }else if(GlobalConstant.ROLL_CLIENTUSER.equals(user.getRoleType())){
//            //店铺账号使用自身的店铺orgId
//            clientId = user.getClientId();
//            orgId = user.getOrgId();
//        }else{
//            return AjaxResult.failed("没有权限访问");
//        }
//
//        List<Org> orgList= orgManageService.getEntities(new Org(clientId, orgId));
//
//        if(orgList == null || orgList.size() == 0 ){
//            return AjaxResult.failed("没有可显示的店铺");
//        }
//
//        return AjaxResult.success(orgList,"店铺数据获取成功");
//    }
}
