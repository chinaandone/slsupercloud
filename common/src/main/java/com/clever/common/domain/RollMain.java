package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.RollMainView;
import com.clever.common.util.DateTime;

import java.util.Date;
import java.util.List;


public class RollMain extends BaseBean {

	private Long rollMainId;

	private Long pictrueId;

	private Integer orderSeq;

	private String description;

	private Date startTime;

	private Date endTime;

	private Integer type;

	private Integer rollTime;

	private String pictruePath;

	private String qiniuPath;

	private String picOrinName;//图片原始文件名

	//detailText_data
	private String title;

	private String descriptionText;

	private List<Advertisement> advertisementList;

	private List<RollDetail> rollDetailList;

	private Integer isApp;//是否为后端访问

	public RollMain(){

	}

	public RollMain(Long clientId, Long orgId, Integer type, Integer isApp, Long rollMainId, String title){
		this.setClientId(clientId);
		this.setOrgId(orgId);
		this.type = type;
		this.isApp = isApp;
		this.rollMainId = rollMainId;
		this.title = title;
	}

	public RollMain(RollMainView rollMainView, User user){
		if(rollMainView != null){
			this.rollMainId = rollMainView.getRollMainId();
			this.pictrueId = rollMainView.getPictrueId();
			this.orderSeq = rollMainView.getOrderSeq();
			this.startTime = DateTime.toMillis(rollMainView.getStartTime());
			this.endTime = DateTime.toMillis(rollMainView.getEndTime());
			this.rollTime = rollMainView.getRollTime();
		}
		if(user != null){
			this.setClientId(user.getClientId());
			this.setOrgId(user.getOrgId());
			this.setCreatedBy(user.getUserId());
			this.setUpdatedBy(user.getUserId());
		}
		this.setCreated(new Date());
		this.setUpdated(new Date());
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getRollTime() {
		return rollTime;
	}

	public void setRollTime(Integer rollTime) {
		this.rollTime = rollTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPictruePath() {
		return pictruePath;
	}

	public void setPictruePath(String pictruePath) {
		this.pictruePath = pictruePath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescriptionText() {
		return descriptionText;
	}

	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	public List<Advertisement> getAdvertisementList() {
		return advertisementList;
	}

	public void setAdvertisementList(List<Advertisement> advertisementList) {
		this.advertisementList = advertisementList;
	}

	public List<RollDetail> getRollDetailList() {
		return rollDetailList;
	}

	public void setRollDetailList(List<RollDetail> rollDetailList) {
		this.rollDetailList = rollDetailList;
	}

	public Integer getIsApp() {
		return isApp;
	}

	public void setIsApp(Integer isApp) {
		this.isApp = isApp;
	}

	public String getQiniuPath() {
		return qiniuPath;
	}

	public void setQiniuPath(String qiniuPath) {
		this.qiniuPath = qiniuPath;
	}

	public String getPicOrinName() {
		return picOrinName;
	}

	public void setPicOrinName(String picOrinName) {
		this.picOrinName = picOrinName;
	}
}