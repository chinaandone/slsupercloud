package com.clever.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clever.common.client.view.AdvertisementView;
import com.clever.common.client.view.RollDetailView;
import com.clever.common.client.view.RollMainSeqView;
import com.clever.common.client.view.RollMainView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.*;
import com.clever.common.service.*;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.DateTime;
import com.clever.common.util.FastJsonUtil;
import com.clever.common.util.MyStringUtils;
import com.clever.common.util.TypeConverter;
import com.clever.common.view.AjaxResult;
import com.clever.common.view.PaginationView;
import com.clever.web.resource.SessionManager;
import com.clever.web.util.Constants;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.clever.common.util.FastJsonUtil.*;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/rollMain")
public class RollMainManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RollMainManageController.class);

    @Autowired
    private RollMainManageService rollMainManageService;

    @Autowired
    private DetailTextManageService detailTextManageService;

    @Autowired
    private RollDetailManageService rollDetailManageService;

    @Autowired
    private PictrueManageService pictrueManageService;

    /**
     * 增加本店及木爷资源活动
     * @param rollMainViewJson
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(@RequestParam(value = "rollMainViewJson") String rollMainViewJson){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        RollMainView rollMainView = null;
        try {
            rollMainView = this.toJavaObject(rollMainViewJson, RollMainView.class);
        } catch (Exception e) {
            logger.warn("---toObjectList() exception:" + e.getMessage());
            return AjaxResult.failed("json解析错误!");
        }
        if(!dataValidate(rollMainView, user)){
            return AjaxResult.failed("一次不能发布超过十五个以上详细图片");
        }
        if(rollMainView.getRollDetailViewList() == null ||
                (rollMainView.getRollDetailViewList() != null && rollMainView.getRollDetailViewList().size() == 0)){
            return AjaxResult.failed("轮播详细不能为空!");
        }
        try {
            Pictrue p = (Pictrue) pictrueManageService.getEntity(new Pictrue(rollMainView.getPictrueId()));
            if (p == null) {
                return AjaxResult.failed("图片资源不能为空");
            }
            RollMain rollMain = new RollMain(rollMainView, user);
            rollMain.setType(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) ? 1 : 0);
            if (addValidate(user)) {
                rollMainManageService.addEntityBySeq(rollMain);
            } else {
                return AjaxResult.failed("超过最多个数，请删除其他专区再保存!");
            }
            DetailText detailText = new DetailText(rollMainView, rollMain, user);
            List<DetailText> dtList = detailTextManageService.getEntities(detailText);
            if (dtList != null && dtList.size() > 0) {
                return AjaxResult.failed("数据有误，DetailText表里已存在!");
            } else {
                detailTextManageService.addEntityBySeq(detailText);
            }
            if (!saveOrUpdateDetailList(rollMainView.getRollDetailViewList(), rollMain, detailText, user)) {
                return AjaxResult.failed();
            }
        }catch (Exception e){
            logger.error("保存轮播信息异常", e);
            return AjaxResult.failed("保存轮播信息异常");
        }
        return AjaxResult.success();
    }


    /**
     * 编辑本店及木爷资源活动
     * @param rollMainViewJson
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@RequestParam(value = "rollMainViewJson") String rollMainViewJson){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        RollMainView rollMainView = null;
        try {
            rollMainView = this.toJavaObject(rollMainViewJson, RollMainView.class);
        } catch (Exception e) {
            logger.warn("---toObjectList() exception:" + e.getMessage());
            return AjaxResult.failed("json数据错误!");
        }
        if(MyStringUtils.isBlank(TypeConverter.toString(rollMainView.getRollMainId()))){
            return AjaxResult.failed("编辑传入参数错误!");
        }
        if(!dataValidate(rollMainView, user)){
            return AjaxResult.failed("一次不能发布超过十五个以上详细图片");
        }
        try {
            Pictrue p = (Pictrue) pictrueManageService.getEntity(new Pictrue(rollMainView.getPictrueId()));
            if (p == null) {
                return AjaxResult.failed("图片资源不能为空");
            }
            rollMainView.setType(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) ? 1 : 0);
            RollMain rollMain = new RollMain(rollMainView, user);
            DetailText detailText = new DetailText(rollMainView, rollMain, user);
            List<DetailText> dtList = detailTextManageService.getEntities(detailText);
            if(dtList != null && dtList.size() > 0 && dtList.get(0) != null && dtList.get(0).getDetailTextId() > 0) {
                detailText.setDetailTextId(dtList.get(0).getDetailTextId());
                detailTextManageService.updateEntity(detailText);
            }
            if (!saveOrUpdateDetailList(rollMainView.getRollDetailViewList(), rollMain, detailText, user)) {
                return AjaxResult.failed("保存详细轮播图片出错");
            }
            rollMainManageService.updateEntity(rollMain);
        }catch (Exception e){
            logger.error("保存轮播信息异常", e);
            return AjaxResult.failed("保存轮播信息异常");
        }
        return AjaxResult.success();
    }

    /**
     * 修改本店活动资排序，木爷不使用此方法，发布的时候已经排序好
     * @param rollMainSeqViewJson
     * @return
     */
    @RequestMapping(value = "/orderSeq/edit")
    @ResponseBody
    public AjaxResult orderSeqEdit(@RequestParam(value = "rollMainSeqViewJson") String rollMainSeqViewJson){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        List<RollMainSeqView> rollMainSeqViewList = null;
        try {
            rollMainSeqViewList = toJavaObjectList(rollMainSeqViewJson, RollMainSeqView.class);
            if (rollMainSeqViewList == null){
                return AjaxResult.failed();
            }
            for(RollMainSeqView view : rollMainSeqViewList){
                RollMain rollMain = new RollMain();
                if(view.getRollMainId() == null || view.getOrderSeq() == null){
                    continue;
                }
                rollMain.setRollMainId(view.getRollMainId());
                rollMain.setOrderSeq(view.getOrderSeq());
                RollMain rm = (RollMain)rollMainManageService.getEntity(rollMain);
                if(rm == null){
                    return AjaxResult.failed("没有需要修改的活动");
                }
                if(TypeConverter.toString(GlobalConstant.ISMUYERESOURCE).equals(TypeConverter.toString(rm.getType()))){
                    return AjaxResult.failed("木爷发布资源不允许排序");
                }
                if(rm != null){
                    rollMainManageService.updateEntity(rollMain);
                }else{
                    continue;
                }
            }
        } catch (Exception e) {
            logger.warn("---toObjectList() exception:" + e.getMessage());
            return AjaxResult.failed("参数错误!");
        }
        return AjaxResult.success();
    }

    /**
     * 本店活动和木爷资源列表
     * @param paginationView
     * @param clientId
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list(@ModelAttribute("paginationView") PaginationView paginationView,
                                @RequestParam(value = "clientId", required = false) Long clientId,
                                @RequestParam(value = "orgId", required = false) Long orgId,
                                @RequestParam(value = "isStore", required = false) Integer isStore){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())){
            if(isStore != null && isStore.intValue() == 1){//如果查询店铺, 则根据clientId和orgId查询相应店铺的活动，否则查询木爷活动资源
                paginationView.loadFilter().put("type", GlobalConstant.NOTMUYERESOURCE);
                paginationView.loadFilter().put("clientId", clientId);
                paginationView.loadFilter().put("orgId", orgId);
            }else{
                paginationView.loadFilter().put("type", GlobalConstant.ISMUYERESOURCE);
            }
        }else if(GlobalConstant.ROLL_BRANDUSER.equals(user.getRoleType())){
            paginationView.loadFilter().put("clientId", user.getClientId());
            paginationView.loadFilter().put("orgId", orgId);
            paginationView.loadFilter().put("type", GlobalConstant.NOTMUYERESOURCE);
        }else if(GlobalConstant.ROLL_CLIENTUSER.equals(user.getRoleType())){
            paginationView.loadFilter().put("orgId", user.getOrgId());
            paginationView.loadFilter().put("type", GlobalConstant.NOTMUYERESOURCE);
        }else{
            return AjaxResult.failed("没有权限访问");
        }
        paginationView.setDomain(SqlDomainNames.ROLLMAIN_LIST);
        rollMainManageService.listInPage(paginationView);
        return AjaxResult.success(paginationView);
    }

    /**
     * 删除木爷资源及本店活动
     * @param rollMainId
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam(value = "rollMainId") Long rollMainId){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        RollMain rollMain = (RollMain)rollMainManageService.getEntity(new RollMain(null, null, null, null, rollMainId, null));
        if(rollMain != null && rollMain.getRollMainId() != null){
            rollDetailManageService.removeEntity(new RollDetail(null, rollMainId, null));
            detailTextManageService.removeEntity(new DetailText(null, rollMainId));
            rollMainManageService.removeEntity(new RollMain(null, null, null, null, rollMainId, null));
        }
        return AjaxResult.success();
    }

    /**
     * 保存轮播详细轮播图片内容
     * @param rollDetailViewList
     * @param rollMain
     * @param detailText
     * @return
     */
    boolean saveOrUpdateDetailList(List<RollDetailView> rollDetailViewList,
                           RollMain rollMain, DetailText detailText, User user){
        try {
            if (rollDetailViewList != null && rollDetailViewList.size() > 0) {
                for (int i = 0; i < rollDetailViewList.size(); i++) {
                    RollDetailView rollDetailView = rollDetailViewList.get(i);
                    if (rollDetailView == null) {
                        continue;
                    }
                    Pictrue p1 = (Pictrue) pictrueManageService.getEntity(new Pictrue(rollDetailView.getPictrueId()));
                    if (p1 == null) {
                        continue;
                    }
                    List<RollDetail> rd = rollDetailManageService.findList(new RollDetail(rollDetailView, rollMain, detailText, user));
                    rollDetailView.setOrderSeq(i);//排序
                    if(rd != null && rd.size() > 0){
                        rollDetailManageService.updateEntity(new RollDetail(rollDetailView, rollMain, detailText, user));
                    }else{
                        rollDetailManageService.addEntityBySeq(new RollDetail(rollDetailView, rollMain, detailText, user));
                    }
                }
            }
            return true;
        }catch(Exception e){
            logger.error("保存详细异常", e);
            return false;
        }
    }

    /**
     *
     * @param user
     * @return
     */
    boolean addValidate(User user){
        if(user == null){
            return false;
        }
        PaginationView paginationView = new PaginationView();
        paginationView.loadFilter().put("orgId", MyStringUtils.isBlank(TypeConverter.toString(user.getOrgId())) ? null : user.getOrgId());
        paginationView.loadFilter().put("type", GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) ? 1 : 0);
        paginationView.setDomain(SqlDomainNames.ROLLMAIN_LIST);
        int existCount = rollMainManageService.count(paginationView);
        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())){
            return true;
        }else if(existCount < 10){
            return true;
        }else{
            return false;
        }

    }


    private static List<RollMainSeqView> toJavaObjectList(String jsonStr, Class clazz){
        List<RollMainSeqView> valueList = new ArrayList<RollMainSeqView>();
        JSONArray jsonArray = JSON.parseArray(jsonStr);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject value = (JSONObject) jsonArray.get(i);
            RollMainSeqView obj = null;
            try {
                obj = (RollMainSeqView)JSON.parseObject(value.toString(), clazz);
                valueList.add(obj);
            } catch (Exception e) {
                logger.warn("---toJavaObjectList() exception:" + e.getMessage());
            }
        }
        return valueList;
    }

    public static RollMainView toJavaObject(String jsonStr, Class clazz) throws Exception{
        RollMainView javaObj = null;
        if (jsonStr == null || jsonStr.toString() == null) {
            return null;
        }
        javaObj = (RollMainView) JSON.parseObject(jsonStr, clazz);
        return javaObj;
    }

    /**
     * 1.不能为空
     * 2.详细不能超过15个
     * 3.开始时间和结束时间必须输入且开始时间必须小于等于结束时间(木爷资源可以不输入)
     * 4.图片ID不能为空
     * @param rollMainView
     * @param user
     * @return
     */
    boolean dataValidate(RollMainView rollMainView, User user){
        boolean flag = false;
        if(user == null){
            return false;
        }
        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())){
            flag = true;
        }
        if(rollMainView == null){
            return false;
        }else if(rollMainView != null && rollMainView.getRollDetailViewList() != null && rollMainView.getRollDetailViewList().size() > 15){
            return false;
        }else if(!flag && (rollMainView.getStartTime() == null || rollMainView.getEndTime() == null)){
            return false;
        }else if(!flag && DateTime.toMillis(rollMainView.getEndTime()).compareTo(DateTime.toMillis(rollMainView.getStartTime())) < 0){
            return false;
        }else if(rollMainView.getPictrueId() == null){
            return false;
        }else{
            return true;
        }

    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }
}
