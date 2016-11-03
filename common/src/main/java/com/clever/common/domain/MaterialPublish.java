package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.MaterialView;
import com.clever.common.util.DateTime;

import java.util.Date;


public class MaterialPublish extends BaseBean {

	private Long materialPublishId;

	private Long materialBusinessId;

	private Integer kind;//素材种类,0:未定义;1:apk升级素材

	private Date timeStart;

	private Date timeEnd;

	private Date publishTime;

	private String previewPath;

	private String materialPath;

	private String original;

	private String orgName;

	private String description;

	public MaterialPublish(){

	}

	public MaterialPublish(Long materialPublishId, Long materialBusinessId, Long clientId, Long orgId, Date publishTime, String description,
						String original, Integer kind, Long timeStart, Long timeEnd,Long UserId,Date now){
		this.materialPublishId = materialPublishId;
		this.materialBusinessId = materialBusinessId;
		this.setOrgId(orgId);
		this.setClientId(clientId);
		this.publishTime = publishTime;
		this.description = description;
		this.original = original;
		this.kind = kind;
		this.timeStart = DateTime.toMillis(timeStart);
		this.timeEnd = DateTime.toMillis(timeEnd);
		this.setCreatedBy(UserId);
		this.setCreated(now);
		this.setUpdatedBy(UserId);
		this.setUpdated(now);
	}

	public MaterialPublish(User user, MaterialView view){
		Date now = new Date();
		if(user != null){
			this.setOrgId(user.getOrgId());
			this.setClientId(user.getClientId());
			this.setCreatedBy(user.getUserId());
			this.setCreated(now);
			this.setUpdatedBy(user.getUserId());
			this.setUpdated(now);
		}
		if(view != null){
			this.materialPublishId = view.getMaterialPublishId();
			this.materialBusinessId = view.getMaterialBusinessId();
			this.kind = view.getKind();
			this.timeStart = DateTime.toDate(view.getTimeStart());
			this.timeEnd = DateTime.toDate(view.getTimeEnd());
			this.publishTime = now;
			this.description = view.getDescription();
		}
	}

	public Long getMaterialPublishId() {
		return materialPublishId;
	}

	public void setMaterialPublishId(Long materialPublishId) {
		this.materialPublishId = materialPublishId;
	}

	public Long getMaterialBusinessId() {
		return materialBusinessId;
	}

	public void setMaterialBusinessId(Long materialBusinessId) {
		this.materialBusinessId = materialBusinessId;
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

	public String getMaterialPath() {
		return materialPath;
	}

	public void setMaterialPath(String materialPath) {
		this.materialPath = materialPath;
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
}