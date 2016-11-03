package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.MaterialView;
import com.clever.common.util.DateTime;

import java.util.Date;


public class MaterialPublishDTView{

	private Long materialPublishId;

	private Long materialBusinessId;

	private Date timeStart;

	private Date timeEnd;

	private Date publishTime;

	private String previewPath;

	private String materialPath;

	private String qiniuPath;

	private String description;

	private int active;

	private String packageName;

	private String version;

	private String fileKindName;

	private int kind;//素材种类,0:未定义;1:apk升级素材,2：微信支付二维码3：支付宝支付二维码......

	private String materialUse;//素材种类显示的名字，根据kind用for循环遍历

	public MaterialPublishDTView(){

	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getMaterialUse() {
		return materialUse;
	}

	public void setMaterialUse(String materialUse) {
		this.materialUse = materialUse;
	}

	public String getFileKindName() {
		return fileKindName;
	}

	public void setFileKindName(String fileKindName) {
		this.fileKindName = fileKindName;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public String getQiniuPath() {
		return qiniuPath;
	}

	public void setQiniuPath(String qiniuPath) {
		this.qiniuPath = qiniuPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}