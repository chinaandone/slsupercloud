package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.TablePhoneView;
import com.clever.common.client.view.TableWatchView;

import java.util.Date;


public class TableWatch extends BaseBean {



	private Long tableWatchId;

	private Long tableId;

	private String clientName;

	private String orgName;

	private String tableName;

	private String displayTableName;

	private String dongleadd;

	private String watchadd;

	private String watchadd1;

	private String watchadd2;

	private String mac_address;

	public String getMac_address() {
		return mac_address;
	}

	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}

	public TableWatch(){

	}

	public TableWatch(Long tableWatchId){
		this.setTableWatchId(tableWatchId);
	}

	public TableWatch(TableWatchView tableWatchView, User user){
		if( null != tableWatchView){
			this.setTableWatchId( tableWatchView.getTableWatchId() );
			this.setOrgId(tableWatchView.getOrgId());
			this.setClientId(tableWatchView.getClientId());
			this.setTableId(tableWatchView.getTableId());
			this.setWatchadd(tableWatchView.getWatchadd());
			this.setDisplayTableName(tableWatchView.getDisplayTableName());
			this.setMac_address(tableWatchView.getMac_address());
            this.setDongleadd(tableWatchView.getDongleadd());
		}
		if(null != user){
			this.setCreatedBy(user.getUserId());
			this.setCreated(new Date());
			this.setUpdatedBy(user.getUserId());
			this.setUpdated(new Date());
		}
	}

	public Long getTableWatchId() {
		return tableWatchId;
	}

	public void setTableWatchId(Long tableWatchId) {
		this.tableWatchId = tableWatchId;
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

	public String getDongleadd() {
		return dongleadd;
	}

	public void setDongleadd(String dongleadd) {
		this.dongleadd = dongleadd;
	}

	public String getWatchadd() {
		return watchadd;
	}

	public void setWatchadd(String watchadd) {
		this.watchadd = watchadd;
	}

	public String getWatchadd1() {
		return watchadd1;
	}

	public void setWatchadd1(String watchadd1) {
		this.watchadd1 = watchadd1;
	}

	public String getWatchadd2() {
		return watchadd2;
	}

	public void setWatchadd2(String watchadd2) {
		this.watchadd2 = watchadd2;
	}
}