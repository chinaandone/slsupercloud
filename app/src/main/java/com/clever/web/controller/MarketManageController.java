package com.clever.web.controller;

import com.alibaba.fastjson.JSON;
import com.clever.common.client.view.MarketView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.*;
import com.clever.common.service.*;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.*;
import com.clever.common.view.AjaxResult;
import com.clever.common.view.PaginationView;
import com.clever.web.resource.SessionManager;
import com.clever.web.util.Constants;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.output.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-07-26
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/market")
public class MarketManageController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(MarketManageController.class);

    @Autowired
    private OrgManageService orgManageService;

    @Autowired
    private MarketManageService marketManageService;

    @Autowired
    private PictrueManageService pictrueManageService;
    /**
     * 增加推广记录
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(@RequestParam(value = "dataJson") String dataJson, Long orgId, Long clientId) {
//        if (orgId == null || "".equals(orgId)) {
//            return AjaxResult.failed("请切换要推广的门店!");
//        }
        User user = getUserInfo();
        if (user == null || (user != null && user.getOrgId() == null)) {
            return AjaxResult.failed();
        }
        MarketView marketView = null;
        try {
            marketView = this.toJavaObject(dataJson, MarketView.class);
        } catch (Exception e) {
            logger.warn("---toObjectList() exception:" + e.getMessage());
            return AjaxResult.failed("json解析错误!");
        }
        if (!dataValidate(marketView, user)) {
            return AjaxResult.failed("一次不能发布超过十五个以上监控图片，上画时间不能小于下画时间");
        }

        try {
//            Org org = (Org) orgManageService.getEntity(new Org(clientId, orgId));
//            if (org == null) {
//                return AjaxResult.failed("参数错误，门店不存在");
//            }
            List<Org> orgList = orgManageService.getEntities(new Org(clientId, orgId));
            for(Org org:orgList){
                Market market = new Market(marketView, user);
                market.setClientId(org.getClientId());
                market.setOrgId(org.getOrgId());
                //从后台查询该店铺下点点笔的数量，更新到market对象....

                Long lastInsertId = marketManageService.addEntityBySeq(market);

                if (marketView.getMonitors() != null) {
                    for (MarketMonitor marketMonitor : marketView.getMonitors()) {
                        marketMonitor.setMarketId(lastInsertId);
                        marketManageService.addMarketMonitorEntity(marketMonitor);
                    }
                }
            }

        } catch (Exception e) {
            logger.error("保存推广信息异常", e);
            return AjaxResult.failed("保存推广信息异常");
        }
        return AjaxResult.success();
    }


    /**
     * 编辑本店及木爷资源活动
     *
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@RequestParam(value = "dataJson") String dataJson) {
        User user = getUserInfo();
        if (user == null || (user != null && user.getOrgId() == null)) {
            return AjaxResult.failed();
        }
        MarketView marketView = null;
        try {
            marketView = this.toJavaObject(dataJson, MarketView.class);
        } catch (Exception e) {
            logger.warn("---toObjectList() exception:" + e.getMessage());
            return AjaxResult.failed("json解析错误!");
        }
        if (MyStringUtils.isBlank(TypeConverter.toString(marketView.getMarketId()))) {
            return AjaxResult.failed("编辑传入参数错误!");
        }
        MarketView marketOld = (MarketView) marketManageService.getEntity(new Market(marketView.getMarketId()));
        if (marketOld == null) {
            return AjaxResult.failed("修改对象错误");
        }
        if (!dataValidate(marketView, user)) {
            return AjaxResult.failed("一次不能发布超过十五个以上监控图片，上画时间不能小于下画时间,监控照片不能为空");
        }

        try {
            Market market = new Market(marketView, user);
            market.setUpdated(new Date());
            market.setUpdatedBy(user.getUserId());
            //更新market对象
            marketManageService.updateEntity(market);

            //更新关联的监控图片
            if (marketView.getMonitors() != null) {
                if (!compareList(marketOld.getMonitors(), marketView.getMonitors())) {//当比较不相同时再更新
                    //先删除所有关联图片关系
                    marketManageService.deleteMarketMonitorByMarket(new Market(marketOld, null));
                    //再插入新的关联关系
                    for (MarketMonitor marketMonitor : marketView.getMonitors()) {
                        marketMonitor.setMarketId(marketOld.getMarketId());
                        marketManageService.addMarketMonitorEntity(marketMonitor);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("保存推广信息异常", e);
            return AjaxResult.failed("保存推广信息异常");
        }
        return AjaxResult.success();
    }

    /**
     * 队列比较，比较两个队列是否相等
     *
     * @param <T>
     * @param a
     * @param b
     * @return
     */
    public static <T extends Comparable<T>> boolean compareList(List<MarketMonitor> a, List<MarketMonitor> b) {
        if (a.size() != b.size())
            return false;
        Collections.sort(a);
        Collections.sort(b);
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).getPictrueId().equals(b.get(i).getPictrueId()))
                return false;
        }
        return true;
    }

    /**
     * 本店活动和木爷资源列表
     *
     * @param paginationView
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list(@ModelAttribute("paginationView") PaginationView paginationView,
                           @RequestParam(value = "company", required = false) String company) {
        User user = getUserInfo();
        if (user == null || (user != null && user.getOrgId() == null)) {
            return AjaxResult.failed();
        }
        if (!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) && !GlobalConstant.ROLL_JF.equals(user.getRoleType())) {
            return AjaxResult.failed("没有权限访问");
        }
        if (company != null && !"".equals(company)) {
            paginationView.loadFilter().put("company", company);
        }
        paginationView.loadFilter().put("iDisplayAll", paginationView.getIDisplayAll() );
        paginationView.setDomain(SqlDomainNames.MARKET_LIST);
        marketManageService.listInPage(paginationView);

        //把字段id替换成名称,把时间转成时间戳
        List<MarketView> result = (List<MarketView>) paginationView.getAaData();
        List<MarketView> resultReturn = new ArrayList<MarketView>();
        if (result != null && result.size() > 0) {
            for (MarketView marketView : result) {
                marketView.setAdLengthName("" + Constants.adLengthMap.get(marketView.getAdLength()));
                marketView.setMediaFormName("" + Constants.mediaFormMap.get(marketView.getMediaForm()));
                marketView.setPlayFreqName("" + Constants.playFreqMap.get(marketView.getPlayFreq()));
                marketView.setTimeStart(marketView.getTimeStartDate().getTime());
                marketView.setTimeEnd(marketView.getTimeEndDate().getTime());
                resultReturn.add(marketView);
            }
        }
        paginationView.setAaData(resultReturn);

        return AjaxResult.success(paginationView);
    }

    /**
     * 删除木爷资源及本店活动
     *
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam(value = "marketId") Long marketId) {
        User user = getUserInfo();
        if (user == null || (user != null && user.getOrgId() == null)) {
            return AjaxResult.failed();
        }
        MarketView marketView = (MarketView) marketManageService.getEntity(new Market(marketId));
        if (marketView != null && marketView.getMarketId() != null) {
            marketManageService.deleteMarketMonitorByMarket(new Market(marketView, null));
            marketManageService.removeEntity(new Market(marketId));
        } else {
            return AjaxResult.failed("删除出错");
        }
        return AjaxResult.success();
    }

    public static MarketView toJavaObject(String jsonStr, Class clazz) throws Exception {
        MarketView javaObj = null;
        if (jsonStr == null || jsonStr.toString() == null) {
            return null;
        }
        javaObj = (MarketView) JSON.parseObject(jsonStr, clazz);
        return javaObj;
    }

    /**
     * 1.不能为空
     * 2.详细不能超过15个
     * 3.开始时间和结束时间必须输入且开始时间必须小于等于结束时间(木爷资源可以不输入)
     *
     * @param user
     * @return
     */
    boolean dataValidate(MarketView marketView, User user) {
        boolean flag = false;
        if (user == null) {
            return false;
        }
        if (GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())) {
            flag = true;
        }
        if (marketView == null) {
            return false;
        } else if (marketView != null && marketView.getMonitors() != null && (marketView.getMonitors().size() > 15 /*|| marketView.getMonitors().size() < 1*/)) {
            return false;
        } else if (!flag && (marketView.getTimeStart() == null || marketView.getTimeEnd() == null)) {
            return false;
        } else if (!flag && (DateTime.toMillis(marketView.getTimeEnd()).compareTo(DateTime.toMillis(marketView.getTimeStart())) < 0)) {
            return false;
        } else {
            return true;
        }

    }

    User getUserInfo() {
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }

}


