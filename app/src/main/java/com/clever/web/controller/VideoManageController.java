package com.clever.web.controller;

import com.clever.common.client.view.VideoView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.User;
import com.clever.common.domain.Video;
import com.clever.common.service.VideoManageService;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.*;
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

import javax.activation.MimetypesFileTypeMap;
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
 * 视频文件的增删改，类似于视频文件资源库(只有视频文件和文件属性)
 */
@Controller
@RequestMapping(value = "/video")
public class VideoManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(VideoManageController.class);

    @Autowired
    private VideoManageService videoManageService;

    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult upload(@RequestParam("file") CommonsMultipartFile file,
                             @RequestParam(value = "qiniuPath") String qiniuPath,
                             @RequestParam(value = "playSecond",required = false) Integer playSecond){
        String type = FileType.getFileType(file);
        if(!type.matches(Constants.OFF_ALLOW_VIDEO)){
            return AjaxResult.failed("格式不正确,只能为rmvb、mp4、avi格式");
        }
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null) || MyStringUtils.isBlank(qiniuPath)){
            return AjaxResult.failed();
        }
        Long tempMillis = System.currentTimeMillis();
        String path = Constants.UPLOAD_VIDEO_PATH
                + TypeConverter.toLong(DateTime.getShortDate())
                + "/" + tempMillis
                + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        File dest = VideoManageController.getResFile(TypeConverter.toLong(user.getOrgId()), path);
        Video video = new Video();
        video.setVideoPath(user.getOrgId() + "/" + path);
        video.setQiniuPath(qiniuPath);
        video.setType(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) ? 1 : 0);
        video.setCreated(new Date());
        video.setCreatedBy(user.getUserId());
        video.setOriginal(file.getOriginalFilename());
        video.setOrgId(user.getOrgId());
        video.setClientId(user.getClientId());
        video.setPlaySecond(playSecond);
        try {
            if (!file.isEmpty()) {
                dest.mkdirs();
                file.transferTo(dest);
                video.setVideoSize(dest.length());
                videoManageService.addEntityBySeq(video);
            }
        } catch (IOException e) {
            logger.error(e.toString());
            return AjaxResult.failed("出错");
        }
        return AjaxResult.success(video);
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@ModelAttribute("videoView") VideoView videoView){
        User user = getUserInfo();
        if(dataValidate(videoView)){
            return AjaxResult.failed("传入参数有误");
        }
        Video video = (Video)videoManageService.getEntity(new Video(user, videoView));
        if(video == null){
            return AjaxResult.failed("数据有误");
        }
        if(videoView.getVideoPath() != null){
            videoView.setVideoPath(videoView.getVideoPath());
        }
        videoView.setType(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) ? 1 : 0);
        Video v = new Video(user, videoView);
        try {
            videoManageService.updateEntity(v);
        } catch (Exception e) {
            logger.error(e.toString());
            return AjaxResult.failed("异常");
        }
        return AjaxResult.success();
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list(@ModelAttribute("paginationView") PaginationView paginationView){
        User user = getUserInfo();
        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())){
            paginationView.loadFilter().put("type", 1);
        }else{
            paginationView.loadFilter().put("type", 0);
        }
        paginationView.loadFilter().put("orgId", MyStringUtils.isBlank(TypeConverter.toString(user.getOrgId())) ? null : user.getOrgId());
        paginationView.loadFilter().put("dayTime", new Date());
        paginationView.setDomain(SqlDomainNames.VIDEO_LIST);
        videoManageService.listInPage(paginationView);
        return AjaxResult.success(paginationView);
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam(value = "videoId") Long videoId){
        Video v = (Video)videoManageService.getEntity(new Video(videoId));
        if(v == null || (v != null && MyStringUtils.isBlank(v.getVideoPath()))){
            return AjaxResult.failed();
        }
        File file = new File(Constants.DOWNLOAD_HOME+"/"+v.getVideoPath());
        boolean flag = FileUtils.deleteQuietly(file);
        videoManageService.removeEntity(new Video(videoId));
        return AjaxResult.success();
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public AjaxResult get(@RequestParam(value = "videoId") Long videoId){
        Video v = (Video)videoManageService.getEntity(new Video(videoId));
        return AjaxResult.success(v);
    }

    boolean dataValidate(VideoView videoView){
        if(videoView == null){
            return false;
        }else if(videoView.getVideoPath() == null){
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

    public static String getMimeType(String fileUrl) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String type = fileNameMap.getContentTypeFor(fileUrl);
        return type;
    }

    static File getResFile(Long orgID, String absPath) {
        return FileUtils.getFile(Constants.DOWNLOAD_HOME, Long.toString(orgID), absPath);
    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }
}
