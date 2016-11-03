package com.clever.web.controller;

import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.UploadAccessLog;
import com.clever.common.domain.User;
import com.clever.common.service.UploadAccessLogManageService;
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
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Info: 评价接口
 * User: chay.ni@clever-m.com
 * Date: 2016-03-29
 * Time: 15:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@RestController
@Scope("prototype")
@RequestMapping("/accessLog")
public class UploadAccessLogManageController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(UploadAccessLogManageController.class);

    @Autowired
    private UploadAccessLogManageService uploadAccessLogManageService;

    /**
     * 分页/不分页显示统计list
     * @param paginationView
     * @param clientId
     * @param orgId
     * @param tableId
     * @param analysisLevel 分析行为级别：
     *                      0：1、2级别行为都输出，2级行为若actionId为null则不输出。
     *                      1：只分析1级行为；
     *                      2：只分析2级行为，必须带actionId；
     * @param group 是否分组，0分组，1不分组,即显示详细列表
     * @param actionId 要查看的二级行为关联的一级行为Id，若只看一级行为，则该参数传入空即可。
     * @param begin
     * @param end
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult listPage(@ModelAttribute("paginationView") PaginationView paginationView,
                               @RequestParam(value = "clientId",required = false)Long clientId,
                               @RequestParam(value = "orgId",required = false)Long orgId,
                               @RequestParam(value = "tableId",required = false)Long tableId,
                               @RequestParam(value = "group")int group,
                               @RequestParam(value = "analysisLevel")int analysisLevel,
                               @RequestParam(value = "actionId",required = false)Long actionId,
                               @RequestParam(value = "begin",required = false)Long begin,
                               @RequestParam(value = "end",required = false)Long end){
        User user = getUserInfo();
        if(user == null || user.getOrgId() == null || user.getOrgId().equals("")){
            return AjaxResult.failed();
        }

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
        if((begin == null || "".equals(begin)) && end != null){
            return AjaxResult.failed("请设置查看的起始时间");
        }
        if((end == null || "".equals(end) )&& begin != null){
            return AjaxResult.failed("请设置查看的截止时间");
        }
        if(begin != null && end != null) {
            if (DateTime.toMillis(end).compareTo(DateTime.toMillis(begin)) < 0) {
                return AjaxResult.failed("结束时间不能小于开始时间");
            }
            paginationView.loadFilter().put("begin", DateTime.toMillis(begin));
            paginationView.loadFilter().put("end", DateTime.toMillis(end));
        }
//        System.out.println("begin:"+DateTime.toMillis(begin)+";"+DateTime.toNormalDateTime(begin));
//        System.out.println("end:"+DateTime.toMillis(end));

        //当只要分析二级行为时，actionId必须存在
        if(analysisLevel == 2 && actionId == null){
            return AjaxResult.failed("请输入要分析二级行为关联的一级行为ID");
        }

        //返回的结果数组
        Map<String,Object> result = new HashMap<String,Object>();

        paginationView.loadFilter().put("clientId", clientId);
        paginationView.loadFilter().put("orgId", orgId);
        paginationView.loadFilter().put("tableId", tableId);
        paginationView.setIDisplayAll(paginationView.getIDisplayAll());
        paginationView.loadFilter().put("iDisplayAll", paginationView.getIDisplayAll());
        if( group != 1 && group != 0 ){
            group = 0;
        }
        paginationView.loadFilter().put("group", group);

        //1级行为要分析的actionId的list，为null则输出所有1级行为。
        //目前我们设置的是报表显示6种，导出excel的时候全部28都输出
        List<Integer> inList =new  ArrayList<Integer>(Arrays.asList(
                Constants.ACTION_ORDER,
                Constants.ACTION_WATER,
                Constants.ACTION_PAPER,
                Constants.ACTION_CALL_PAY,
                Constants.ACTION_OTHER,
                Constants.ACTION_TIDY));
        paginationView.loadFilter().put("inList", inList);

        //分析1级行为
        if(analysisLevel == 0 || analysisLevel == 1){
            List<UploadAccessLog> level1Analysis = (List<UploadAccessLog>)uploadAccessLogManageService.getLevel1Analysis(paginationView);
            Long total1TimeStay = uploadAccessLogManageService.countTimeStay(paginationView);
            Long total1Click = uploadAccessLogManageService.countClick(paginationView);

            result.put("level1Analysis",level1Analysis);
            result.put("total1Click",total1Click);
            result.put("total1TimeStay",total1TimeStay);
            result.put("total1AnalysisNum",level1Analysis.size());
        }

        //分析二级行为，目前只有id为4、21、22、31的1级行为才有二级行为
        if(null != actionId
                && ( analysisLevel == 0 || analysisLevel == 2)
                && ( actionId == Constants.ACTION_CALL_PAY
                || actionId == Constants.ACTION_ACTIVITY_DISCOUNT
                || actionId == Constants.ACTION_ACTIVITY_STORE
                || actionId == Constants.ACTION_VIDEO_AD_CLICK
                || actionId == Constants.ACTION_PRIZE_SERVER_LUCKYID ) ){
            //查询二级行为不需要限定id
            paginationView.loadFilter().remove("inList");
            paginationView.loadFilter().put("actionId", actionId);
            List<UploadAccessLog> level2Analysis = (List<UploadAccessLog>)uploadAccessLogManageService.getLevel2Analysis(paginationView);
            Long total2TimeStay = uploadAccessLogManageService.countLv2TimeStay(paginationView);
            Long total2Click = uploadAccessLogManageService.countLv2Click(paginationView);

            result.put("level2Analysis",level2Analysis);
            result.put("total2Click", total2Click);
            result.put("total2TimeStay",total2TimeStay);
            result.put("total2AnalysisNum",level2Analysis.size());
        }

        return AjaxResult.success(result);
    }

    /**
     * excel export
     * 事例
     * 只输出前5W条数据，如果没有权限或设置有错误，输出为null
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel(@RequestParam(value = "clientId",required = false)Long clientId,
                                    @RequestParam(value = "orgId",required = false)Long orgId,
                                    @RequestParam(value = "tableId",required = false)Long tableId,
                                    @RequestParam(value = "group")int group,
                                    @RequestParam(value = "analysisLevel")int analysisLevel,
                                    @RequestParam(value = "actionId",required = false)Long actionId,
                                    @RequestParam(value = "begin")Long begin,
                                    @RequestParam(value = "end")Long end){
        ModelAndView mv = new ModelAndView();

        User user = getUserInfo();
        if(user == null || user.getOrgId() == null || user.getOrgId().equals("")){
            return null;
        }

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
            return null; //AjaxResult.failed("没有权限访问");
        }

        if(begin == null || "".equals(begin)){
            return null; //AjaxResult.failed("请设置查看的起始时间");
        }
        if(end == null || "".equals(end)){
            return null; //AjaxResult.failed("请设置查看的截止时间");
        }
        if(DateTime.toMillis(end).compareTo(DateTime.toMillis(begin)) < 0){
            return null; //AjaxResult.failed("结束时间不能小于开始时间");
        }
        //当只要分析二级行为时，actionId必须存在
        if(analysisLevel == 2 && actionId == null){
            return null;//AjaxResult.failed("请输入要分析二级行为关联的一级行为ID");
        }

        try{
            //查询条件
            PaginationView paginationView = new PaginationView();
            paginationView.loadFilter().put("clientId", clientId);
            paginationView.loadFilter().put("orgId", orgId);
            paginationView.loadFilter().put("tableId", tableId);
            paginationView.loadFilter().put("begin", DateTime.toMillis(begin));
            paginationView.loadFilter().put("end", DateTime.toMillis(end));
            //我们默认只输出前5W条数据
            paginationView.loadFilter().put("iDisplayAll", 0);
            paginationView.loadFilter().put("rowNum", 50000);
            paginationView.loadFilter().put("skipNum", 0);
            if( group != 1 && group != 0 ){
                group = 0;
            }
            paginationView.loadFilter().put("group", group);

            Map<String,Object> dataMap = new HashMap<String,Object>();
            List<String> titles = new ArrayList<String>();
            List<ExcelData> varList = new ArrayList<ExcelData>();

            //每一列标题
            titles.add("品牌名称"); 		//1
            titles.add("店铺名称"); 		//2
            titles.add("餐桌名称"); 		//3
            titles.add("点击日期"); 		//4
            titles.add("点击时间"); 		//5
            titles.add("星期"); 		    //6
            titles.add("点击次数"); 		//7
            titles.add("停留时间(秒数)"); //8
            titles.add("停留时间"); 		//9
            titles.add("一级行为名称"); 	//10
            titles.add("二级行为名称");	//11
            titles.add("描述");  		//12

            dataMap.put("titles", titles);

            //分析1级行为
            if(analysisLevel == 0 || analysisLevel == 1){
                List<UploadAccessLog> level1Analysis = (List<UploadAccessLog>)uploadAccessLogManageService.getLevel1Analysis(paginationView);
                Long total1TimeStay = uploadAccessLogManageService.countTimeStay(paginationView);
                Long total1Click = uploadAccessLogManageService.countClick(paginationView);

                //先存总数
                ExcelData vpd1 = new ExcelData();
                vpd1.put("var1", "一级行为分析");
                vpd1.put("var2", "");
                vpd1.put("var3", "");
                vpd1.put("var4", "");
                vpd1.put("var5", "");
                vpd1.put("var6", "一级行为总和");
                vpd1.put("var7", total1Click == null ? "" : String.valueOf(total1Click));
                vpd1.put("var8", total1TimeStay == null ? "" : String.valueOf(total1TimeStay));
                vpd1.put("var9", total1TimeStay == null ? "" : new TimeDurationFormat(TimeDurationFormat.Format.STRING_DDHHMMSS).format(total1TimeStay));
                vpd1.put("var10", "");
                vpd1.put("var11", "");
                vpd1.put("var12", "");
                varList.add(vpd1);

                //再存详细分析结果
                for(int i=0;i<level1Analysis.size();i++){
                    ExcelData vpd = new ExcelData();
                    UploadAccessLog uploadAccessLog = level1Analysis.get(i);
                    vpd.put("var1", uploadAccessLog.getClientName() == null       ? "" : uploadAccessLog.getClientName().toString());
                    vpd.put("var2", uploadAccessLog.getOrgName() == null          ? "" : uploadAccessLog.getOrgName().toString());
                    vpd.put("var3", uploadAccessLog.getTableName() == null        ? "" : uploadAccessLog.getTableName().toString());
                    //group 是否分组，0分组，1不分组,即显示详细列表
                    if( group == 0){
                        vpd.put("var4", "");//分组显示时，不显示点击时间，不分组时显示
                        vpd.put("var5", "");//分组显示时，不显示点击时间，不分组时显示
                        vpd.put("var6", "");//分组显示时，不显示点击时间，不分组时显示
                    }
                    else{
                        String dateString = DateTime.toNormalDate(uploadAccessLog.getTimePoit()) ;
                        vpd.put("var4", uploadAccessLog.getTimePoit() == null         ? "" : dateString);
                        vpd.put("var5", uploadAccessLog.getTimePoit() == null         ? "" : DateTime.toNormalTime(uploadAccessLog.getTimePoit()) );
                        vpd.put("var6", uploadAccessLog.getTimePoit() == null         ? "" : DateTime.intWeekToChinese(DateTime.dayForWeek(dateString)) );
                    }
                    vpd.put("var7", uploadAccessLog.getClickNum() == null         ? "" : uploadAccessLog.getClickNum().toString());
                    vpd.put("var8", uploadAccessLog.getTimeStay() == null ? "" : uploadAccessLog.getTimeStay().toString());
                    vpd.put("var9", uploadAccessLog.getTimeStay() == null ? "" : new TimeDurationFormat(TimeDurationFormat.Format.STRING_DDHHMMSS).format(uploadAccessLog.getTimeStay()));
                    vpd.put("var10", uploadAccessLog.getActionName() == null       ? "" : uploadAccessLog.getActionName().toString());
                    vpd.put("var11", uploadAccessLog.getLevelSecondName() == null  ? "" : uploadAccessLog.getLevelSecondName().toString());
                    vpd.put("var12", uploadAccessLog.getDiscription() == null      ? "" : uploadAccessLog.getDiscription().toString());
                    varList.add(vpd);
                }
            }

            //分析二级行为，目前只有id为4、21、22的1级行为才有二级行为
            if(null != actionId
                    && ( analysisLevel == 0 || analysisLevel == 2)
                    && ( actionId == Constants.ACTION_CALL_PAY
                        || actionId == Constants.ACTION_ACTIVITY_DISCOUNT
                        || actionId == Constants.ACTION_ACTIVITY_STORE
                        || actionId == Constants.ACTION_VIDEO_AD_CLICK
                        || actionId == Constants.ACTION_PRIZE_SERVER_LUCKYID) ){
                paginationView.loadFilter().put("actionId", actionId);
                List<UploadAccessLog> level2Analysis = (List<UploadAccessLog>)uploadAccessLogManageService.getLevel2Analysis(paginationView);
                Long total2TimeStay = uploadAccessLogManageService.countLv2TimeStay(paginationView);
                Long total2Click = uploadAccessLogManageService.countLv2Click(paginationView);

                //先存总数
                ExcelData vpd1 = new ExcelData();
                vpd1.put("var1", "二级行为分析");
                vpd1.put("var2", "");
                vpd1.put("var3", "");
                vpd1.put("var4", "");
                vpd1.put("var5", "");
                vpd1.put("var6", "二级行为总和");
                vpd1.put("var7", total2Click == null ? "" : String.valueOf(total2Click));
                vpd1.put("var8", total2TimeStay == null ? "" : String.valueOf(total2TimeStay));
                vpd1.put("var9", total2TimeStay == null ? "" : new TimeDurationFormat(TimeDurationFormat.Format.STRING_DDHHMMSS).format(total2TimeStay));
                vpd1.put("var10", "");
                vpd1.put("var11","");
                vpd1.put("var12","");
                varList.add(vpd1);

                //再存详细分析结果
                for(int i=0;i<level2Analysis.size();i++){
                    ExcelData vpd = new ExcelData();
                    UploadAccessLog uploadAccessLog = level2Analysis.get(i);
                    vpd.put("var1", uploadAccessLog.getClientName() == null       ? "" : uploadAccessLog.getClientName().toString());
                    vpd.put("var2", uploadAccessLog.getOrgName() == null          ? "" : uploadAccessLog.getOrgName().toString());
                    vpd.put("var3", uploadAccessLog.getTableName() == null        ? "" : uploadAccessLog.getTableName().toString());
                    String dateString = DateTime.toNormalDate(uploadAccessLog.getTimePoit()) ;
                    vpd.put("var4", uploadAccessLog.getTimePoit() == null         ? "" : dateString);
                    vpd.put("var5", uploadAccessLog.getTimePoit() == null         ? "" : DateTime.toNormalTime(uploadAccessLog.getTimePoit()) );
                    vpd.put("var6", uploadAccessLog.getTimePoit() == null         ? "" : DateTime.intWeekToChinese(DateTime.dayForWeek(dateString)) );

                    vpd.put("var7", uploadAccessLog.getClickNum() == null         ? "" : uploadAccessLog.getClickNum().toString());
                    vpd.put("var8", uploadAccessLog.getTimeStay() == null         ? "" : uploadAccessLog.getTimeStay().toString());
                    vpd.put("var9", uploadAccessLog.getTimeStay() == null         ? "" : new TimeDurationFormat(TimeDurationFormat.Format.STRING_DDHHMMSS).format(uploadAccessLog.getTimeStay() ) );
                    vpd.put("var10", uploadAccessLog.getActionName() == null       ? "" : uploadAccessLog.getActionName().toString());
                    vpd.put("var11", uploadAccessLog.getLevelSecondName() == null  ? "" : uploadAccessLog.getLevelSecondName().toString());
                    vpd.put("var12", uploadAccessLog.getDiscription() == null      ? "" : uploadAccessLog.getDiscription().toString());
                    varList.add(vpd);
                }
            }

            dataMap.put("varList", varList);

            ObjectExcelView erv = new ObjectExcelView(DateTime.getNormalNameDateTime());//以格式化的时间来命名文件
            mv = new ModelAndView(erv,dataMap);
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;

    }

    User getUserInfo(){
        return SessionManager.getCurrentOperator(getRequest());//正式用
    }

}