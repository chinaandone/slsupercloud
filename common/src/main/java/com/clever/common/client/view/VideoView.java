package com.clever.common.client.view;

import com.clever.common.bean.BaseBean;

import java.util.Date;
import java.util.List;


public class VideoView extends BaseBean {

	private Long businessId;//视频业务ID

	private Long videoId;//视频ID

	private Long tableId;//餐桌ID（不需要传入）

	private Integer kind;//视频种类，1:待机广告视频2:点播视频

	private String videoPath;//视频路径

	private String qiniuPath;//七牛视频路径

	private Long timeStart;//视频播放开始日期时间

	private Long timeEnd;//视频播放截止日期时间

	private Integer playSecond;//需要播放时长

	private Long videoSize;//视频大小

	private Integer type;//类型（0：商户，1：商业）（不需要传入）

	private String description;//描述

	private String previewPath;//缩略图路径

	private String original;//图片显示名称

	public VideoView(){

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

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
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

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
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