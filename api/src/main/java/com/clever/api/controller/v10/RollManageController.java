package com.clever.api.controller.v10;

import com.clever.api.controller.BaseController;
import com.clever.api.view.ClientAjaxResult;
import com.clever.common.domain.*;
import com.clever.common.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Info: 点点笔优惠专区轮播接口
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 15:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@RestController
@Scope("prototype")
@RequestMapping("/v10")
public class RollManageController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(RollManageController.class);

    @Autowired
    private RollMainManageService rollMainManageService;

    @Autowired
    private DetailTextManageService detailTextManageService;

    /**
     * 轮播主图列表
     * @param orgId
     * @param type: 0或null：只显示商户,1：商户和商业的都需要显示
     * @return
     */
    @RequestMapping(value = "/roll/main/list")
    public ClientAjaxResult queryRollMainList(@RequestParam(value = "orgId") Long orgId,
                                              @RequestParam(value = "type", required = false) Integer type) {
        logger.info("查询轮播主图列表,orgId:" + orgId);
        try {
            List<RollMain> list = rollMainManageService.getEntities(new RollMain(null, orgId, type, null, null, null));
            return ClientAjaxResult.success(list);
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ClientAjaxResult.failed("糟了...系统出错了...");
        }
    }

//    /**
//     * 轮播详细列表
//     * @param orgId
//     * @return
//     */
//    @RequestMapping(value = "/roll/detail/list")
//    public ClientAjaxResult queryRollDetailList(@RequestParam(value = "orgId") Long orgId,
//                                                @RequestParam(value = "rollMainId") Long rollMainId) {
//        logger.info("查询轮播详细列表,rollMainId:" + rollMainId);
//        try {
//            DetailText text = (DetailText)detailTextManageService.getEntity(new DetailText(orgId, rollMainId));
//            return ClientAjaxResult.success(text);
//        }  catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getMessage(), e);
//            return ClientAjaxResult.failed("糟了...系统出错了...");
//        }
//    }

}