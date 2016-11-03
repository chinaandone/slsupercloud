package com.clever.common.domain;

import com.clever.common.bean.BaseBean;


public class Path extends BaseBean {

	private String actionName;

	private Long pathId;

	private String pathName;

	public Path(){

	}

	public Path(Long pathId, String actionName){
		if(pathId != null){
			this.pathId = pathId;
		}
		if(actionName != null){
			this.actionName = actionName;
		}
	}

	public Path(String actionName){
		this.actionName = actionName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Long getPathId() {
		return pathId;
	}

	public void setPathId(Long pathId) {
		this.pathId = pathId;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
}