package com.clever.web.controller;

import com.clever.common.client.view.ClientView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.Client;
import com.clever.common.domain.User;
import com.clever.common.service.ClientManageService;
import com.clever.common.sql.SqlDomainNames;
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
 * Date: 2016-03-07
 * Time: 10:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/client")
public class ClientManageController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(ClientManageController.class);

    @Autowired
    private ClientManageService clientManageService;


    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(@ModelAttribute("clientView") ClientView clientView){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null){
            return AjaxResult.failed();
        }
        //1.只有超级用户/JF权限可以新增品牌
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                && !GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            return AjaxResult.failed("没有权限");
        }
        if(clientView.getName() == null || clientView.getName().equals("")){
            return AjaxResult.failed("品牌名称不能为空");
        }
        if(getClientNumByName(clientView.getName()) > 0){
            return AjaxResult.failed("已有该名称，请更换名称");
        }
        try{
            clientView.setActive(1);//新建默认active=1
            clientManageService.addEntityBySeq(new Client(clientView,user));
        } catch (Exception e) {
            logger.error("保存品牌信息异常", e);
            return AjaxResult.failed();
        }
        return AjaxResult.success();
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@ModelAttribute("clientView") ClientView clientView){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null){
            return AjaxResult.failed();
        }
        //1.只有超级用户/JF权限可以修改品牌
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                && !GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            return AjaxResult.failed("没有权限");
        }
        if(clientView.getName() == null || clientView.getName().equals("")){
            return AjaxResult.failed("品牌名称不能为空");
        }
        try {
            Client c = (Client)clientManageService.getEntity(new Client(clientView.getClientId(),null));
            if(c == null){
                return AjaxResult.failed("要修改的品牌信息不存在");
            }
            //修改时，只有用户修改了当前对象名称时，再验证是否重名。否则认为用户没有修改当前对象的名称,不做重名验证。
            if(getClientNumByName(clientView.getName()) > 0 && !c.getName().equals(clientView.getName())){
                return AjaxResult.failed("已有该名称，请更换名称");
            }
//            clientView.setActive(1);//默认品牌也是资源库，一直有效，不能删除，只能修改
            clientManageService.updateEntity(new Client(clientView,user));
        } catch (Exception e) {
            logger.error(e.toString());
            return AjaxResult.failed();
        }
        return AjaxResult.success();
    }

//    @RequestMapping(value = "/remove")
//    @ResponseBody
//    public AjaxResult remove(@RequestParam(value = "clientId") Long clientId){
//        Client client = (Client)clientManageService.getEntity(new Client(clientId,null));
//        if(client == null){
//            return AjaxResult.failed();
//        }
//        //不直接删除品牌client的详细信息，只是把active设为0，表示不启用是不是更好？？？？
////        clientManageService.removeEntity(client);
//        return AjaxResult.success();
//    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public AjaxResult get(@RequestParam(value = "clientId") Long clientId){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null || user.getOrgId().equals("")){
            return AjaxResult.failed();
        }
        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                || GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            //木爷权限/服务JF权限使用外部传入的clientId和orgId
        }else if(GlobalConstant.ROLL_BRANDUSER.equals(user.getRoleType())
                || GlobalConstant.ROLL_CLIENTUSER.equals(user.getRoleType()) ){
            //品牌/店铺账号使用自身的品牌clientId
            clientId = user.getClientId();
        }else{
            return AjaxResult.failed("没有权限访问");
        }
        Client client = (Client)clientManageService.getEntity(new Client(clientId,null));
        return AjaxResult.success(client);
    }


    /**
     * 分页/不分页显示品牌list
     * @param paginationView
     * @param clientId
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult listPage(@ModelAttribute("paginationView") PaginationView paginationView,
                               @RequestParam(value = "clientId",required = false)Long clientId,
                               @RequestParam(value = "orgId",required = false)Long orgId,
                               @RequestParam(value = "searchValue",required = false)Long searchValue){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null || user.getOrgId().equals("")){
            return AjaxResult.failed();
        }

        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                || GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            //木爷权限/服务JF权限使用外部传入的clientId和orgId
        }else if(GlobalConstant.ROLL_BRANDUSER.equals(user.getRoleType())
                || GlobalConstant.ROLL_CLIENTUSER.equals(user.getRoleType()) ){
            //品牌/店铺账号使用自身的品牌clientId
            clientId = user.getClientId();
        }else{
            return AjaxResult.failed("没有权限访问");
        }

        paginationView.loadFilter().put("clientId", clientId);
        paginationView.loadFilter().put("orgId", orgId);
        paginationView.setDomain(SqlDomainNames.CLIENT_LIST);
        paginationView.setIDisplayAll(paginationView.getIDisplayAll());
        paginationView.loadFilter().put("iDisplayAll", paginationView.getIDisplayAll() );
        paginationView.loadFilter().put("searchValue", searchValue );
        clientManageService.listInPage(paginationView);
        return AjaxResult.success(paginationView);
    }

    private int getClientNumByName(String name){
        PaginationView paginationView = new PaginationView();
        paginationView.loadFilter().put("clientName",name);
        paginationView.setDomain(SqlDomainNames.CLIENT_LIST);
        int c = clientManageService.count(paginationView);
        return c;
    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }

    /**
     *
     * 根据clientId品牌ID查询品牌详细信息
     * @param clientId 公共过滤关键字1，要查询的品牌id
     * @return AjaxResult对象，含有paginationView对象，含有数据
     */
//    @RequestMapping(value = "/list")
//    @ResponseBody
//    public AjaxResult list(@RequestParam(value = "clientId",required = false)Long clientId){
//        User user = getUserInfo();
//        if(user == null || user.getOrgId() == null || user.getOrgId().equals("")){
//            return AjaxResult.failed();
//        }
//
//        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
//                || GlobalConstant.ROLL_JF.equals(user.getRoleType())){
//            //木爷权限/服务JF权限使用外部传入的clientId和orgId
//        }else if(GlobalConstant.ROLL_BRANDUSER.equals(user.getRoleType())
//                || GlobalConstant.ROLL_CLIENTUSER.equals(user.getRoleType()) ){
//            //品牌/店铺账号使用自身的品牌clientId
//            clientId = user.getClientId();
//        }else{
//            return AjaxResult.failed("没有权限访问");
//        }
//
//        List<Client> clientList = clientManageService.getEntities(new Client(clientId,null));
//
//        if(clientList == null || clientList.size() == 0 ){
//            return AjaxResult.failed("没有可显示的品牌");
//        }
//
//        return AjaxResult.success(clientList,"品牌数据获取成功");
//    }

}
