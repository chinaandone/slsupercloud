package com.clever.api.controller.v10;

import com.clever.api.controller.BaseController;
import com.clever.api.view.ClientAjaxResult;
import com.clever.common.domain.BaseDataInfo;
import com.clever.common.domain.TableType;
import com.clever.common.domain.TableTypeView;
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

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Info: 商户餐桌类型管理接口
 * User: Gary.zhang@clever-m.com
 * Date: 2016-02-04
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@RestController
@Scope("prototype")
@RequestMapping("/v10/tableType")
public class TableTypeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(TableTypeController.class);

    @Autowired
    private TableTypeService tableTypeService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 商户菜品列表
     * @param shopId
     * @return
     */
    @RequestMapping(value = "/list")
    public ClientAjaxResult queryTableTypeList(@RequestParam(value = "shopId") String shopId,
                                              @RequestParam(value = "timestamp", required = false) Long timestamp) {
        logger.info("查询商户餐桌类型信息,shopId:"+shopId);
        try {

            long id = 0l;
            if(StringUtils.isNotEmpty(shopId)){
                id = Long.parseLong(shopId);
            } else {
                return ClientAjaxResult.failed("商户ID不能为空");
            }
            if (timestamp == null) {
                timestamp = 0l;
            }

            BaseDataInfo tableTypes = redisUtil.get("table:type:info:" + shopId, BaseDataInfo.class);
            if (tableTypes!=null) {
                if(timestamp == tableTypes.getTimestamp()){
                    tableTypes.setList(null);
                    tableTypes.setTimestamp(timestamp);
                }
            } else {
                logger.info("query id " + id);
                List<TableTypeView> list = tableTypeService.queryAllTableTypeByShopId(id);
                if (list != null) {
                    tableTypes = new BaseDataInfo();
                    tableTypes.setList(list);
                }
                tableTypes.setTimestamp(new Date().getTime());
                redisUtil.set("table:type:info:" + shopId, tableTypes, 365, TimeUnit.DAYS);
            }

            return ClientAjaxResult.success(tableTypes);
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ClientAjaxResult.failed("糟了...系统出错了...");
        }
    }


}