package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.EvaluationView;
import com.clever.common.util.DateTime;

import java.util.Date;


public class Evaluation extends BaseBean {

	private Long evaluationId;//主键ID

	private Long tableId;//餐桌ID

	private Integer feelWhole;//整体评价星级，0,20,40,80,100，即0-5分扩大20倍

	private Integer feelFlavor;//口味星级

	private Integer feelService;//服务星级

	private Integer feelEnvironment;//环境星级

	private String mealsRemark;//用餐评价

	private String deviceRemark;//设备评价

	private Long timeSecond;//提交时间，格式yyyyMMddHHmmss

	private String orgName;//店铺名称

	private String clientName;//品牌名称

	private String tableName;//桌子名称

	public Evaluation(){

	}

	public Evaluation(EvaluationView v, Table table){
		this.tableId = v.getTableId();
		this.feelWhole = v.getFeelWhole();
		this.feelService = v.getFeelService();
		this.feelFlavor = v.getFeelFlavor();
		this.feelEnvironment = v.getFeelEnvironment();
		this.mealsRemark = v.getMealsRemark();
		this.deviceRemark = v.getDeviceRemark();
		this.timeSecond = DateTime.getShortDateTimeL();
		this.setClientId(table.getClientId());
		this.setOrgId(table.getOrgId());
		this.setCreatedBy(table.getOrgId());
		this.setCreated(new Date());
		this.setUpdatedBy(table.getOrgId());
		this.setUpdated(new Date());
	}

	public Long getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(Long evaluationId) {
		this.evaluationId = evaluationId;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Integer getFeelWhole() {
		return feelWhole;
	}

	public void setFeelWhole(Integer feelWhole) {
		this.feelWhole = feelWhole;
	}

	public Integer getFeelFlavor() {
		return feelFlavor;
	}

	public void setFeelFlavor(Integer feelFlavor) {
		this.feelFlavor = feelFlavor;
	}

	public Integer getFeelService() {
		return feelService;
	}

	public void setFeelService(Integer feelService) {
		this.feelService = feelService;
	}

	public Integer getFeelEnvironment() {
		return feelEnvironment;
	}

	public void setFeelEnvironment(Integer feelEnvironment) {
		this.feelEnvironment = feelEnvironment;
	}

	public String getMealsRemark() {
		return mealsRemark;
	}

	public void setMealsRemark(String mealsRemark) {
		this.mealsRemark = mealsRemark;
	}

	public String getDeviceRemark() {
		return deviceRemark;
	}

	public void setDeviceRemark(String deviceRemark) {
		this.deviceRemark = deviceRemark;
	}

	public Long getTimeSecond() {
		return timeSecond;
	}

	public void setTimeSecond(Long timeSecond) {
		this.timeSecond = timeSecond;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}