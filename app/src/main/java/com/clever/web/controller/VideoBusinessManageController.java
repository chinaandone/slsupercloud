package com.clever.web.controller;

import com.clever.common.client.view.VideoView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.User;
import com.clever.common.domain.Video;
import com.clever.common.domain.VideoBusiness;
import com.clever.common.service.VideoBusinessManageService;
import com.clever.common.service.VideoManageService;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.DateTime;
import com.clever.common.util.FileType;
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
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Date;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 * 视频商业属性的增删改，类似于可用于推送的商业视频资源库(视频文件id+商业属性)
 */
@Controller
@RequestMapping(value = "/videoBusiness")
public class VideoBusinessManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(VideoBusinessManageController.class);

    @Autowired
    private VideoBusinessManageService videoBusinessManageService;

    @Autowired
    private VideoManageService videoManageService;

    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(@ModelAttribute("videoView") VideoView videoView){
        User user = getUserInfo();
        if(!dataValidate(videoView, user)){
            return AjaxResult.failed("参数错误，请检查!");
        }
        Video video = (Video)videoManageService.getEntity(new Video(user, videoView));
        if(video == null){
            return AjaxResult.failed("传入参数有误,没有此视频");
        }
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())){
            PaginationView paginationView = new PaginationView();
            paginationView.loadFilter().put("orgId", user.getOrgId());
            paginationView.loadFilter().put("type", GlobalConstant.NOTMUYERESOURCE);
            paginationView.setDomain(SqlDomainNames.VIDEOBUSINESS_LIST);

            if(videoView.getKind() == null ){
                return AjaxResult.failed("请设置视频类别!");
            }
            if( videoView.getKind() != GlobalConstant.VIDEO_ROLLMAIN && videoView.getKind() != GlobalConstant.VIDEO_DEMAND ){
                return AjaxResult.failed("错误的视频类别!");
            }
            paginationView.loadFilter().put("kind", videoView.getKind());
            if(videoBusinessManageService.count(paginationView) > 0){
                return AjaxResult.failed("只能发布一个点播视频和一个待机视频，请删除后重新发布!");
            }

        }
        videoView.setType(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) ? 1 : 0);
        try {
            videoBusinessManageService.addEntityBySeq(new VideoBusiness(user, videoView));
        } catch (Exception e) {
            logger.error(e.toString());
            return AjaxResult.failed("出错");
        }
        return AjaxResult.success();
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@ModelAttribute("videoView") VideoView videoView){
        User user = getUserInfo();
        if(!dataValidate(videoView, user)){
            return AjaxResult.failed("参数错误，请检查!");
        }
        if(videoView.getBusinessId() == null){
            return AjaxResult.failed("更新的ID为空");
        }
        VideoBusiness videoBusiness = (VideoBusiness)videoBusinessManageService.getEntity(new VideoBusiness(user, videoView));
        if(videoBusiness == null){
            return AjaxResult.failed("没有需要更新的数据");
        }
        Video video = (Video)videoManageService.getEntity(new Video(user, videoView));
        if(video == null){
            return AjaxResult.failed("传入参数有误,没有此视频");
        }
        videoView.setType(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) ? 1 : 0);
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())){
            if(videoView.getKind() == null ){
                return AjaxResult.failed("请设置视频类别!");
            }
            if( videoView.getKind() != GlobalConstant.VIDEO_ROLLMAIN && videoView.getKind() != GlobalConstant.VIDEO_DEMAND ){
                return AjaxResult.failed("错误的视频类别!");
            }

            //只有本店视频存在开始和结束时间
            if( videoView.getTimeStart() == null || videoView.getTimeEnd() == null || DateTime.toMillis(videoView.getTimeEnd()).compareTo(DateTime.toMillis(videoView.getTimeStart())) < 0){
                return AjaxResult.failed("错误的开始时间和结束时间!");
            }
        }
        try {
            videoBusinessManageService.updateEntity(new VideoBusiness(user, videoView));
        } catch (Exception e) {
            logger.error(e.toString());
            return AjaxResult.failed("异常");
        }
        return AjaxResult.success();
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list(@ModelAttribute("paginationView") PaginationView paginationView,
                           @RequestParam(value = "clientId", required = false) Long clientId,
                           @RequestParam(value = "orgId", required = false) Long orgId,
                           @RequestParam(value = "isStore", required = false) Integer isStore){
        User user = getUserInfo();
        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())){
            if(isStore != null && isStore.intValue() == 1){//如果查询店铺, 则根据clientId和orgId查询相应店铺的视频，否则查询木爷视频资源
                paginationView.loadFilter().put("type", GlobalConstant.NOTMUYERESOURCE);
                paginationView.loadFilter().put("clientId", clientId);
                paginationView.loadFilter().put("orgId", orgId);
            }else{
                paginationView.loadFilter().put("type", GlobalConstant.ISMUYERESOURCE);
            }
        }else if(GlobalConstant.ROLL_BRANDUSER.equals(user.getRoleType())){
            paginationView.loadFilter().put("type", GlobalConstant.NOTMUYERESOURCE);
            paginationView.loadFilter().put("clientId", clientId);
            paginationView.loadFilter().put("orgId", orgId);
        }else if(GlobalConstant.ROLL_CLIENTUSER.equals(user.getRoleType())){
            paginationView.loadFilter().put("type", GlobalConstant.NOTMUYERESOURCE);
            paginationView.loadFilter().put("orgId", user.getOrgId());
        }else{
            return AjaxResult.failed("没有权限访问");
        }
        paginationView.setDomain(SqlDomainNames.VIDEOBUSINESS_LIST);
        videoBusinessManageService.listInPage(paginationView);
        return AjaxResult.success(paginationView);
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam(value = "businessId") Long businessId){
        VideoBusiness v = (VideoBusiness)videoBusinessManageService.getEntity(new VideoBusiness(businessId, null, null,null));
        if(v == null || (v != null && MyStringUtils.isBlank(v.getVideoPath()))){
            return AjaxResult.failed();
        }
        videoBusinessManageService.removeEntity(new VideoBusiness(businessId, null, null,null));
        return AjaxResult.success();
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public AjaxResult get(@RequestParam(value = "businessId") Long businessId){
        VideoBusiness v = (VideoBusiness)videoBusinessManageService.getEntity(new VideoBusiness(businessId, null, null,null));
        return AjaxResult.success(v);
    }

    /**
     * 验证参数正确性
     * 1.视频存在
     * 2.开始时间小于结束时间
     * @param videoView
     * @return
     */
    boolean dataValidate(VideoView videoView, User user){
        if(videoView == null){
            return false;
        }else if(videoView.getVideoId() == null){
            return false;
        }else if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) && (videoView.getTimeStart() == null || videoView.getTimeEnd() == null)){
            return false;
        }else if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) && DateTime.toMillis(videoView.getTimeEnd()).compareTo(DateTime.toMillis(videoView.getTimeStart())) < 0){
            return false;
        }else{
            return true;
        }
    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }
}
