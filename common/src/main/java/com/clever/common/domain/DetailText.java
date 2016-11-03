package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.RollMainView;

import java.util.Date;
import java.util.List;


public class DetailText extends BaseBean {

	private Long detailTextId;

	private Long rollMainId;

	private Long pictrueId;

	private String title;

	private String description;

	private List<Advertisement> advertisementList;

	private List<RollDetail> rollDetailList;

	public DetailText(){
	}

	public DetailText(Long orgId, Long rollMainId){
		this.setOrgId(orgId);
		this.rollMainId = rollMainId;
	}

	public DetailText(RollMainView rollMainView, RollMain rollMain, User user){
		if(rollMainView != null){
			this.pictrueId = rollMainView.getPictrueId();
			this.title = rollMainView.getTitle();
			this.description = rollMainView.getDescription();
		}
		if(rollMain != null){
			this.rollMainId = rollMain.getRollMainId();
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

	public Long getDetailTextId() {
		return detailTextId;
	}

	public void setDetailTextId(Long detailTextId) {
		this.detailTextId = detailTextId;
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

}