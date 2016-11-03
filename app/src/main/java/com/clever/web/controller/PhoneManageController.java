package com.clever.web.controller;


import com.clever.common.domain.ComputerInfo;
import com.clever.common.domain.InfoPhone;
import com.clever.common.service.MonitorManageService;
import com.clever.common.service.PhoneManageService;
import com.clever.common.view.AjaxResultObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Info: （大学士）手环管理类
 * User: dido.deng@clever-m.com
 * Date: 2016-04-18
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/phone")
public class PhoneManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(PhoneManageController.class);

    @Autowired
    private PhoneManageService phoneManageService;



    /**
     * 根据shopId获取所有的手环
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResultObj queryComputerInfoList(@RequestParam("orgId") long orgId) {
        logger.info("开始查询信息");
        try {
            InfoPhone infoPhone = new InfoPhone();
            infoPhone.setOrgId(orgId);
            List<InfoPhone> infoPhoneList= phoneManageService.getEntities(infoPhone);
            if(infoPhoneList == null || infoPhoneList.size() == 0 ){
                return AjaxResultObj.failed("没有可显示的手环");
        }
            return AjaxResultObj.success(infoPhoneList,"获取手环查询成功！","xxxx","Cooky");
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AjaxResultObj.failed("糟了...系统出错了...");
        }
    }
}
