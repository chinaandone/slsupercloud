package com.clever.web.controller;

import com.clever.common.client.view.AdvertisementView;
import com.clever.common.client.view.TablePhoneView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.*;
import com.clever.common.service.AdvertisementManageService;
import com.clever.common.service.PictrueManageService;
import com.clever.common.service.TableManageService;
import com.clever.common.service.TablePhoneManageService;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.MyStringUtils;
import com.clever.common.util.TypeConverter;
import com.clever.common.util.ValidatorUtil;
import com.clever.common.view.AjaxResult;
import com.clever.common.view.PaginationView;
import com.clever.web.resource.SessionManager;
import com.clever.web.util.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.List;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-03-17
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/tablePhone")
public class TablePhoneManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(TablePhoneManageController.class);

    @Autowired
    private TablePhoneManageService tablePhoneManageService;

    @Autowired
    private TableManageService tableManageService;

    /**
     * 同步桌名：更新或新建TablePhone对象到r_info_phone表
     * @param tablePhoneView
     * @return
     */
//    @RequestMapping(value = "/saveOrEdit")
    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(@ModelAttribute("tablePhoneView") TablePhoneView tablePhoneView){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        //只有JF权限账号可以修改桌子电话
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                && !GlobalConstant.ROLL_JF.equals(user.getRoleType()) ){
            return AjaxResult.failed("没有权限");
        }

        try{
            if(!validatePhone(tablePhoneView.getPhone())){
                return AjaxResult.failed("请输入正确的手机号！");
            }
            Table t = (Table)tableManageService.getEntity( new Table( tablePhoneView.getTableId() ) );
            if( null == t ){
                return AjaxResult.failed("桌号不存在");
            }
            //新建/更新时设置对象显示桌名为最新的桌名
            tablePhoneView.setDisplayTableName(t.getName());
            tablePhoneView.setOrgId(t.getOrgId());
            tablePhoneView.setClientId(t.getClientId());

            //如果tablePhoneId为null，则判断库中对应桌子ID的记录是否存在，不存在则新建，存在则更新
            if( MyStringUtils.isBlank(TypeConverter.toString( tablePhoneView.getTablePhoneId() ) ) ){
                List<TablePhone> tempPhoneList = tablePhoneManageService.getEntities(new TablePhone(tablePhoneView,user));
                //如果库中没有记录，则新建
                if(null == tempPhoneList || tempPhoneList.size() == 0){
                    tablePhoneManageService.addEntityBySeq(new TablePhone(tablePhoneView,user));
                }
                //如果库中已经有关于该桌子的记录了且只有一条，则修改；
                else if(tempPhoneList != null && tempPhoneList.size()==1){
                    //TODO 系统保留桌子，不能修改
                    if(tempPhoneList.get(0).getActive() == 0){
                        return AjaxResult.failed("系统保留桌子，不能修改");
                    }
                    tablePhoneView.setTablePhoneId(tempPhoneList.get(0).getTablePhoneId());
                    tablePhoneManageService.updateEntity(new TablePhone(tablePhoneView,user));
                }
                //如果记录大于1条，则删除多余记录，只留下一条然后修改；
                else if(tempPhoneList != null && tempPhoneList.size()>1){
                    //删除多余记录,保留active=0的数据，这是之前已经配置过的手机号
                    for (int i = 1; i < tempPhoneList.size(); i++) {
                        //TODO 系统保留桌子，不能修改
                        if(tempPhoneList.get(i).getActive() == 0){
                            continue;
                        }
                        else if(tempPhoneList.get(i).getActive() == 1){
                            tablePhoneManageService.removeEntity(tempPhoneList.get(i).getTablePhoneId());
                        }
                    }
                    //更新一条记录
                    tablePhoneView.setTablePhoneId(tempPhoneList.get(0).getTablePhoneId());
                    tablePhoneManageService.updateEntity(new TablePhone(tablePhoneView,user));
                }
                else {
                    return AjaxResult.failed("传入参数有误");
                }

            }
            //如果tablePhoneId不为null，且tablePhone对象存在，则更新
            else{
                TablePhone tP = (TablePhone)tablePhoneManageService.getEntity(new TablePhone(tablePhoneView,user));
                if(null == tP){
                    return AjaxResult.failed("关联的更新桌子电话对象不存在");
                }
                if(!tP.getTableId() .equals( t.getTableId()) ){
                    return AjaxResult.failed("桌子ID和电话ID对象不匹配");
                }
                //TODO 系统保留桌子，不能修改
                if(tP.getActive() == 0){
                    return AjaxResult.failed("系统保留桌子，不能修改");
                }
                tablePhoneManageService.updateEntity(new TablePhone(tablePhoneView,user));
            }
        } catch (Exception e) {
            logger.error("保存桌子电话信息异常", e);
            return AjaxResult.failed("保存桌子电话信息异常");
        }
        return AjaxResult.success(null,"保存成功");
    }

    /**
     * 当手机号为空或“”时，说明用户不填，也是可以的
     * @param phone
     * @return true验证通过，false验证失败
     */
    private boolean validatePhone(String phone){
        if(phone == null || phone.equals("")){
            return true;
        }

        String phones[]= phone.split(",");
        for(int i=0;i<phones.length;i++){
            if(!ValidatorUtil.isMobile(phones[i])){
                return false;
            }

        }
        return true;
    }

//    @RequestMapping(value = "/editPhone")
    @RequestMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@RequestParam(value = "tablePhoneId") Long tablePhoneId,
                           @RequestParam(value = "phone")String phone){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        //只有JF权限账号可以修改桌子电话
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                && !GlobalConstant.ROLL_JF.equals(user.getRoleType()) ){
            return AjaxResult.failed("没有权限");
        }
        if( MyStringUtils.isBlank( TypeConverter.toString(tablePhoneId) )){
            return AjaxResult.failed("请先同步桌名，再修改手机号！");
        }
        try {
            if(!validatePhone(phone)){
                return AjaxResult.failed("请输入正确的手机号！");
            }
            TablePhone tP = (TablePhone)tablePhoneManageService.getEntity(new TablePhone(tablePhoneId));
            if(null == tP){
                return AjaxResult.failed("请先同步桌名，再修改手机号");
            }
            //TODO 系统保留桌子，不能修改
            if(tP.getActive() == 0){
                return AjaxResult.failed("系统保留桌子，不能修改");
            }

            TablePhoneView tempTP = new TablePhoneView();
            tempTP.setPhone(phone);
            tempTP.setTablePhoneId(tablePhoneId);
            tablePhoneManageService.updateEntity(new TablePhone(tempTP,user));
        } catch (Exception e) {
            logger.error(e.toString());
            return AjaxResult.failed("桌子电话修改失败！");
        }
        return AjaxResult.success(null,"桌子电话修改成功");
    }

    /**
     * 删除桌子电话对象函数，暂时用不到
     * @param tablePhoneId
     * @return
     */
//    @RequestMapping(value = "/remove")
//    @ResponseBody
//    public AjaxResult remove(@RequestParam(value = "tablePhoneId") Long tablePhoneId){
//        User user = getUserInfo();
//        if(user == null || (user != null && user.getOrgId() == null)){
//            return AjaxResult.failed();
//        }
//        //只有JF权限账号可以修改/删除/查询桌子电话
//        if(!GlobalConstant.ROLL_JF.equals(user.getRoleType()) ){
//            return AjaxResult.failed("没有权限");
//        }
//        try {
//            TablePhone tP = (TablePhone)tablePhoneManageService.getEntity( new TablePhone( tablePhoneId ) );
//            if(null == tP){
//                return AjaxResult.failed("关联的桌子电话对象不存在,无法删除");
//            }
//
//            tablePhoneManageService.removeEntity(tablePhoneId);
//        } catch (Exception e) {
//            logger.error(e.toString());
//            return AjaxResult.failed("桌子电话删除失败！");
//        }
//        return AjaxResult.success("桌子电话删除成功");
//    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public AjaxResult get(@RequestParam(value = "tablePhoneId") Long tablePhoneId){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        //只有JF权限账号可以修改桌子电话
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                && !GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            return AjaxResult.failed("没有权限");
        }
        TablePhone tP = (TablePhone)tablePhoneManageService.getEntity(new TablePhone( tablePhoneId) );
        //TODO 系统保留桌子，不能修改
        if(tP != null &&  tP.getActive() == 0){
            return AjaxResult.success(tP,"系统保留餐桌，请勿修改！");
        }
        return AjaxResult.success(tP);
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list(@ModelAttribute("paginationView") PaginationView paginationView,
                           @RequestParam(value = "clientId",required = false)Long clientId,
                           @RequestParam(value = "orgId",required = false)Long orgId,
                           @RequestParam(value = "tableId",required = false)Long tableId){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null || user.getOrgId().equals("")){
            return AjaxResult.failed();
        }
        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                || GlobalConstant.ROLL_JF.equals(user.getRoleType()) ){
            paginationView.loadFilter().put("clientId", clientId);
            paginationView.loadFilter().put("orgId", orgId);
            paginationView.loadFilter().put("tableId", tableId);
        }else{
            return AjaxResult.failed("没有权限访问");
        }
        paginationView.setDomain(SqlDomainNames.TABLEPHONE_LIST);
        tablePhoneManageService.listInPage(paginationView);
        return AjaxResult.success(paginationView);
    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }
}
