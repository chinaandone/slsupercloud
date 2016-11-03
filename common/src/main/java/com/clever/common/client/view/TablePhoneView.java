package com.clever.common.client.view;

import com.clever.common.bean.BaseBean;


public class TablePhoneView extends BaseBean {

	private Long tablePhoneId; //桌子电话对象ID

	private Long tableId; //关联桌子ID

	private String clientName; //品牌/商户名称,上传不需要

	private String orgName; //店铺/餐厅名称，上传不需要

	private String tableName; //桌子名，上传不需要

	private String displayTableName; //显示桌名,上传不需要

	private String phone; //桌子关联电话


	public TablePhoneView(){

	}

	public Long getTablePhoneId() {
		return tablePhoneId;
	}

	public void setTablePhoneId(Long tablePhoneId) {
		this.tablePhoneId = tablePhoneId;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDisplayTableName() {
		return displayTableName;
	}

	public void setDisplayTableName(String displayTableName) {
		this.displayTableName = displayTableName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}