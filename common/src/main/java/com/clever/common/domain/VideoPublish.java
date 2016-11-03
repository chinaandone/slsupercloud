package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.VideoView;
import com.clever.common.util.DateTime;

import java.util.Date;


public class VideoPublish extends BaseBean {

	private Long videoPublishId;

	private Long videoId;

	private Long businessId;

	private Integer kind;//1：待机广告视频

	private Date timeStart;

	private Date timeEnd;

	private Date publishTime;

	private String previewPath;

	private String videoPath;

	private String original;

	private String orgName;

	private String description;

	private Integer playFreq;//播放日频次
	private String playFreqName;//播放日频次显示名称，返回前端页面显示用

	public VideoPublish(){

	}

	public VideoPublish(Long videoPublishId, Long businessId, Long clientId, Long orgId, Date publishTime, String description,
						String original, Integer kind, Long timeStart, Long timeEnd){
		this.videoPublishId = videoPublishId;
		this.businessId = businessId;
		this.setOrgId(orgId);
		this.setClientId(clientId);
		this.publishTime = publishTime;
		this.description = description;
		this.original = original;
		this.kind = kind;
		this.timeStart = DateTime.toMillis(timeStart);
		this.timeEnd = DateTime.toMillis(timeEnd);
		this.setCreated(publishTime);
	}

	public VideoPublish(User user, VideoView view){
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
			this.kind = view.getKind();
			this.timeStart = DateTime.toDate(view.getTimeStart());
			this.timeEnd = DateTime.toDate(view.getTimeEnd());
		}
	}

	public VideoPublish(VideoPublishView vpv, Org org, Date publishTime) {
		this.businessId = vpv.getBusinessId();
		this.setOrgId(org.getOrgId());
		this.setClientId(org.getClientId());
		this.publishTime = publishTime;
		this.description = vpv.getDescription();
		this.kind = vpv.getKind();
		this.timeStart = DateTime.toMillis(vpv.getTimeStart());
		this.timeEnd = DateTime.toMillis(vpv.getTimeEnd());
		this.playFreq = vpv.getPlayFreq();
		this.setCreated(publishTime);
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

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Long getVideoPublishId() {
		return videoPublishId;
	}

	public void setVideoPublishId(Long videoPublishId) {
		this.videoPublishId = videoPublishId;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
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

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getVideoId() {
		return videoId;
	}

	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}

	public Integer getPlayFreq() {
		return playFreq;
	}

	public void setPlayFreq(Integer playFreq) {
		this.playFreq = playFreq;
	}

	public String getPlayFreqName() {
		return playFreqName;
	}

	public void setPlayFreqName(String playFreqName) {
		this.playFreqName = playFreqName;
	}
}