package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.RollMainView;
import com.clever.common.util.DateTime;

import java.util.Date;
import java.util.List;


public class RollPublish extends BaseBean {

	private Long rollPublishId;

	private Long rollMainId;

	private String title;

	private Integer orderSeq;

	private Date startTime;

	private Date endTime;

	private Date publishTime;

	private String orgName;

	public RollPublish(Long rollPublishId, Long clientId, Long orgId, Long rollMainId, String title, Date publishTime, Integer orderSeq, Date startTime, Date endTime,User user){
		this.rollPublishId = rollPublishId;
		this.rollMainId = rollMainId;
		this.title = title;
		this.publishTime = publishTime;
		this.orderSeq = orderSeq;
		this.startTime = startTime;
		this.endTime = endTime;
		this.setClientId(clientId);
		this.setOrgId(orgId);
		if(user != null){
			this.setCreatedBy(user.getUserId());
			this.setUpdatedBy(user.getUserId());
		}
		this.setCreated(new Date());
		this.setUpdated(new Date());
	}

	public RollPublish(){

	}

	public Long getRollPublishId() {
		return rollPublishId;
	}

	public void setRollPublishId(Long rollPublishId) {
		this.rollPublishId = rollPublishId;
	}

	public Long getRollMainId() {
		return rollMainId;
	}

	public void setRollMainId(Long rollMainId) {
		this.rollMainId = rollMainId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}