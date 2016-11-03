package com.clever.api.controller.v10;

import com.clever.api.controller.BaseController;
import com.clever.api.view.ClientAjaxResult;
import com.clever.common.client.view.MaterialView;
import com.clever.common.domain.MaterialBusiness;
import com.clever.common.domain.MaterialBusinessDTView;
import com.clever.common.service.MaterialBusinessManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Info: 视频接口
 * User: chay.ni@clever-m.com
 * Date: 2016-04-22
 * Time: 15:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@RestController
@Scope("prototype")
@RequestMapping("/v10/material")
public class MaterialManageController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(MaterialManageController.class);

    @Autowired
    private MaterialBusinessManageService materialBusinessManageService;

    /**
     * 点点笔素材
     * @param orgId
     * @param type: 0或null只显示商户,1需要显示商业
     * @return
     */
    @RequestMapping(value = "/list")
    public ClientAjaxResult queryMaterialList(@RequestParam(value = "orgId") Long orgId,
                                              @RequestParam(value = "type", required = false) Integer type) {
        logger.info("查询点点笔素材列表,orgId:" + orgId);
        try {
            List<MaterialBusinessDTView> list = materialBusinessManageService.getEntities(new MaterialBusiness(null, orgId, type));
            if(list == null){
                return ClientAjaxResult.success();
            }


            return ClientAjaxResult.success(list);
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ClientAjaxResult.failed("糟了...系统出错了...");
        }
    }

    /**
     * 点点笔固件升级素材
     * @return
     */
    @RequestMapping(value = "/binList")
    public ClientAjaxResult queryBinMaterialList() {
        logger.info("查询点点固件升级素材列表" );
        try {
            //只查找出bin/img文件，且只查找type=1代表是木爷上传的商业素材
            List<MaterialBusinessDTView> list = materialBusinessManageService.getBinEntities(new MaterialBusiness("bin/img"));
            if(list == null){
                return ClientAjaxResult.success();
            }
            return ClientAjaxResult.success(list);
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ClientAjaxResult.failed("糟了...系统出错了...");
        }
    }

}