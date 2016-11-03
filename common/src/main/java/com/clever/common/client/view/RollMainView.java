package com.clever.common.client.view;

import com.clever.common.bean.BaseBean;
import com.clever.common.domain.Pictrue;

import java.util.Date;
import java.util.List;


public class RollMainView {

	private Long rollMainId;//轮播主图信息ID

	private Long pictrueId;//轮播主图图片ID

	private Integer orderSeq;//排列顺序

	private Long startTime;//有效开始时间

	private Long endTime;//有效结束时间

	private Integer type;//0：商户，1：商业（默认0）

	private Integer rollTime;//轮播时间

	private Long detailTextId;//轮播详细文本ID，保存时不需要传入

	private String title;//轮播详细标题

	private String description;//轮播详细描述

	private List<RollDetailView> rollDetailViewList;//轮播详细图片信息

	public RollMainView(){

	}

	public Long getRollMainId() {
		return rollMainId;
	}

	public void setRollMainId(Long rollMainId) {
		this.rollMainId = rollMainId;
	}

	public Long getPictrueId() {
		return pictrueId;
	}

	public void setPictrueId(Long pictrueId) {
		this.pictrueId = pictrueId;
	}

	public Integer getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getRollTime() {
		return rollTime;
	}

	public void setRollTime(Integer rollTime) {
		this.rollTime = rollTime;
	}

	public Long getDetailTextId() {
		return detailTextId;
	}

	public void setDetailTextId(Long detailTextId) {
		this.detailTextId = detailTextId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<RollDetailView> getRollDetailViewList() {
		return rollDetailViewList;
	}

	public void setRollDetailViewList(List<RollDetailView> rollDetailViewList) {
		this.rollDetailViewList = rollDetailViewList;
	}

}