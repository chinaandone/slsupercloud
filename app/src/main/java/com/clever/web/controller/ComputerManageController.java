package com.clever.web.controller;


import com.alibaba.fastjson.JSON;
import com.clever.common.domain.ComputerInfo;
import com.clever.common.domain.bean.*;
import com.clever.common.domain.bean.request.Group;
import com.clever.common.domain.bean.request.Member;
import com.clever.common.service.MonitorManageService;
import com.clever.common.view.AjaxResultObj;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Info: （大学士）管理类
 * User: dido.deng@clever-m.com
 * Date: 2016-04-18
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/computer")
public class ComputerManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ComputerManageController.class);

    @Autowired
    private MonitorManageService monitorManageService;



    /**
     * 根据shopId获取所有的大学士
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResultObj queryComputerInfoList(@RequestParam("orgId") long orgId) {
        logger.info("开始查询信息");
        try {
            ComputerInfo computerInfo = new ComputerInfo();
            computerInfo.setOrgId(orgId);
            List<ComputerInfo> computerList= monitorManageService.getEntities(computerInfo);
            if(computerList == null || computerList.size() == 0 ){
                return AjaxResultObj.success(null,"没有可以显示的大学士！","xxxx","Cooky");
        }
            return AjaxResultObj.success(computerList,"获取大学士查询成功！","xxxx","Cooky");
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }
}
