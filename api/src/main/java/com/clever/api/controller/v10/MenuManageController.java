package com.clever.api.controller.v10;

import com.clever.api.controller.BaseController;
import com.clever.api.view.ClientAjaxResult;
import com.clever.common.domain.BaseDataInfo;
import com.clever.common.domain.MenuDataInfo;
import com.clever.common.domain.MenuInfoView;
import com.clever.common.service.MenuManageService;
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
 * Info: 商户菜品管理接口
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-20
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@RestController
@Scope("prototype")
@RequestMapping("/v10/menu")
public class MenuManageController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(MenuManageController.class);

    @Autowired
    private MenuManageService menuManageService;
    @Autowired
    private RedisUtil redisUtil;


    /**
     * 商户菜品列表
     * @param shopId
     * @return
     */
    @RequestMapping(value = "/list")
    public ClientAjaxResult queryShopMenuList(@RequestParam(value = "shopId",required = false) String shopId,
                                              @RequestParam(value = "timestamp", required = false) long timestamp) {
        logger.info("查询商户菜品信息,shopId:"+shopId+",timestamp:"+timestamp);
        try {

            long id = 0l;
            if(StringUtils.isNotEmpty(shopId)){
                id = Long.parseLong(shopId);
            }
            MenuDataInfo shopMenu = redisUtil.get("shop:menu:info:" + shopId, MenuDataInfo.class);
            if (shopMenu!=null) {
                if(timestamp == shopMenu.getTimestamp()){
                    shopMenu.setList(null);
                    shopMenu.setTimestamp(timestamp);
                }
            } else {
                //如果为空，从数据库中查询数据
                List<MenuInfoView> list = menuManageService.queryAllMenuInfoByShopId(id);
                if (list != null) {
                    shopMenu = new MenuDataInfo();
                    shopMenu.setList(list);
                }
                shopMenu.setTimestamp(new Date().getTime());
                redisUtil.set("shop:menu:info:" + shopId, shopMenu, 365, TimeUnit.DAYS);
            }

            return ClientAjaxResult.success(shopMenu);
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ClientAjaxResult.failed("糟了...系统出错了...");
        }
    }


}