package com.clever.common.domain;

import com.clever.common.bean.BaseBean;


public class VideoPublishView extends BaseBean {

	private Long businessId;//视频业务ID

	private Integer kind;//视频类别

	private Long timeStart;//视频播放开始日期时间

	private Long timeEnd;//视频播放截止日期时间

	private Long publishTime;//发布时间

	private String description;//描述

	private Integer playFreq;//播放日频次

	public VideoPublishView(){

	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Long getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Long timeStart) {
		this.timeStart = timeStart;
	}

	public Long getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Long timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Long getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Long publishTime) {
		this.publishTime = publishTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPlayFreq() {
		return playFreq;
	}

	public void setPlayFreq(Integer playFreq) {
		this.playFreq = playFreq;
	}
}