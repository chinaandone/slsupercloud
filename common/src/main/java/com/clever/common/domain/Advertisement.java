package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.AdvertisementView;

import java.util.Date;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-03-02
 * Time: 15:11
 * Version: 1.0
 * History: <p>2016-03-03增加字段timeEnd,timeStart</P>
 */

public class Advertisement extends BaseBean {

	//BaseBean已经包含基本公有参数：clientId，orgId,active,createdBy,created,updatedBy,updated
	private Long advertisementId;

	private Long rollMainId;

	private Long pictrueId;

	private Integer orderSeq;

	private String title;

	private Date timeEnd;

	private Date timeStart;

	private String description;

	private String pictruePath;

	private String qiniuPath;

	private Long advertisementMainId;

	private String picOrinName;//图片原始文件名

	public Advertisement(){

	}

	public Advertisement(Long advertisementId){
		this.advertisementId = advertisementId;
	}

	public Advertisement(AdvertisementView advertisementView, RollMain rollMain,User user){
		if(rollMain != null && rollMain.getRollMainId() != null){
			this.rollMainId = rollMain.getRollMainId();
		}
		if(advertisementView.getOrderSeq() != null){
			this.orderSeq = advertisementView.getOrderSeq();
		}
		if(advertisementView.getAdvertisementId() != null){
			this.advertisementId = advertisementView.getAdvertisementId();
		}
		if(advertisementView.getTitle() != null){
			this.title = advertisementView.getTitle();
		}
		if(advertisementView.getDescription() != null){
			this.description = advertisementView.getDescription();
		}
		if(advertisementView.getPictrueId() != null){
			this.pictrueId = advertisementView.getPictrueId();
		}
		if(advertisementView.getTimeEnd() != null){
			this.timeEnd = advertisementView.getTimeEnd();
		}
		if(advertisementView.getTimeStart() != null){
			this.timeStart = advertisementView.getTimeStart();
		}
		if(user != null){
			this.setUpdatedBy(user.getUserId());
		}
		this.setUpdated(new Date());
	}

	public Advertisement(AdvertisementView advertisementView, User user){
		this.pictrueId = advertisementView.getPictrueId();
		this.orderSeq = advertisementView.getOrderSeq();
		this.title = advertisementView.getTitle();
		this.description = advertisementView.getDescription();
		this.setClientId(user.getClientId());
		this.setOrgId(user.getOrgId());
		this.setCreatedBy(user.getUserId());
		this.setCreated(new Date());
	}

	public String getPicOrinName() {
		return picOrinName;
	}

	public void setPicOrinName(String picOrinName) {
		this.picOrinName = picOrinName;
	}

	public Long getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(Long advertisementId) {
		this.advertisementId = advertisementId;
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

	public String getPictruePath() {
		return pictruePath;
	}

	public void setPictruePath(String pictruePath) {
		this.pictruePath = pictruePath;
	}

	public Long getAdvertisementMainId() {
		return advertisementMainId;
	}

	public void setAdvertisementMainId(Long advertisementMainId) {
		this.advertisementMainId = advertisementMainId;
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

	public String getQiniuPath() {
		return qiniuPath;
	}

	public void setQiniuPath(String qiniuPath) {
		this.qiniuPath = qiniuPath;
	}
}