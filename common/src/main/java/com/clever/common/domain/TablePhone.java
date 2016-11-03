package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.TablePhoneView;

import java.util.Date;


public class TablePhone extends BaseBean {

	private Long tablePhoneId;

	private Long tableId;

	private String clientName;

	private String orgName;

	private String tableName;

	private String displayTableName;

	private String phone;

	private String phone1;

	private String phone2;


	public TablePhone(){

	}

	public TablePhone(Long tablePhoneId){
		this.setTablePhoneId(tablePhoneId);
	}

	public TablePhone(TablePhoneView tablePhoneView,User user){
		if( null != tablePhoneView){
			this.setTablePhoneId( tablePhoneView.getTablePhoneId() );
			this.setOrgId(tablePhoneView.getOrgId());
			this.setClientId(tablePhoneView.getClientId());
			this.setTableId(tablePhoneView.getTableId());
			this.setPhone(tablePhoneView.getPhone());
			this.setDisplayTableName(tablePhoneView.getDisplayTableName());
		}
		if(null != user){
			this.setCreatedBy(user.getUserId());
			this.setCreated(new Date());
			this.setUpdatedBy(user.getUserId());
			this.setUpdated(new Date());
		}
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

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
}