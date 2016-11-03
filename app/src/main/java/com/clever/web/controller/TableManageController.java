package com.clever.web.controller;

import com.clever.common.client.view.TableView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.*;
import com.clever.common.service.ClientManageService;
import com.clever.common.service.OrgManageService;
import com.clever.common.service.TableManageService;
import com.clever.common.service.TableTypeManageService;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.redis.RedisUtil;
import com.clever.common.view.AjaxResult;
import com.clever.common.view.PaginationView;
import com.clever.web.resource.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-03-17
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Controller
@RequestMapping(value = "/table")
public class TableManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(TableManageController.class);

    @Autowired
    private TableTypeManageService tableTypeManageService;

    @Autowired
    private TableManageService tableManageService;

    @Autowired
    private OrgManageService orgManageService;

    @Autowired
    private ClientManageService clientManageService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 新建
     * @param tableView
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(@ModelAttribute("tableView") TableView tableView){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        //只有JF权限账号可以修改桌子
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                && !GlobalConstant.ROLL_JF.equals(user.getRoleType()) ){
            return AjaxResult.failed("没有权限");
        }
        if(null == tableView.getTableTypeId()){
            return AjaxResult.failed("桌子类型不能为空");
        }
        if(null == tableView.getOrgId()){
            return AjaxResult.failed("店铺ID不能为空");
        }
        if(null == tableView.getClientId()){
            return AjaxResult.failed("品牌ID不能为空");
        }
        if(null == tableView.getName()
                || "".equals(tableView.getName())){
            return AjaxResult.failed("桌子名称不能为空");
        }
        try{
            Org o = (Org)orgManageService.getEntity(new Org(tableView.getClientId(),tableView.getOrgId()));
            if(o == null){
                return AjaxResult.failed("所属店铺参数有误...");
            }

            Client c = (Client)clientManageService.getEntity(new Client(tableView.getClientId(),null));
            if(c == null){
                return AjaxResult.failed("所属品牌参数有误...");
            }

            TableTypeBean tTP= (TableTypeBean)tableTypeManageService.getEntity(new TableTypeBean(tableView.getTableTypeId()));
            if(null == tTP){
                return AjaxResult.failed("所属桌子类型不存在");
            }
            if( !tTP.getOrgId().equals(tableView.getOrgId())
                    || !tTP.getClientId().equals(tableView.getClientId()) ){
                return AjaxResult.failed("桌子类型与桌子所属店铺或品牌不一致");
            }

            if(getTableNumByName(tableView.getClientId(), tableView.getOrgId(), tableView.getName()) > 0){
                return AjaxResult.failed("已有该名称，请更换名称");
            }

            if(!validateScanCode(tableView.getScanCode())){
                return AjaxResult.failed("桌子码只能是000-200之间的三位数字！");
            }

            tableManageService.addEntityBySeq(new Table(tableView, user));
            //清除缓存信息
            redisUtil.delete("shop:table:info:" + tableView.getOrgId());
        } catch (Exception e) {
            logger.error("保存桌子信息异常", e);
            return AjaxResult.failed("保存桌子信息异常");
        }
        return AjaxResult.success("保存成功");
    }

    private boolean validateScanCode(String scanCode) {
        if(scanCode == null || "".equals(scanCode)){
            return true;
        }
        else if(scanCode.length() != 3){
            return false;
        }
        else if(!scanCode.matches("[012]\\d{2}")){//判断是不是0/1/2开头的3位纯数字
            return false;
        }
        try{
            int nScanCode = Integer.parseInt(scanCode);
            if(nScanCode >= 0 && nScanCode <= 200){
                return true;
            }
            else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/edit")
    @ResponseBody
    public AjaxResult edit(@ModelAttribute("tableView") TableView tableView){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        //只有JF权限账号可以修改桌子
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                && !GlobalConstant.ROLL_JF.equals(user.getRoleType()) ){
            return AjaxResult.failed("没有权限");
        }
        if(null == tableView.getTableTypeId()){
            return AjaxResult.failed("桌子类型不能为空");
        }
        if(null == tableView.getTableId()){
            return AjaxResult.failed("餐桌ID不能为空");
        }
        if(null == tableView.getName()
                || "".equals(tableView.getName())){
            return AjaxResult.failed("桌子名称不能为空");
        }
        try {
            Table t = (Table)tableManageService.getEntity(new Table(tableView.getTableId()));
            if(null == t){
                return AjaxResult.failed("桌子不存在");
            }

            TableTypeBean tTP= (TableTypeBean)tableTypeManageService.getEntity(new TableTypeBean(tableView.getTableTypeId()));
            if(null == tTP){
                return AjaxResult.failed("桌子类型不存在");
            }
            if( !tTP.getOrgId().equals(t.getOrgId())
                    || !tTP.getClientId().equals(t.getClientId()) ){
                return AjaxResult.failed("桌子类型与桌子所属店铺或品牌不一致");
            }

            //修改时，只有用户修改了当前对象名称时，再验证是否重名。否则认为用户没有修改当前对象的名称,不做重名验证。
            if(getTableNumByName(tableView.getClientId(),tableView.getOrgId(),tableView.getName()) > 0 && !t.getName().equals(tableView.getName()) ){
                return AjaxResult.failed("已有该名称，请更换名称");
            }

            if(!validateScanCode(tableView.getScanCode())){
                return AjaxResult.failed("桌子码只能是000-200之间的三位数字！");
            }

            tableManageService.updateEntity(new Table(tableView, user));
            //清除缓存信息
            redisUtil.delete("shop:table:info:" + t.getOrgId());
        } catch (Exception e) {
            logger.error(e.toString());
            return AjaxResult.failed("桌子修改失败！");
        }
        return AjaxResult.success("桌子修改成功");
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public AjaxResult get(@RequestParam(value = "tableId") Long tableId){
        User user = getUserInfo();
        if(user == null || (user != null && user.getOrgId() == null)){
            return AjaxResult.failed();
        }
        //只有JF权限账号可以修改桌子电话
        if(!GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                && !GlobalConstant.ROLL_JF.equals(user.getRoleType()) ){
            return AjaxResult.failed("没有权限");
        }
        Table tP = (Table)tableManageService.getEntity(new Table(tableId));
        return AjaxResult.success(tP);
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list(@ModelAttribute("paginationView") PaginationView paginationView,
                           @RequestParam(value = "clientId",required = false)Long clientId,
                           @RequestParam(value = "orgId",required = false)Long orgId){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null || user.getOrgId().equals("")){
            return AjaxResult.failed();
        }
//        if(GlobalConstant.ROLL_JF.equals(user.getRoleType())  ){
//            paginationView.loadFilter().put("clientId", clientId);
//            paginationView.loadFilter().put("orgId", orgId);
//        }else{
//            return AjaxResult.failed("没有权限访问");
//        }
        if(GlobalConstant.ROLL_SUPERADMIN.equals(user.getRoleType())
                || GlobalConstant.ROLL_JF.equals(user.getRoleType())){
            //木爷权限/服务JF权限使用外部传入的clientId和orgId
        }else if(GlobalConstant.ROLL_BRANDUSER.equals(user.getRoleType())  ){
            //品牌账号使用自身的品牌clientId
            clientId = user.getClientId();
        }else if( GlobalConstant.ROLL_CLIENTUSER.equals(user.getRoleType()) ){
            //店铺账号使用自身的品牌clientId,orgId
            clientId = user.getClientId();
            orgId = user.getOrgId();
        }
        else{
            return AjaxResult.failed("没有权限访问");
        }
        paginationView.loadFilter().put("clientId", clientId);
        paginationView.loadFilter().put("orgId", orgId);
        paginationView.loadFilter().put("iDisplayAll", paginationView.getIDisplayAll() );
        paginationView.setDomain(SqlDomainNames.TABLE_LIST);
        tableManageService.listInPage(paginationView);
        return AjaxResult.success(paginationView);
    }

    private int getTableNumByName(Long clientId,Long orgId,String name) {
        PaginationView paginationView = new PaginationView();
        paginationView.loadFilter().put("clientId", clientId);
        paginationView.loadFilter().put("orgId", orgId);
        paginationView.loadFilter().put("tableName", name);
        paginationView.setDomain(SqlDomainNames.TABLE_LIST);
        int c = tableManageService.count(paginationView);
        return c;
    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }
}
