package com.clever.web.controller;

import com.clever.common.client.view.OrgView;
import com.clever.common.client.view.UserView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.Client;
import com.clever.common.domain.Org;
import com.clever.common.domain.User;
import com.clever.common.service.ClientManageService;
import com.clever.common.service.OrgManageService;
import com.clever.common.service.UserManageService;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.MyStringUtils;
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

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-03-07
 * Time: 10:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/user")
public class UserManageController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(UserManageController.class);

    @Autowired
    private OrgManageService orgManageService;

    @Autowired
    private ClientManageService clientManageService;

    @Autowired
    private UserManageService userManageService;

    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(@ModelAttribute("userView") UserView userView){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null){
            return AjaxResult.failed();
        }
        //1.只有超级用户/JF权限可以新增品牌或店铺账号
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                && !GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            return AjaxResult.failed("没有权限");
        }
        if(userView.getClientId() == null || userView.getClientId().equals("")){
            return AjaxResult.failed("用户所属品牌不能为空");
        }
        if(userView.getOrgId() == null || userView.getOrgId().equals("")){
            return AjaxResult.failed("用户所属店铺不能为空");
        }
        if(userView.getName() == null || userView.getName().equals("")){
            return AjaxResult.failed("账号显示名称不能为空");
        }
        if(userView.getLoginId() == null || userView.getLoginId().equals("")){
            return AjaxResult.failed("登录账号名称不能为空");
        }
        if(MyStringUtils.isContainChinese(userView.getLoginId())){
            return AjaxResult.failed("登录账号名称不能包含中文");
        }
        if(userView.getPassword() == null || userView.getPassword().equals("")){
            return AjaxResult.failed("登录账号密码不能为空");
        }
        if(MyStringUtils.isContainChinese(userView.getPassword())){
            return AjaxResult.failed("登录账号密码不能包含中文");
        }
        if(userView.getBirthday() == null || userView.getBirthday().equals("")){
            return AjaxResult.failed("请设置生日");
        }
        if( null == userView.getRoleType() ){
            return AjaxResult.failed("请设置权限");
        }
        if(!userView.getRoleType().equals(GlobalConstant.ROLL_BRANDUSER) && !userView.getRoleType().equals(GlobalConstant.ROLL_CLIENTUSER) ){
            return AjaxResult.failed("权限设置错误");
        }
        if(userView.getRoleType().equals(GlobalConstant.ROLL_BRANDUSER)
                && (userView.getOrgId() == null || "".equals(userView.getOrgId()) ) ){
            return AjaxResult.failed("新建品牌账号前必须先有该品牌的店铺");
        }
        try{
            Client c = (Client)clientManageService.getEntity(new Client(userView.getClientId(),null));
            if(c == null){
                return AjaxResult.failed("用户所属品牌参数有误...");
            }
            Org o = (Org) orgManageService.getEntity(new Org(userView.getClientId(),userView.getOrgId()));
            if(o == null){
                return AjaxResult.failed("用户所属店铺参数有误...");
            }

            int countTemp = 1;
            PaginationView paginationView = new PaginationView();
            paginationView.setDomain(SqlDomainNames.USER_LIST);
            paginationView.loadFilter().put("clientId", userView.getClientId());
            //每个品牌或店铺账号都只允许有一个
            if(userView.getRoleType().equals(GlobalConstant.ROLL_BRANDUSER)){
                paginationView.loadFilter().put("roleType", GlobalConstant.ROLL_BRANDUSER);
                countTemp = userManageService.count(paginationView);
            }
            else if(userView.getRoleType().equals(GlobalConstant.ROLL_CLIENTUSER)){
                paginationView.loadFilter().put("orgId",userView.getOrgId());
                paginationView.loadFilter().put("roleType", GlobalConstant.ROLL_CLIENTUSER);
                countTemp = userManageService.count(paginationView);
            }

            if(countTemp >= 1){
                return AjaxResult.failed("每个品牌或店铺账号都只允许有一个");
            }

            if(getUserNumByLoginId(userView.getLoginId()) > 0){
                return AjaxResult.failed("已有该账号名称，请更换");
            }
            userManageService.addEntityBySeq(new User(userView,user));
        } catch (Exception e) {
            logger.error("保存账号信息异常", e);
            return AjaxResult.failed("保存账号信息异常");
        }
        return AjaxResult.success();
    }

    private int getUserNumByLoginId(String loginId) {
        PaginationView paginationView = new PaginationView();
        paginationView.loadFilter().put("loginId",loginId);
        paginationView.setDomain(SqlDomainNames.USER_LIST);
        int c = userManageService.count(paginationView);
        return c;
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@ModelAttribute("userView") UserView userView){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null){
            return AjaxResult.failed();
        }
        //1.只有超级用户/JF权限可以修改用户账号
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                && !GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            return AjaxResult.failed("没有权限");
        }
        if(userView.getName() == null || userView.getName().equals("")){
            return AjaxResult.failed("账号名称不能为空");
        }
        if(userView.getLoginId() == null || userView.getLoginId().equals("")){
            return AjaxResult.failed("登录账号名称不能为空");
        }
        if(MyStringUtils.isContainChinese(userView.getLoginId())){
            return AjaxResult.failed("登录账号名称不能包含中文");
        }
//        if(userView.getPassword() == null || userView.getPassword().equals("")){
//            return AjaxResult.failed("登录账号密码不能为空");
//        }
//        if(MyStringUtils.isContainChinese(userView.getPassword())){
//            return AjaxResult.failed("登录账号密码不能包含中文");
//        }
        if(userView.getBirthday() == null || userView.getBirthday().equals("")){
            return AjaxResult.failed("请设置生日");
        }


        try {
            User uTemp = (User)userManageService.getEntity(new User(userView.getUserId()));
            if(uTemp == null){
                return AjaxResult.failed("要修改的账号信息不存在");
            }
            //修改时，只有用户修改了当前对象名称时，再验证是否重名。否则认为用户没有修改当前对象的名称,不做重名验证。
            if(getUserNumByLoginId(userView.getLoginId()) > 0 && !uTemp.getLoginId().equals(userView.getLoginId())){
                return AjaxResult.failed("已有该账号名称，请更换");
            }

            //交付权限只能查看品牌和店铺账号，不能看超级用户和其他交付账号信息
            if( user.getRoleType().equals(GlobalConstant.ROLL_JF )
                    && !uTemp.getRoleType().equals(GlobalConstant.ROLL_BRANDUSER)
                    && !uTemp.getRoleType().equals(GlobalConstant.ROLL_CLIENTUSER)){
                return AjaxResult.failed("您无权修改");
            }

            //密码不能直接更新
            uTemp.setPassword(null);
            userManageService.updateEntity(new User(userView,user));
        } catch (Exception e) {
            logger.error(e.toString());
            return AjaxResult.failed();
        }
        return AjaxResult.success();
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public AjaxResult get(@RequestParam(value = "userId") Long userId){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null || user.getOrgId().equals("")){
            return AjaxResult.failed();
        }
        Long clientId = null;
        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                || GlobalConstant.ROLL_JF.equals(user.getRoleType())) {
        }
        else {
            return AjaxResult.failed("没有权限访问");
        }

        User u = (User)userManageService.getEntity(new User(userId));
        //密码不显示
        u.setPassword(null);

        //交付权限只能查看品牌和店铺账号，不能看超级用户和其他交付账号信息
        if( user.getRoleType().equals(GlobalConstant.ROLL_JF )
                && !u.getRoleType().equals(GlobalConstant.ROLL_BRANDUSER)
                && !u.getRoleType().equals(GlobalConstant.ROLL_CLIENTUSER)){
            return AjaxResult.failed("您无权查看");
        }

        return AjaxResult.success(u);
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
        }else{
            return AjaxResult.failed("没有权限访问");
        }

        //交付权限只能查看品牌和店铺账号，不能看超级用户和其他交付账号信息
//        if(GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            paginationView.loadFilter().put("isJF", 1);
//        }
        paginationView.loadFilter().put("clientId", clientId);
        paginationView.loadFilter().put("orgId", orgId);
        paginationView.setIDisplayAll(paginationView.getIDisplayAll());
        paginationView.loadFilter().put("iDisplayAll", paginationView.getIDisplayAll() );
        paginationView.setDomain(SqlDomainNames.USER_LIST);
        userManageService.listInPage(paginationView);
        return AjaxResult.success(paginationView);
    }


    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }

}
