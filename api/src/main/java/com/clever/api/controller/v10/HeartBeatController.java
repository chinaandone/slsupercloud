package com.clever.api.controller.v10;

import com.clever.api.controller.BaseController;
import com.clever.api.view.ClientAjaxResult;
import com.clever.common.domain.BaseDataInfo;
import com.clever.common.domain.TableInfo;
import com.clever.common.domain.TableTypeView;
import com.clever.common.service.TableInfoService;
import com.clever.common.service.TableManageService;
import com.clever.common.service.TableTypeService;
import com.clever.common.util.redis.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
@RequestMapping("/v10/heartbeat")
public class HeartBeatController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(HeartBeatController.class);

    @Autowired
    private TableManageService tableManageService;



    /**
     * 返回状态值
     * @param tableId
     * @return
     */
    @RequestMapping(value = "/beatinfo")
    public ClientAjaxResult queryTableInfoList(@RequestParam(value = "tableId") Long tableId,
                                               @RequestParam(value = "orgId") Long orgId,
                                              @RequestParam(value = "versioncode") String versioncode,
                                               @RequestParam(value = "versionname") String versionname,
                                               @RequestParam(value = "timestamp",required=false) String timestamp) {
        logger.info("更新商户餐桌开机在线信息,tableId:"+ tableId);
        try {

            long id = tableId;
            String updated = null;
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            if(timestamp!=null){
                updated=timestamp;
            }else{
//                updated = Calendar.getInstance().getTime().toLocaleString();
                updated = sdf.format(Calendar.getInstance().getTime());
            }
            Map<String,String> map = new HashMap<String,String>();
            map.put("orgId",Long.toString(orgId));
            map.put("tableId",Long.toString(tableId));
            map.put("versioncode",versioncode);
            map.put("versionname",versionname);
            map.put("runflag","1");
            map.put("updated",updated);
            tableManageService.updateEntity(map);
            return ClientAjaxResult.success();
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ClientAjaxResult.failed("糟了...系统出错了...");
        }
    }


}