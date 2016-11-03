package com.clever.web.controller;

import com.clever.common.client.view.EvaluationView;
import com.clever.common.constant.GlobalConstant;
import com.clever.common.domain.Evaluation;
import com.clever.common.domain.Table;
import com.clever.common.domain.User;
import com.clever.common.domain.enums.EvaluationLevelType;
import com.clever.common.service.EvaluationManageService;
import com.clever.common.service.TableManageService;
import com.clever.common.sql.SqlDomainNames;
import com.clever.common.util.*;
import com.clever.common.view.AjaxResult;
import com.clever.common.view.PaginationView;
import com.clever.web.resource.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
@RequestMapping("/evaluation")
public class EvaluationManageController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(EvaluationManageController.class);

    @Autowired
    private EvaluationManageService evaluationManageService;

    @Autowired
    private TableManageService tableManageService;

    private final static String FEEL_AVG = "feelAVG";

    /**
     * 分页/不分页显示评论list
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult listPage(@ModelAttribute("paginationView") PaginationView paginationView,
                               @RequestParam(value = "clientId",required = false)Long clientId,
                               @RequestParam(value = "orgId",required = false)Long orgId,
                               @RequestParam(value = "tableId",required = false)Long tableId,
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
            paginationView.loadFilter().put("begin", DateTime.toShortDateTimeL(begin));
            paginationView.loadFilter().put("end", DateTime.toShortDateTimeL(end));
        }

//        System.out.println("#####################"+DateTime.toShortDateTimeL(begin) );
//        System.out.println("#####################"+DateTime.toShortDateTimeL(end) );

        paginationView.loadFilter().put("clientId", clientId);
        paginationView.loadFilter().put("orgId", orgId);
        paginationView.loadFilter().put("tableId", tableId);
        paginationView.setDomain(SqlDomainNames.EVALUATION_LIST);
        paginationView.setIDisplayAll(paginationView.getIDisplayAll());
        paginationView.loadFilter().put("iDisplayAll", paginationView.getIDisplayAll());

        //返回的结果数值
        Map<String,Object> result = new HashMap<String,Object>();

        //查询总体平均值
        List<Evaluation> feelAverage = (List<Evaluation>)evaluationManageService.getFeelAverage(paginationView);
        //为了前端评分参数显示不为NaN(not a number)，判断当查询结果为空时，扔个0或null就行了
        if(null != feelAverage && feelAverage.size() > 0){
        }
        else{
            feelAverage = new ArrayList<Evaluation>();
            Evaluation eTemp = new Evaluation();
            eTemp.setFeelEnvironment(0);
            eTemp.setFeelFlavor(0);
            eTemp.setFeelService(0);
            eTemp.setFeelWhole(0);
            feelAverage.add(eTemp);
        }
        result.put(FEEL_AVG,feelAverage);

        //查询评论详细
        evaluationManageService.listInPage(paginationView);
        result.put("paginationView", paginationView);


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

        try{
            //检索条件------ 开始
            PaginationView paginationView = new PaginationView();
            paginationView.loadFilter().put("clientId", clientId);
            paginationView.loadFilter().put("orgId", orgId);
            paginationView.loadFilter().put("tableId", tableId);
            paginationView.loadFilter().put("begin", DateTime.toShortDateTimeL(begin));
            paginationView.loadFilter().put("end", DateTime.toShortDateTimeL(end));
            //我们默认只输出前5W条数据
            paginationView.loadFilter().put("iDisplayAll", 0);
            paginationView.loadFilter().put("rowNum", 50000);
            paginationView.loadFilter().put("skipNum", 0);
            //检索条件------ 结束

            Map<String,Object> dataMap = new HashMap<String,Object>();
            List<String> titles = new ArrayList<String>();

            //每一列标题
            titles.add("品牌名称"); 		//1
            titles.add("店铺名称"); 		//2
            titles.add("餐桌名称"); 		//3
            titles.add("整体评价"); 		//4
            titles.add("口味评价"); 		//5
            titles.add("服务评价"); 		//6
            titles.add("环境评价"); 		//7
            titles.add("提交时间");		//8
            titles.add("用餐评价");  		//9
            titles.add("设备评价");  		//10

            dataMap.put("titles", titles);

            //先存平均值
            List<Evaluation> EvaluationList = (List<Evaluation>)evaluationManageService.getFeelAverage(paginationView);
            List<ExcelData> varList = new ArrayList<ExcelData>();
            DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
            for(int i=0;i<EvaluationList.size();i++){
                ExcelData vpd = new ExcelData();
                vpd.put("var1", "");
                vpd.put("var2", "");
                vpd.put("var3", "总体平均值（与时间段无关）");
                //评分结果数据库放大了20倍存储，我查询出来除以了2，所有都放大了10倍，需要处理下，显示一位小数
                vpd.put("var4", EvaluationList.get(i).getFeelWhole() == null ? "" : df.format((float)EvaluationList.get(i).getFeelWhole() / 10));
                vpd.put("var5", EvaluationList.get(i).getFeelFlavor() == null ? "" : df.format((float)EvaluationList.get(i).getFeelWhole() / 10));
                vpd.put("var6", EvaluationList.get(i).getFeelService() == null ? "" : df.format((float)EvaluationList.get(i).getFeelWhole() / 10));
                vpd.put("var7", EvaluationList.get(i).getFeelEnvironment() == null ? "" : df.format((float)EvaluationList.get(i).getFeelWhole() / 10));
                vpd.put("var8",  "");
                vpd.put("var9",  "");
                vpd.put("var10", "");
                varList.add(vpd);
            }

            //再存全部详细评论
//            PaginationView paginationView = new PaginationView();
//            paginationView.setFilter( condition ) ;
            paginationView.setDomain(SqlDomainNames.EVALUATION_LIST);
            EvaluationList = (List<Evaluation>)evaluationManageService.list(paginationView);

            SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(int i=0;i<EvaluationList.size();i++){
                ExcelData vpd = new ExcelData();
                Evaluation evaluation = EvaluationList.get(i);
                vpd.put("var1", evaluation.getClientName() == null       ? "" : evaluation.getClientName().toString());
                vpd.put("var2", evaluation.getOrgName() == null          ? "" : evaluation.getOrgName().toString());
                vpd.put("var3", evaluation.getTableName() == null        ? "" : evaluation.getTableName().toString());
                //评分结果数据库放大了20倍存储
                vpd.put("var4", evaluation.getFeelWhole() == null        ? "" : String.valueOf(evaluation.getFeelWhole() / 20));
                vpd.put("var5", evaluation.getFeelFlavor() == null       ? "" : String.valueOf(evaluation.getFeelWhole() / 20));
                vpd.put("var6", evaluation.getFeelService() == null      ? "" : String.valueOf(evaluation.getFeelWhole() / 20));
                vpd.put("var7", evaluation.getFeelEnvironment() == null  ? "" : String.valueOf(evaluation.getFeelWhole() / 20));
//                System.out.println("#####time" + DateTime.toNormalDateTime(evaluation.getTimeSecond()) +","+ new Date(evaluation.getTimeSecond()));
                vpd.put("var8", evaluation.getTimeSecond() == null ? "" : format.format(evaluation.getTimeSecond()) );
                vpd.put("var9", evaluation.getMealsRemark() == null      ? "" : evaluation.getMealsRemark().toString());
                vpd.put("var10", evaluation.getDeviceRemark() == null    ? "" : evaluation.getDeviceRemark().toString());
                varList.add(vpd);
            }

            dataMap.put("varList", varList);

            ObjectExcelView erv = new ObjectExcelView();
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