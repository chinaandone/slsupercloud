package com.clever.common.client.view;

import com.clever.common.bean.BaseBean;
import com.clever.common.domain.Pictrue;

import java.util.Date;


public class AdvertisementView {

	private Long advertisementId;//广告信息ID

//	private Long rollMainId;//轮播主图信息ID

	private Long pictrueId;//广告图片ID

	private Integer orderSeq;//广告排列顺序

	private String title;//广告标题

	private String description;//广告描述


	private Date timeEnd;

	private Date timeStart;

	public AdvertisementView(){

	}

	public Long getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(Long advertisementId) {
		this.advertisementId = advertisementId;
	}

	public Integer getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Long getPictrueId() {
		return pictrueId;
	}

	public void setPictrueId(Long pictrueId) {
		this.pictrueId = pictrueId;
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

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}
}