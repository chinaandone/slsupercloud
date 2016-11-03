package com.clever.web.controller;

import com.clever.common.client.view.MaterialView;
import com.clever.common.client.view.VideoView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.Material;
import com.clever.common.domain.MaterialDTView;
import com.clever.common.domain.User;
import com.clever.common.domain.Video;
import com.clever.common.service.MaterialManageService;
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
 * User: chay.ni@clever-m.com
 * Date: 2016-04-19
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 * 视频文件的增删改，类似于视频文件资源库(只有视频文件和文件属性)
 */
@Controller
@RequestMapping(value = "/material")
public class MaterialManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(MaterialManageController.class);

    @Autowired
    private MaterialManageService materialManageService;

    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult upload(@RequestParam("file") CommonsMultipartFile file,
                             @RequestParam(value = "qiniuPath",required = false) String qiniuPath){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null) ){
            return AjaxResult.failed();
        }
        if( !user.getRoleType().equals(GlobalConstant.ROLL_SUPERADMIN) ){
            return AjaxResult.failed("没有上传资源权限！");
        }

        Long tempMillis = System.currentTimeMillis();
        String fileKindString = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).trim().toLowerCase();
        int fileKind = 0;
        if(fileKindString != null && !fileKindString.equals("")){
            fileKind = validateFileKind(fileKindString,file);
        }
        else {
            return AjaxResult.failed("不支持的资源文件格式！");
        }
        if(fileKind == 0){
            return AjaxResult.failed("不支持的资源文件格式,只支持视频(rmvb、mp4、avi)、apk、音频(mp3、wmv、wav)、txt、图片(jpg、png、bmp)、bin/img！");
        }

        String path = Constants.UPLOAD_MATERIAL_PATH
                + TypeConverter.toLong(DateTime.getShortDate())
                + "/" + tempMillis
                + fileKindString;
        File dest = MaterialManageController.getResFile(TypeConverter.toLong(user.getOrgId()), path);
        Material m = new Material();
        m.setMaterialPath(user.getOrgId() + "/" + path);
        m.setQiniuPath(qiniuPath);
        m.setType(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) ? 1 : 0);
        m.setCreatedBy(user.getUserId());
        m.setCreated(DateTime.toMillis(tempMillis));
        m.setUpdated(m.getCreated());
        m.setUpdatedBy(user.getUserId());
        m.setOriginal(file.getOriginalFilename());
        m.setFileKind(fileKind);
        m.setOrgId(user.getOrgId());
        m.setClientId(user.getClientId());
        try {
            if (!file.isEmpty()) {
                dest.mkdirs();
                file.transferTo(dest);
                m.setMaterialSize(dest.length());
                materialManageService.addEntityBySeq(m);
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return AjaxResult.failed("出错");
        }
        MaterialDTView mLastInsert = (MaterialDTView)materialManageService.getEntity(new Material(m.getMaterialId()));
        MaterialDTView mReturn = new MaterialDTView();
        mReturn.setMaterialId(mLastInsert.getMaterialId());
        mReturn.setMaterialPath(mLastInsert.getMaterialPath());
        mReturn.setQiniuPath(mLastInsert.getQiniuPath());
        mReturn.setFileKindName(mLastInsert.getFileKindName());
        return AjaxResult.success(mReturn);
    }

    //验证文件扩展名类型符合规则,以后添加其他文件后可以增加,类型与数据库一一对应
    private int validateFileKind(String fileKindString,CommonsMultipartFile file) {
        int tempKind = 0;
        String type = FileType.getFileType(file);

        if(fileKindString.equals(".apk")) {//apk不知道头文件是什么，只验证扩展名
            tempKind = 1;
        }
        else if(fileKindString.equals(".txt")){//txt不知道头文件是什么，只验证扩展名
            tempKind = 2;
        }
        else if(fileKindString.equals(".mp3")||fileKindString.equals(".wmv")||fileKindString.equals(".wav")){
            if(type.matches(Constants.OFF_ALLOW_AUDIO)) {
                tempKind = 3;
            }
        }
        else if(fileKindString.equals(".rmvb")||fileKindString.equals(".mp4")||fileKindString.equals(".avi")){
            if(type.matches(Constants.OFF_ALLOW_VIDEO)){
                tempKind = 4;
            }
        }
        else if(fileKindString.equals(".jpg")||fileKindString.equals(".png")||fileKindString.equals(".bmp")){
            if(type.matches(Constants.OFF_ALLOW_PICTRUE)){
                tempKind = 5;
            }
        }
        else if(fileKindString.equals(".bin")||fileKindString.equals(".img")){
            tempKind = 6;
        }
        return tempKind;
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@ModelAttribute("materialView") MaterialView materialView){
        User user = getUserInfo();
        if(!dataValidate(materialView)){
            return AjaxResult.failed("传入参数有误");
        }
        MaterialDTView m = (MaterialDTView)materialManageService.getEntity(new Material(user, materialView));
        if(m == null){
            return AjaxResult.failed("要修改的对象不存在");
        }
        materialView.setType(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) ? 1 : 0);
        Material m1 = new Material(user, materialView);
        try {
            materialManageService.updateEntity(m1);
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
        paginationView.setDomain(SqlDomainNames.MATERIAL_LIST);
        materialManageService.listInPage(paginationView);
        return AjaxResult.success(paginationView);
    }
//
//    @RequestMapping(value = "/remove")
//    @ResponseBody
//    public AjaxResult remove(@RequestParam(value = "videoId") Long videoId){
//        Video v = (Video)videoManageService.getEntity(new Video(videoId));
//        if(v == null || (v != null && MyStringUtils.isBlank(v.getVideoPath()))){
//            return AjaxResult.failed();
//        }
//        File file = new File(Constants.DOWNLOAD_HOME+"/"+v.getVideoPath());
//        boolean flag = FileUtils.deleteQuietly(file);
//        videoManageService.removeEntity(new Video(videoId));
//        return AjaxResult.success();
//    }
//
    @RequestMapping(value = "/get")
    @ResponseBody
    public AjaxResult get(@RequestParam(value = "materialId") Long materialId){
        MaterialDTView v = (MaterialDTView)materialManageService.getEntity(new Material(materialId));
        return AjaxResult.success(v);
    }

    boolean dataValidate(MaterialView mView){
        if(mView == null){
            return false;
        }else if(mView.getMaterialPath() == null){
            return false;
        }else if(mView.getMaterialId() == null){
            return false;
        }else{
            return true;
        }
    }

    static File getResFile(Long orgID, String absPath) {
        return FileUtils.getFile(Constants.DOWNLOAD_HOME, Long.toString(orgID), absPath);
    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }
}
