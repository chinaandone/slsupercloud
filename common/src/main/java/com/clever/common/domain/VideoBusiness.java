package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.VideoView;
import com.clever.common.util.DateTime;

import java.util.Date;


public class VideoBusiness extends BaseBean {

	private Long businessId;

	private Long videoId;

	private Long tableId;

	private Integer kind;

	private String previewPath;

	private String videoPath;

	private String qiniuPath;

	private Long videoSize;

	private Date timeStart; //本店视频上下架时间用

	private Date timeEnd;//本店视频上下架时间用

	private Integer type;

	private String description;

	private Integer playFreq;//返回给安卓应用端的时候使用

	private Long publishId;//返回给安卓应用端的时候使用



	public VideoBusiness(){

	}

	public VideoBusiness(User user, VideoView view){
		if(user != null){
			this.setOrgId(user.getOrgId());
			this.setClientId(user.getClientId());
			this.setCreatedBy(user.getUserId());
			this.setCreated(new Date());
			this.setUpdatedBy(user.getUserId());
			this.setUpdated(new Date());
		}
		if(view != null){
			this.businessId = view.getBusinessId();
			this.videoId = view.getVideoId();
			this.tableId = view.getTableId();
			this.kind = view.getKind();
			this.timeStart = DateTime.toMillis(view.getTimeStart());
			this.timeEnd = DateTime.toMillis(view.getTimeEnd());
			this.type = view.getType();
			this.description = view.getDescription();
		}
	}

	public VideoBusiness(Long businessId, Long orgId, Integer type,Integer kind){
		this.setOrgId(orgId);
		this.type = type;
		this.businessId = businessId;
		this.kind = kind;
	}

	public Long getVideoId() {
		return videoId;
	}

	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getPreviewPath() {
		return previewPath;
	}

	public void setPreviewPath(String previewPath) {
		this.previewPath = previewPath;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public Long getVideoSize() {
		return videoSize;
	}

	public void setVideoSize(Long videoSize) {
		this.videoSize = videoSize;
	}

	public String getQiniuPath() {
		return qiniuPath;
	}

	public void setQiniuPath(String qiniuPath) {
		this.qiniuPath = qiniuPath;
	}

	public Long getPublishId() {
		return publishId;
	}

	public void setPublishId(Long publishId) {
		this.publishId = publishId;
	}

	public Integer getPlayFreq() {
		return playFreq;
	}

	public void setPlayFreq(Integer playFreq) {
		this.playFreq = playFreq;
	}
}