package com.clever.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clever.common.client.view.AdvertisementView;
import com.clever.common.client.view.RollDetailView;
import com.clever.common.client.view.RollMainSeqView;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/rollDetail")
public class RollDetailManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RollDetailManageController.class);

    @Autowired
    private RollDetailManageService rollDetailManageService;

    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam(value = "rollDetailId") Long rollDetailId){
        RollDetail rollDetail = (RollDetail)rollDetailManageService.getEntity(new RollDetail(null, null, rollDetailId));
        if(rollDetail == null){
            return AjaxResult.failed();
        }
        rollDetailManageService.removeEntity(new RollDetail(null, rollDetail.getRollMainId(), rollDetailId));
        return AjaxResult.success();
    }

}
