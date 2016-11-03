package com.clever.common.client.view;

import com.clever.common.bean.BaseBean;


public class TableView extends BaseBean {

	private Long tableId; //桌子ID，新建不用传，修改传

	private Long tableTypeId; //桌子类型ID
	private String tableTypeName;//桌子类型名称

	private Long tableZoneId;//桌子区域ID
	private String tableZoneName;//桌子区域名称

	private String name;//桌子名称

	private String description;//桌子描述

	private Integer seatAdded;//桌子座位，暂不用

	private Long beeperDeviceId;//桌子设备ID，暂不用

	private String scanCode;//桌子码，用于点点笔扫码

	private String erpId;//erpId，小超人用

	public TableView(){

	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getTableTypeId() {
		return tableTypeId;
	}

	public void setTableTypeId(Long tableTypeId) {
		this.tableTypeId = tableTypeId;
	}

	public Long getTableZoneId() {
		return tableZoneId;
	}

	public void setTableZoneId(Long tableZoneId) {
		this.tableZoneId = tableZoneId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSeatAdded() {
		return seatAdded;
	}

	public void setSeatAdded(Integer seatAdded) {
		this.seatAdded = seatAdded;
	}

	public Long getBeeperDeviceId() {
		return beeperDeviceId;
	}

	public void setBeeperDeviceId(Long beeperDeviceId) {
		this.beeperDeviceId = beeperDeviceId;
	}

	public String getTableTypeName() {
		return tableTypeName;
	}

	public void setTableTypeName(String tableTypeName) {
		this.tableTypeName = tableTypeName;
	}

	public String getTableZoneName() {
		return tableZoneName;
	}

	public void setTableZoneName(String tableZoneName) {
		this.tableZoneName = tableZoneName;
	}

	public String getScanCode() {
		return scanCode;
	}

	public void setScanCode(String scanCode) {
		this.scanCode = scanCode;
	}

	public String getErpId() {
		return erpId;
	}

	public void setErpId(String erpId) {
		this.erpId = erpId;
	}
}