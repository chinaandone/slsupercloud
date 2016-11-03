package com.clever.api.controller.v10;

import com.clever.api.controller.BaseController;
import com.clever.api.view.ClientAjaxResult;
import com.clever.common.domain.VersionInfo;
import com.clever.common.service.VersionManageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Info: 小超人版本管理接口
 * User: Gary.zhang@clever-m.com
 * Date: 2016-02-04
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@RestController
@Scope("prototype")
@RequestMapping("/v10/version")
public class VersionManageController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(VersionManageController.class);
    @Autowired
    private VersionManageService versionManageService;

    /**
     * 版本信息查询
     * @return
     */
    @RequestMapping(value = "/query")
    public ClientAjaxResult queryVersionInfo(@RequestParam("shopId") String shopId) {
        logger.info("查询版本信息");
        try {
            if(StringUtils.isEmpty(shopId)){
                return ClientAjaxResult.failed("请输入商户ID");
            }

            Map<String,Object> map = null;
            VersionInfo versionInfo = versionManageService.queryVersionInfo(Integer.parseInt(shopId));
            if(versionInfo != null){
                map = new HashMap<String,Object>();
                map.put("version", versionInfo.getVersion());
                map.put("info", versionInfo.getInfo());
                map.put("url", versionInfo.getUrl());
            }

            return ClientAjaxResult.success(map);
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ClientAjaxResult.failed("糟了...系统出错了...");
        }
    }


}