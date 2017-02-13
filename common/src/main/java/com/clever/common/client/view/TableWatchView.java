package com.clever.common.client.view;

import com.clever.common.bean.BaseBean;


public class TableWatchView extends BaseBean {

	private Long tableWatchId; //桌子手环对象ID

	private Long tableId; //关联桌子ID

	private String clientName; //品牌/商户名称,上传不需要

	private String orgName; //店铺/餐厅名称，上传不需要

	private String tableName; //桌子名，上传不需要

	private String displayTableName; //显示桌名,上传不需要

	private String dongleadd; //桌子pad对应的蓝牙dongle的433

	private String watchadd; //桌子关联的手环A的433寻址地址

	private String watchadd1; //桌子关联的手环B的433寻址地址

	private String mac_address;//桌子上PAD对应的Dongle蓝牙地址

	public String getMac_address() {
		return mac_address;
	}

	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}

	public Long getTableWatchId() {
		return tableWatchId;
	}

	public void setTableWatchId(Long tableWatchId) {
		this.tableWatchId = tableWatchId;
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

	private String watchadd2; //桌子关联的手环C的433寻址地址

	public TableWatchView(){

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

}