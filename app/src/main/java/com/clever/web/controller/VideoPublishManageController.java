package com.clever.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clever.common.client.view.VideoView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.*;
import com.clever.common.service.OrgManageService;
import com.clever.common.service.VideoBusinessManageService;
import com.clever.common.service.VideoPublishManageService;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.DateTime;
import com.clever.common.view.AjaxResult;
import com.clever.common.view.PaginationView;
import com.clever.web.resource.SessionManager;
import com.clever.common.domain.VideoPublishView;
import com.clever.web.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/videoPublish")
public class VideoPublishManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(VideoPublishManageController.class);

    @Autowired
    private VideoBusinessManageService videoBusinessManageService;

    @Autowired
    private VideoPublishManageService videoPublishManageService;

    @Autowired
    private OrgManageService orgManageService;

    /**
     * 木爷视频发布接口
     * @param value：发布的资源集合json字符串数组
     * @param clientId：发布到品牌ID
     * @param orgId：发布到餐厅ID
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(@RequestParam(value = "value") String value,
                           @RequestParam(value = "clientId", required = false) Long clientId,
                           @RequestParam(value = "orgId", required = false) Long orgId){
        User user = getUserInfo();
        List<VideoPublishView> videoPublishViewList = null;
        try {
            videoPublishViewList = this.toJavaObjectList(value, VideoPublishView.class);
        } catch (Exception e) {
            logger.warn("---toObjectList() exception:" + e.getMessage());
            return AjaxResult.failed("参数错误!");
        }

        if(!dataValidate(videoPublishViewList, clientId, orgId)){
            return AjaxResult.failed("已经发布过，请删除后再重新发布!");
        }
        List<Org> orgList = orgManageService.getEntities(new Org(clientId, orgId));
        if(orgList == null || (orgList != null && orgList.size() == 0)){
            return AjaxResult.failed("没有此商家或餐厅!");
        }
        //循环验证每个餐厅下面的待机广告视频发布数量不超过30个,点播视频不超过1个。
        for(Org org : orgList) {
            PaginationView paginationView = new PaginationView();
            paginationView.loadFilter().put("clientId", org.getClientId());
            paginationView.loadFilter().put("orgId", org.getOrgId());
            paginationView.loadFilter().put("kind", GlobalConstant.VIDEO_ROLLMAIN);
            int existCount = videoPublishManageService.countExist(paginationView);
            if(existCount >= 30){
                return AjaxResult.failed("当前品牌'"+org.getName()+"'店铺下待机广告视频发布数量不能超过30个，请删除当前品牌某店铺下的推送记录后再推送!");
            }
            paginationView.loadFilter().put("kind", GlobalConstant.VIDEO_DEMAND);
            existCount = videoPublishManageService.countExist(paginationView);
            if(existCount >= 1){
                return AjaxResult.failed("当前品牌'"+org.getName()+"'店铺下点播视频发布数量不能超过1个，请删除推送记录后再推送!");
            }
        }
        Date publishTime = new Date();
        try {
            for(Org org : orgList){
                for(VideoPublishView vpv : videoPublishViewList){
                    videoPublishManageService.addEntityBySeq(new VideoPublish(vpv,org, publishTime));
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return AjaxResult.failed("出错");
        }
        return AjaxResult.success();
    }

    /**
     * 获取木爷视频发布列表
     * @param paginationView
     * @param clientId
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list(@ModelAttribute("paginationView") PaginationView paginationView,
                           @RequestParam(value = "clientId", required = false) Long clientId,
                           @RequestParam(value = "orgId", required = false) Long orgId){
        User user = getUserInfo();
        paginationView.loadFilter().put("clientId", clientId);
        paginationView.loadFilter().put("orgId", orgId);
        paginationView.setDomain(SqlDomainNames.VIDEOPUBLISH_LIST);
        videoPublishManageService.listInPage(paginationView);
        //把播放频次转换成显示名称返回前端
        List<VideoPublish> result = (List<VideoPublish>) paginationView.getAaData();
        List<VideoPublish> resultReturn = new ArrayList<VideoPublish>();
        if (result != null && result.size() > 0) {
            try {
                for (VideoPublish videoPublish : result) {
                    String playFreqName = Constants.playFreqMap.get(videoPublish.getPlayFreq());
                    logger.info(playFreqName);
                    if(null != playFreqName && !"null".equals(playFreqName) && !"".equals(playFreqName) ) {
                        videoPublish.setPlayFreqName(playFreqName);
                    }
                    else {
                        videoPublish.setPlayFreqName("");
                    }
                    resultReturn.add(videoPublish);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        paginationView.setAaData(resultReturn);
        return AjaxResult.success(paginationView);
    }

    /**
     * 删除木爷视频发布的历史
     * @param businessId
     * @param description
     * @param publishTime
     * @param clientId
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam(value = "videoPublishId", required = false) Long videoPublishId,
                             @RequestParam(value = "businessId") Long businessId,
                             @RequestParam(value = "description") String description,
                             @RequestParam(value = "publishTime") Long publishTime,
                             @RequestParam(value = "clientId", required = false) Long clientId,
                             @RequestParam(value = "orgId", required = false) Long orgId){
        videoPublishManageService.removeEntity(new VideoPublish(videoPublishId, businessId, clientId, orgId, DateTime.toMillis(publishTime), description, null, null, null, null));
        return AjaxResult.success();
    }

    boolean dataValidate(VideoView videoView){
        if(videoView == null){
            return false;
        }else if(videoView.getVideoId() == null){
            return false;
        }else if(videoView.getTimeStart() == null || videoView.getTimeEnd() == null){
            return false;
        }else if(DateTime.toDate(videoView.getTimeEnd()).compareTo(DateTime.toDate(videoView.getTimeStart())) >= 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 转换json为对象
     * @param jsonStr
     * @param clazz
     * @return
     */
    private static List<VideoPublishView> toJavaObjectList(String jsonStr, Class clazz){
        List<VideoPublishView> valueList = new ArrayList<VideoPublishView>();
        JSONArray jsonArray = JSON.parseArray(jsonStr);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject value = (JSONObject) jsonArray.get(i);
            VideoPublishView obj = null;
            try {
                obj = (VideoPublishView)JSON.parseObject(value.toString(), clazz);
                valueList.add(obj);
            } catch (Exception e) {
                logger.warn("---toJavaObjectList() exception:" + e.getMessage());
            }
        }
        return valueList;
    }

    /**
     * 验证发布的资源文件，
     * 规则：
     * 1.不能为空；
     * 2.一次性发布资源数量不能大于十个；
     * 3.标题、排序、开始时间、结束时间不能为空且开始时间不能大于结束时间；
     * 4.在数据库有此记录
     * 5.某餐厅有此记录则删掉重新插入
     * @param videoPublishViewList
     * @return
     */
    boolean dataValidate(List<VideoPublishView> videoPublishViewList, Long clientId, Long orgId){
        boolean flag = true;
        if(videoPublishViewList == null){
            flag = false;
        }
        if(videoPublishViewList.size() == 0 || videoPublishViewList.size() > 10){
            flag = false;
        }
        for(VideoPublishView vpv : videoPublishViewList){
            if(vpv == null){
                flag = false;
                break;
            }
            if(vpv.getDescription() == null || vpv.getBusinessId() == null || vpv.getTimeStart() == null || vpv.getTimeEnd() == null || vpv.getKind() == null){
                flag = false;
                break;
            }
            if(DateTime.toMillis(vpv.getTimeEnd()).compareTo(DateTime.toMillis(vpv.getTimeStart())) < 0){
                flag = false;
                break;
            }
            VideoBusiness rm = (VideoBusiness)videoBusinessManageService.getEntity(new VideoBusiness(vpv.getBusinessId(), null, GlobalConstant.ISMUYERESOURCE,GlobalConstant.VIDEO_ROLLMAIN));
            if(rm == null){
                flag = false;
                break;
            }
            PaginationView paginationView = new PaginationView();
            paginationView.loadFilter().put("clientId", clientId);
            paginationView.loadFilter().put("orgId", orgId);
            paginationView.loadFilter().put("businessId", vpv.getBusinessId());
//            paginationView.loadFilter().put("description", vpv.getDescription());
            paginationView.setDomain(SqlDomainNames.VIDEOPUBLISH_LIST);
            int existCount = videoPublishManageService.count(paginationView);
            if(existCount > 0){
                flag = false;
                break;
            }
        }
        return flag;
    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }
}
