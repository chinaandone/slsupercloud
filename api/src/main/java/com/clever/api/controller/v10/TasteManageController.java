package com.clever.api.controller.v10;

import com.clever.api.controller.BaseController;
import com.clever.api.view.ClientAjaxResult;
import com.clever.common.domain.BaseDataInfo;
import com.clever.common.domain.TasteInfo;
import com.clever.common.service.TasteManageService;
import com.clever.common.util.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Info: 小超人菜品口味管理
 * User: Gary.zhang@clever-m.com
 * Date: 2016-02-04
 * Time: 15:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@RestController
@Scope("prototype")
@RequestMapping("/v10/taste")
public class TasteManageController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(TasteManageController.class);

    @Autowired
    private TasteManageService tasteManageService;
    @Autowired
    private RedisUtil redisUtil;


    /**
     * 菜品口味列表
     * @return
     */
    @RequestMapping(value = "/list")
    public ClientAjaxResult queryMenuTasteList(@RequestParam(value = "timestamp", required = false) long timestamp) {
        logger.info("查询菜品口味信息:");
        try {

            //BaseDataInfo baseData = redisUtil.get("system:menu:taste", BaseDataInfo.class);
            //if (baseData == null) {
                //如果为空，从数据库中查询数据

            BaseDataInfo baseData = redisUtil.get("system:menu:taste", BaseDataInfo.class);
            if (baseData!=null) {
                if(timestamp == baseData.getTimestamp()){
                    baseData.setList(null);
                    baseData.setTimestamp(timestamp);
                }
            } else {
                List<TasteInfo> list = tasteManageService.queryAllMenuTaste();
                if (list != null) {
                    baseData = new BaseDataInfo();
                    baseData.setList(list);
                }
                baseData.setTimestamp(new Date().getTime());
                redisUtil.set("system:menu:taste", baseData, 365, TimeUnit.DAYS);
            }

            return ClientAjaxResult.success(baseData);
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ClientAjaxResult.failed("糟了...系统出错了...");
        }
    }


}