package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.domain.enums.PictrueKindType;
import com.clever.common.util.DateTime;

import java.util.Date;
import java.util.List;


public class UploadAccessLog extends BaseBean {

	private Long uploadAccessId;//上传记录主键ID

	private Long tableId;//餐桌ID

	private Long actionId;//功能ID

	private Integer clickNum;//点击次数

	private Date timePoit;//点击时间点

	private Long timeStay;//浏览停留时长，秒为单位

	private String discription;//描述

	private Long levelSecondId;//二级功能ID

	private String orgName;//店铺名称

	private String clientName;//品牌名称

	private String tableName;//桌子名称

	private String actionName;//一级行为名称

	private String levelSecondName;//二级行为名称

	public UploadAccessLog(){

	}

	public UploadAccessLog(Long clientId, Long orgId, Long tableId, Long actionId, Integer clickNum, Date timePoit){
		this.setClientId(clientId);
		this.setOrgId(orgId);
		this.setTableId(tableId);
		this.actionId = actionId;
		this.clickNum = clickNum;
		this.timePoit = timePoit;
		this.setCreatedBy(orgId);
		this.setCreated(new Date());
	}

	public Long getUploadAccessId() {
		return uploadAccessId;
	}

	public void setUploadAccessId(Long uploadAccessId) {
		this.uploadAccessId = uploadAccessId;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public Integer getClickNum() {
		return clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

	public Date getTimePoit() {
		return timePoit;
	}

	public void setTimePoit(Date timePoit) {
		this.timePoit = timePoit;
	}

	public Long getTimeStay() {
		return timeStay;
	}

	public void setTimeStay(Long timeStay) {
		this.timeStay = timeStay;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public Long getLevelSecondId() {
		return levelSecondId;
	}

	public void setLevelSecondId(Long levelSecondId) {
		this.levelSecondId = levelSecondId;
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

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getLevelSecondName() {
		return levelSecondName;
	}

	public void setLevelSecondName(String leverSecondName) {
		this.levelSecondName = leverSecondName;
	}

}