package com.clever.api.controller.v10;

import com.clever.api.controller.BaseController;
import com.clever.api.view.ClientAjaxResult;
import com.clever.common.domain.TableWatch;
import com.clever.common.service.TableManageService;
import com.clever.common.service.TableTypeService;
import com.clever.common.service.TableWatchManageService;
import com.clever.common.util.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Info: 心跳消息
 * User: Randy.zhou@clever-m.com
 * Date: 2016-12-02
 * Time: 10:34
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@RestController
@Scope("prototype")
@RequestMapping("/v10/watch")
public class TableWatchController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(TableWatchController.class);

    @Autowired
    private TableWatchManageService tableWatchManageService;




    /**
     * 返回状态值
     * @param tableId
     * @return
     */
    @RequestMapping(value = "/setinfo")
    public ClientAjaxResult queryTableInfoList(@RequestParam(value = "tableId") Long tableId,
                                               @RequestParam(value = "orgId") Long orgId,
                                               @RequestParam(value = "timestamp",required=false) String timestamp) {
        logger.info("获取本桌的Dongle蓝牙地址和对应手环寻址地址,tableId:"+ tableId);
        try {
            TableWatch tableWatch = new TableWatch();
            tableWatch.setTableId(tableId);
            tableWatch.setOrgId(orgId);

            List<TableWatch> ltableWatch = tableWatchManageService.getEntities(tableWatch);
            if(ltableWatch.size()!=0) {
                Map<String,String> map = new HashMap<String,String>();
                map.put("watchadd",ltableWatch.get(0).getWatchadd());
                map.put("dongleadd",ltableWatch.get(0).getDongleadd());
                map.put("mac_address",ltableWatch.get(0).getMac_address());
                return ClientAjaxResult.success(map);
            }else{
                return ClientAjaxResult.success();
            }
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ClientAjaxResult.failed("糟了...系统出错了...");
        }
    }


}