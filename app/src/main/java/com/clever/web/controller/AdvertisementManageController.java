package com.clever.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clever.common.client.view.AdvertisementView;
import com.clever.common.client.view.RollDetailView;
import com.clever.common.client.view.RollMainView;
import com.clever.common.domain.*;
import com.clever.common.service.*;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.MyStringUtils;
import com.clever.common.util.TypeConverter;
import com.clever.common.view.AjaxResult;
import com.clever.common.view.PaginationView;
import com.clever.web.resource.SessionManager;
import com.clever.web.util.Constants;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
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
@RequestMapping(value = "/advertisement")
public class AdvertisementManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(AdvertisementManageController.class);

    @Autowired
    private AdvertisementManageService advertisementManageService;

    @Autowired
    private PictrueManageService pictrueManageService;

    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(@ModelAttribute("advertisementView") AdvertisementView advertisementView){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        if(advertisementView.getPictrueId() == null){
            return AjaxResult.failed();
        }
        try{
            Pictrue p = (Pictrue)pictrueManageService.getEntity(new Pictrue(advertisementView.getPictrueId()));
            if(p == null){
                return AjaxResult.failed("参数有误...");
            }
            advertisementManageService.addEntityBySeq(new Advertisement(advertisementView, user));
        } catch (Exception e) {
            logger.error("保存广告信息异常", e);
            return AjaxResult.failed();
        }
        return AjaxResult.success();
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@ModelAttribute("advertisementView") AdvertisementView advertisementView){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        try {
            Advertisement advertisement = (Advertisement)advertisementManageService.getEntity(new Advertisement(advertisementView.getAdvertisementId()));
            if(advertisement != null){
                advertisementManageService.updateEntity(new Advertisement(advertisementView, null, user));
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return AjaxResult.failed();
        }
        return AjaxResult.success();
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam(value = "advertisementId") Long advertisementId){
        Advertisement ad = (Advertisement)advertisementManageService.getEntity(new Advertisement(advertisementId));
        if(ad == null){
            return AjaxResult.failed();
        }
        advertisementManageService.removeEntity(new Advertisement(advertisementId));
        return AjaxResult.success();
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public AjaxResult get(@RequestParam(value = "advertisementId") Long advertisementId){
        getUserInfo();
        Advertisement ad = (Advertisement)advertisementManageService.getEntity(new Advertisement(advertisementId));
        return AjaxResult.success(ad);
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list(@ModelAttribute("paginationView") PaginationView paginationView){
        paginationView.setDomain(SqlDomainNames.ADVERTISEMENT_LIST);
        advertisementManageService.listInPage(paginationView);
        return AjaxResult.success(paginationView);
    }

    static File getResFile(Long orgID, String absPath) {
        return FileUtils.getFile(Constants.DOWNLOAD_HOME, Long.toString(orgID), absPath);
    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }
}
