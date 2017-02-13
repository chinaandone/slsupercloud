package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.EvaluationView;
import com.clever.common.client.view.TableView;
import com.clever.common.util.DateTime;

import java.util.Date;


public class Table extends BaseBean {

	private Long tableId;

	private Long tableTypeId;
	private String tableTypeName;

	private Long tableZoneId;
	private String tableZoneName;

	private String name;

	private String description;

	private Integer seatAdded;

	private Long beeperDeviceId;

	private String scanCode;//桌子码，用于点点笔扫码

	private String erpId;//erpId，小超人用

	private int runflag; //for设备心跳
	private String versioncode="";//
	private String versionname="";//

	public int getRunflag() {
		return runflag;
	}

	public void setRunflag(int runflag) {
		this.runflag = runflag;
	}

	public Table(){

	}

	public Table(Long tableId){
		this.tableId = tableId;
	}

	public Table(TableView tableView,User user){
		if( null != tableView){
			this.setTableId(tableView.getTableId());
			this.setOrgId(tableView.getOrgId());
			this.setClientId(tableView.getClientId());
			this.setTableTypeId(tableView.getTableTypeId());
			this.setTableZoneId(tableView.getTableZoneId());
			this.setBeeperDeviceId(tableView.getBeeperDeviceId());
			this.setDescription(tableView.getDescription());
			this.setName(tableView.getName());
			this.setSeatAdded(tableView.getSeatAdded());
			this.setScanCode(tableView.getScanCode());
			this.setErpId(tableView.getErpId());
		}

		if( null != user){
			this.setCreatedBy(user.getUserId());
			this.setCreated(new Date());
			this.setUpdatedBy(user.getUserId());
			this.setUpdated(new Date());
		}
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

	public String getVersioncode() {
		return versioncode;
	}

	public void setVersioncode(String versioncode) {
		this.versioncode = versioncode;
	}

	public String getVersionname() {
		return versionname;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}
}