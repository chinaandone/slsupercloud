package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.RollDetailView;

import java.util.Date;
import java.util.List;


public class RollDetail extends BaseBean {

	private Long rollDetailId;

	private Long rollMainId;

	private Long pictrueId;

	private Long detailTextId;

	private Integer orderSeq;

	private Integer rollTime;

	private String pictruePath;

	private String qiniuPath;

	private String picOrinName;//图片原始文件名

	public RollDetail(){
	}

	public RollDetail(Long orgId, Long rollMainId, Long rollDetailId){
		this.setOrgId(orgId);
		this.rollMainId = rollMainId;
		this.rollDetailId = rollDetailId;
	}

	public RollDetail(RollDetailView rollDetailView, RollMain rollMain, DetailText detailText, User user){
		if(rollDetailView != null){
			this.pictrueId = rollDetailView.getPictrueId();
			this.orderSeq = rollDetailView.getOrderSeq();
		}
		if(rollMain != null){
			this.rollMainId = rollMain.getRollMainId();
		}
		if(detailText != null){
			this.detailTextId = detailText.getDetailTextId();
		}
		if(user != null){
			this.setClientId(user.getClientId());
			this.setOrgId(user.getOrgId());
			this.setCreatedBy(user.getUserId());
			this.setUpdatedBy(user.getUserId());
		}
		this.setUpdated(new Date());
		this.setCreated(new Date());
	}

	public String getPicOrinName() {
		return picOrinName;
	}

	public void setPicOrinName(String picOrinName) {
		this.picOrinName = picOrinName;
	}

	public Long getRollDetailId() {
		return rollDetailId;
	}

	public void setRollDetailId(Long rollDetailId) {
		this.rollDetailId = rollDetailId;
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

	public Long getDetailTextId() {
		return detailTextId;
	}

	public void setDetailTextId(Long detailTextId) {
		this.detailTextId = detailTextId;
	}

	public Integer getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Integer getRollTime() {
		return rollTime;
	}

	public void setRollTime(Integer rollTime) {
		this.rollTime = rollTime;
	}

	public String getPictruePath() {
		return pictruePath;
	}

	public void setPictruePath(String pictruePath) {
		this.pictruePath = pictruePath;
	}

	public String getQiniuPath() {
		return qiniuPath;
	}

	public void setQiniuPath(String qiniuPath) {
		this.qiniuPath = qiniuPath;
	}
}