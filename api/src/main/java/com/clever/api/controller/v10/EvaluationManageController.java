package com.clever.api.controller.v10;

import com.clever.api.controller.BaseController;
import com.clever.api.view.ClientAjaxResult;
import com.clever.common.client.view.EvaluationView;
import com.clever.common.domain.Evaluation;
import com.clever.common.domain.Table;
import com.clever.common.domain.enums.EvaluationLevelType;
import com.clever.common.service.EvaluationManageService;
import com.clever.common.service.TableManageService;
import com.clever.common.util.DateTime;
import com.clever.common.util.MyStringUtils;
import com.clever.common.util.TypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Info: 评价接口
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 15:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@RestController
@Scope("prototype")
@RequestMapping("/v10")
public class EvaluationManageController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(EvaluationManageController.class);

    @Autowired
    private EvaluationManageService evaluationManageService;

    @Autowired
    private TableManageService tableManageService;

    /**
     * 保存服务评价接口
     * @param evaluationView
     * @return
     */
    @RequestMapping(value = "/evaluation/save")
    public ClientAjaxResult save(@ModelAttribute("evaluationView") EvaluationView evaluationView) {
        logger.info("保存评价开始");
        try {
            if(!conNotBlank(evaluationView)){
                return ClientAjaxResult.failed("参数不正确...");
            }
            Table table = (Table)tableManageService.getEntity(new Table(evaluationView.getTableId()));
            if(table == null || (table != null && MyStringUtils.isBlank(TypeConverter.toString(table.getTableId())))){
                return ClientAjaxResult.failed("该表不存在...");
            }
            Evaluation evaluation = (Evaluation)evaluationManageService.getEntity(new Evaluation(evaluationView, table));
            if(evaluation != null && MyStringUtils.isBlank(TypeConverter.toString(evaluation.getTimeSecond()))){
                return ClientAjaxResult.failed("数据出问题了...");
            }
            if(evaluation == null || (evaluation != null && ((DateTime.getShortDateTimeL() - evaluation.getTimeSecond()) > 300))){
                evaluationManageService.addEntityBySeq(new Evaluation(evaluationView, table));
            }else if(evaluation != null){
                evaluation.setDeviceRemark(evaluationView.getDeviceRemark());
                evaluation.setMealsRemark(evaluationView.getMealsRemark());
                evaluation.setFeelEnvironment(evaluationView.getFeelEnvironment());
                evaluation.setFeelFlavor(evaluationView.getFeelFlavor());
                evaluation.setFeelService(evaluationView.getFeelService());
                evaluation.setFeelWhole(evaluationView.getFeelWhole());
                evaluation.setTimeSecond(DateTime.getShortDateTimeL());
                evaluation.setUpdated(new Date());
                evaluation.setUpdatedBy(evaluation.getOrgId());
                evaluationManageService.updateEntity(evaluation);
            }
            return ClientAjaxResult.success();
        }  catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ClientAjaxResult.failed("糟了...系统出错了...");
        }
    }

    //评论参数为必须至少有一个输入
    boolean conNotBlank(EvaluationView evaluationView){
        if(evaluationView == null || (evaluationView != null
                && MyStringUtils.isBlank(TypeConverter.toString(evaluationView.getTableId())))){
            return false;
        }else if(!MyStringUtils.isBlank(evaluationView.getDeviceRemark())
                && !MyStringUtils.isBlank(evaluationView.getMealsRemark()) && (evaluationView.getDeviceRemark().length() > 200 || evaluationView.getMealsRemark().length() > 200)){
            return false;
        }else if(!MyStringUtils.isBlank(EvaluationLevelType.getName(evaluationView.getFeelWhole()))
                || !MyStringUtils.isBlank(EvaluationLevelType.getName(evaluationView.getFeelFlavor()))
                || !MyStringUtils.isBlank(EvaluationLevelType.getName(evaluationView.getFeelService()))
                || !MyStringUtils.isBlank(EvaluationLevelType.getName(evaluationView.getFeelEnvironment()))
                || !MyStringUtils.isBlank(evaluationView.getDeviceRemark())
                || !MyStringUtils.isBlank(evaluationView.getMealsRemark()) ){
            return true;
        }else{
            return false;
        }
    }

}