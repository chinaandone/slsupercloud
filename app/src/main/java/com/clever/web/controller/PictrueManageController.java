package com.clever.web.controller;

import com.alibaba.fastjson.JSON;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.Pictrue;
import com.clever.common.domain.User;
import com.clever.common.partnerApi.filestore.QiniuStoreClient;
import com.clever.common.service.AdSequenceManageService;
import com.clever.common.service.PictrueManageService;
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

import java.io.File;
import java.io.IOException;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/pictrue")
public class PictrueManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(PictrueManageController.class);
    @Autowired
    private PictrueManageService pictrueManageService;

    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(@RequestParam("file") CommonsMultipartFile file, @RequestParam(value = "kind") Integer kind,
                           @RequestParam(value = "qiniuPath") String qiniuPath){
        String type = FileType.getFileType(file);
        if(!type.matches(Constants.OFF_ALLOW_PICTRUE)){
            return AjaxResult.failed("格式不正确,只能为jpg、png、bmp格式");
        }
        if(MyStringUtils.isBlank(qiniuPath)){
            return AjaxResult.failed("图片上传不完整");
        }
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        long currentTime = System.currentTimeMillis();
        long shortDate = TypeConverter.toLong(DateTime.getShortDate());
        String path = Constants.UPLOAD_PICTRUE_PATH
                + shortDate + "/" + currentTime + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        File dest = PictrueManageController.getResFile(TypeConverter.toLong(user.getOrgId()), path);
        Pictrue p = new Pictrue(TypeConverter.toLong(user.getClientId()),
                TypeConverter.toLong(user.getOrgId()),
                TypeConverter.toLong(user.getOrgId()) +"/"+path, qiniuPath, kind, file.getOriginalFilename(),
                GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType()) ? 1 : 0);
        try {
            if (!file.isEmpty()) {
                dest.mkdirs();
                file.transferTo(dest);
                ImageCompress imgCom  = new ImageCompress(dest.getAbsolutePath());
                String pathPre = Constants.UPLOAD_PICTRUE_PATH
                        + shortDate+"/"+"240_240";
                File pathPreview = PictrueManageController.getResFile(TypeConverter.toLong(user.getOrgId()), pathPre);
                pathPreview.mkdirs();
                imgCom.resizeFix(240, 240, pathPreview + "/"+ currentTime + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")));
                pictrueManageService.addEntityBySeq(p);
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return AjaxResult.failed("出错");
        }
        return AjaxResult.success(p);
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@RequestParam("file") CommonsMultipartFile file, @RequestParam(value = "pictrueId") Long pictrueId,
                           @RequestParam(value = "qiniuPath") String qiniuPath){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        if(MyStringUtils.isBlank(qiniuPath)){
            return AjaxResult.failed("图片上传不完整");
        }
        Pictrue p = (Pictrue)pictrueManageService.getEntity(new Pictrue(pictrueId));
        if(p == null || (p != null && MyStringUtils.isBlank(TypeConverter.toString(p.getPictrueId())))){
            return AjaxResult.failed();
        }
        long currentTime = System.currentTimeMillis();
        long shortDate = TypeConverter.toLong(DateTime.getShortDate());
        String path = Constants.UPLOAD_PICTRUE_PATH
                + shortDate + "/" + currentTime + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        File dest = PictrueManageController.getResFile(TypeConverter.toLong(user.getOrgId()), path);
        try {
            if (!file.isEmpty()) {
                dest.mkdirs();
                file.transferTo(dest);
                p.setPictruePath(TypeConverter.toLong(user.getOrgId()) + "/" + path);
                p.setOriginal(file.getOriginalFilename());
                p.setQiniuPath(qiniuPath);
                ImageCompress imgCom  = new ImageCompress(dest.getAbsolutePath());
                String pathPre = Constants.UPLOAD_PICTRUE_PATH
                        + shortDate+"/"+"240_240";
                File pathPreview = PictrueManageController.getResFile(TypeConverter.toLong(user.getOrgId()), pathPre);
                pathPreview.mkdirs();
                imgCom.resizeFix(240, 240, pathPreview + "/"+ currentTime + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")));
                pictrueManageService.updateEntity(p);
            }
        } catch (IOException e) {
            logger.error(e.toString());
            return AjaxResult.failed();
        }
        return AjaxResult.success(p);
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam(value = "pictrueId") Long pictrueId){
        User user = getUserInfo();
        Pictrue p = (Pictrue)pictrueManageService.getEntity(new Pictrue(pictrueId));
        if(p == null || (p != null && MyStringUtils.isBlank(p.getPictruePath()))){
            return AjaxResult.failed();
        }
        File file = new File(Constants.DOWNLOAD_HOME+"/"+p.getPictruePath());
        boolean flag = FileUtils.deleteQuietly(file);
        pictrueManageService.removeEntity(new Pictrue(pictrueId));
        return AjaxResult.success();
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public AjaxResult get(@RequestParam(value = "pictrueId") Long pictrueId){
        User user = getUserInfo();
        Pictrue p = (Pictrue)pictrueManageService.getEntity(new Pictrue(pictrueId));
        return AjaxResult.success(p);
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list(@ModelAttribute("paginationView") PaginationView paginationView,
                           @RequestParam(value = "kind", required = false) Integer kind){
        User user = getUserInfo();
        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())){
            paginationView.loadFilter().put("type", 1);
        }else{
            paginationView.loadFilter().put("type", 0);
        }
        if(kind == null){
            paginationView.loadFilter().put("gourpType", 1);//按照kind分组
        }else if(kind != null){
            paginationView.loadFilter().put("gourpType", 2);//不分组，显示等于当天的文件
        }
        paginationView.loadFilter().put("orgId", MyStringUtils.isBlank(TypeConverter.toString(user.getOrgId())) ? null : user.getOrgId());
        paginationView.loadFilter().put("kind", MyStringUtils.isBlank(TypeConverter.toString(kind)) ? null : kind);
        paginationView.setDomain(SqlDomainNames.PICTRUE_LIST);
        pictrueManageService.listInPage(paginationView);
        return AjaxResult.success(paginationView);
    }

    @RequestMapping(value = "/tokenAndKey")
    @ResponseBody
    public String qiniuTokenAndKey(){
        User user = getUserInfo();
        return JSON.toJSONString(QiniuStoreClient.getUploadTokenAndKey(getCommConfig()));
//        return AjaxResult.success(QiniuStoreClient.getUploadTokenAndKey(user.getClientId(), user.getOrgId(), user.getUserId(), fileType));
    }

    static File getResFile(Long orgID, String absPath) {
        return FileUtils.getFile(Constants.DOWNLOAD_HOME, Long.toString(orgID), absPath);
    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
//        User user = new User();//测试用
//        user.setOrgId(10L);
//        return user;
    }
}
