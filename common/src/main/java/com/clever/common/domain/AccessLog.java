package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.domain.enums.PictrueKindType;
import com.clever.common.util.DateTime;

import java.util.Date;


public class AccessLog extends BaseBean {

	private Long accessId;

	private Long tableId;

	private Long accessTime;

	private String actionPath;

	private String pathName;

	public AccessLog(String actionPath, String pathName, Long accessTime){
		this.actionPath = actionPath;
		this.pathName = pathName;
		this.accessTime = accessTime;
		this.setCreated(new Date());
		this.setUpdated(new Date());
	}

	public Long getAccessId() {
		return accessId;
	}

	public void setAccessId(Long accessId) {
		this.accessId = accessId;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public Long getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Long accessTime) {
		this.accessTime = accessTime;
	}

	public String getActionPath() {
		return actionPath;
	}

	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}
}