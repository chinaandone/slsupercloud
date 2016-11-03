package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.VideoView;
import com.clever.common.util.DateTime;

import java.util.Date;


public class Video extends BaseBean {

	private Long videoId;

	private Long tableId;

	private String previewPath;

	private String videoPath;

	private String qiniuPath;

	private Integer playSecond;

	private Long videoSize;

	private Integer type;

	private String description;

	private String original;

	private Integer enable;

	public Video(){

	}

	public Video(Long videoId){
		this.videoId = videoId;
	}

	public Video(User user, VideoView view){
		if(user != null){
			this.setOrgId(user.getOrgId());
			this.setClientId(user.getClientId());
			this.setCreatedBy(user.getUserId());
			this.setCreated(new Date());
			this.setUpdatedBy(user.getUserId());
			this.setUpdated(new Date());
		}
		if(view != null){
			this.videoId = view.getVideoId();
			this.tableId = view.getTableId();
			this.videoPath = view.getVideoPath();
			this.qiniuPath = view.getQiniuPath();
			this.playSecond = view.getPlaySecond();
			this.videoSize = view.getVideoSize();
			this.type = view.getType();
			this.description = view.getDescription();
			this.previewPath = view.getPreviewPath();
			this.original = view.getOriginal();
		}
	}

	public Video(Long orgId, Integer type){
		this.setOrgId(orgId);
		this.type = type;
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

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public Integer getPlaySecond() {
		return playSecond;
	}

	public void setPlaySecond(Integer playSecond) {
		this.playSecond = playSecond;
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

	public String getPreviewPath() {
		return previewPath;
	}

	public void setPreviewPath(String previewPath) {
		this.previewPath = previewPath;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
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
}