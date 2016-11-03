package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.MaterialView;
import com.clever.common.client.view.VideoView;

import java.util.Date;


public class Material extends BaseBean {

	private Long materialId;

	private Long tableId;

	private String previewPath;

	private String materialPath;

	private String qiniuPath;

	private Integer playSecond;

	private Long materialSize;

	private Integer type;

	private Integer fileKind;

	private String fileKindName;

	private String description;

	private String original;

	public Material(){

	}

	public Material(Long materialId){
		this.materialId = materialId;
	}

	public Material(User user, MaterialView view){
		if(user != null){
			this.setOrgId(user.getOrgId());
			this.setClientId(user.getClientId());
			this.setCreatedBy(user.getUserId());
			this.setCreated(new Date());
			this.setUpdatedBy(user.getUserId());
			this.setUpdated(new Date());
		}
		if(view != null){
			this.materialId = view.getMaterialId();
			this.tableId = view.getTableId();
			this.materialPath = view.getMaterialPath();
			this.qiniuPath = view.getQiniuPath();
			this.playSecond = view.getPlaySecond();
			this.type = view.getType();
			this.description = view.getDescription();
			this.previewPath = view.getPreviewPath();
			this.original = view.getOriginal();
			this.setActive(view.getActive());
		}
	}

	public String getFileKindName() {
		return fileKindName;
	}

	public void setFileKindName(String fileKindName) {
		this.fileKindName = fileKindName;
	}

	public Material(Long orgId, Integer type){
		this.setOrgId(orgId);
		this.type = type;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
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

	public String getQiniuPath() {
		return qiniuPath;
	}

	public void setQiniuPath(String qiniuPath) {
		this.qiniuPath = qiniuPath;
	}

	public Integer getPlaySecond() {
		return playSecond;
	}

	public void setPlaySecond(Integer playSecond) {
		this.playSecond = playSecond;
	}

	public Long getMaterialSize() {
		return materialSize;
	}

	public void setMaterialSize(Long materialSize) {
		this.materialSize = materialSize;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getFileKind() {
		return fileKind;
	}

	public void setFileKind(Integer fileKind) {
		this.fileKind = fileKind;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}
}