package com.clever.api.controller.v10;

import com.clever.api.controller.BaseController;
import com.clever.api.view.ClientAjaxResult;
import com.clever.common.domain.MenuInfoView;
import com.clever.common.service.MenuManageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User: enva.liang@clever-m.com
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@Scope("prototype")
@RequestMapping("/v10/forward")
public class ForwardManageController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ForwardManageController.class);

    /**
     * 代驾重定向
     * @return
     */
    @RequestMapping(value = "/driveCar")
    public String driveCar(){
        return "redirect:http://page.kuaidadi.com/m/dj.html?cmpid=aad10qov";
    }

}