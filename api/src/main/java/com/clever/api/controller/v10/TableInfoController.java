package com.clever.api.controller.v10;

import com.clever.api.controller.BaseController;
import com.clever.api.view.ClientAjaxResult;
import com.clever.common.domain.BaseDataInfo;
import com.clever.common.domain.TableInfo;
import com.clever.common.domain.TableTypeView;
import com.clever.common.service.TableInfoService;
import com.clever.common.service.TableTypeService;
import com.clever.common.util.redis.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.TimedSemaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Info: 商户餐桌管理接口
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-20
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@RestController
@Scope("prototype")
@RequestMapping("/v10/table")
public class TableInfoController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(TableInfoController.class);

    @Autowired
    private TableInfoService tableInfoService;
    @Autowired
    private TableTypeService tableTypeService;
    @Autowired
    private RedisUtil redisUtil;


    /**
     * 商户餐桌列表
     * @param shopId
     * @return
     */
    @RequestMapping(value = "/list")
    public ClientAjaxResult queryTableInfoList(@RequestParam(value = "shopId") String shopId,
                                              @RequestParam(value = "timestamp", required = false) Long timestamp) {
        logger.info("查询商户餐桌信息,shopId:"+shopId);
        try {

            long id = 0l;
            if(StringUtils.isNotEmpty(shopId)){
                id = Long.parseLong(shopId);
            } else {
                return ClientAjaxResult.failed("商户ID不能为空");
            }
            if(timestamp==null){
                timestamp = 0l;
            }

            //餐桌信息从redis取，如果没取到，则去数据库取并写入redis
            BaseDataInfo tableInfos = redisUtil.get("shop:table:info:"+shopId, BaseDataInfo.class);
            if (tableInfos!=null){
                if(timestamp == tableInfos.getTimestamp()){
                    tableInfos.setList(null);
                    tableInfos.setList2(null);
                    tableInfos.setTimestamp(timestamp);
                }
            } else {
                List<TableInfo> list = tableInfoService.queryAllTableInfoByShopId(id);
                List<TableTypeView> tableTypes = tableTypeService.queryAllTableTypeByShopId(id);
                if (list != null) {
                    tableInfos = new BaseDataInfo();
                    tableInfos.setList(list);
                    tableInfos.setList2(tableTypes);
                }
                tableInfos.setTimestamp(new Date().getTime());
                redisUtil.set("shop:table:info:" + shopId, tableInfos, 365, TimeUnit.DAYS);
            }

            Map<Object,Object> entity = new HashMap<Object,Object>();
            entity.put("timestamp",tableInfos.getTimestamp());
            entity.put("list",tableInfos.getList());
            entity.put("tableTypeList",tableInfos.getList2());
            return ClientAjaxResult.success(entity);
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ClientAjaxResult.failed("糟了...系统出错了...");
        }
    }


}