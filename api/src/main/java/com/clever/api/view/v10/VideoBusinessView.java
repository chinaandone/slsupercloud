package com.clever.api.view.v10;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.VideoView;
import com.clever.common.domain.User;
import com.clever.common.domain.VideoBusiness;
import com.clever.common.util.DateTime;

import java.util.Date;


public class VideoBusinessView extends BaseBean {

	private Long publishId;

	private Long videoId;

	private Long tableId;

	private Integer kind;

	private String previewPath;

	private String videoPath;

	private String qiniuPath;

	private Long videoSize;

	private Date timeStart;

	private Date timeEnd;

	private Integer type;

	private String description;

	private Integer enable;

	private Integer playFreq;

	public VideoBusinessView(){

	}

	public VideoBusinessView(VideoBusiness v){
		this.publishId = v.getPublishId();
		this.videoId = v.getVideoId();
		this.tableId = v.getTableId();
		this.kind = v.getKind();
		this.previewPath = v.getPreviewPath();
		this.videoPath = v.getVideoPath();
		this.qiniuPath = v.getQiniuPath();
		this.videoSize = v.getVideoSize();
		this.timeStart = v.getTimeStart();
		this.timeEnd = v.getTimeEnd();
		this.type = v.getType();
		this.description = v.getDescription();
		this.setClientId(v.getClientId());
		this.setOrgId(v.getOrgId());
		this.playFreq = v.getPlayFreq();
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