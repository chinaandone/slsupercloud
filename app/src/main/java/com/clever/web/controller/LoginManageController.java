package com.clever.web.controller;


import com.clever.common.domain.*;
import com.clever.common.service.*;
import com.clever.common.view.AjaxResult;
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

import java.util.List;

import static com.clever.common.util.FastJsonUtil.toJavaObject;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/user")
public class LoginManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(LoginManageController.class);

    @Autowired
    private UserManageService userManageService;

    @RequestMapping(value = "/login")
    @ResponseBody
    public AjaxResult login(@ModelAttribute("user") User user) {
        if(user.getLoginId() == null || user.getPassword() == null){
            return AjaxResult.failed("用户名密码不能为空!");
        }
        User u = (User)userManageService.getEntity(new User(user));
        if(u == null){
            return AjaxResult.failed("用户名密码错误!");
        }else{
            SessionManager.setCurrentOperator(getRequest(), u);
            u.setPassword(null);
        }
        String sessionId = null;
        if(getRequest() != null && getRequest().getSession() != null){
            sessionId = getRequest().getSession().getId();
        }
        return AjaxResult.success(u, sessionId);
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public AjaxResult logout() {
        getRequest().getSession().removeAttribute(Constants.SESSION_USER);
        return AjaxResult.success();
    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
//        User user = new User();//测试用
//        user.setRoleType("CLIENTUSER");
//        user.setOrgId(100L);
//        return user;
    }
}
