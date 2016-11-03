package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.MaterialView;
import com.clever.common.util.DateTime;

import java.util.Date;


public class MaterialBusinessDTView{

	private Long materialBusinessId;

	private Long materialId;

	private String previewPath;

	private String materialPath;

	private String qiniuPath;

	private Long materialSize;

	private String description;

	private String packageName;//包名

	private String version;//版本号

	private String fileKindName; //文件类型名称

	private int active;

	private int kind;//素材种类,0:未定义;1:apk升级素材,2：微信支付二维码3：支付宝支付二维码......

	private String materialUse;//素材种类显示的名字，根据kind用for循环遍历

	private Date created;

	public MaterialBusinessDTView(){

	}

	public String getMaterialUse() {
		return materialUse;
	}

	public void setMaterialUse(String materialUse) {
		this.materialUse = materialUse;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getMaterialBusinessId() {
		return materialBusinessId;
	}

	public void setMaterialBusinessId(Long materialBusinessId) {
		this.materialBusinessId = materialBusinessId;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
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

	public Long getMaterialSize() {
		return materialSize;
	}

	public void setMaterialSize(Long materialSize) {
		this.materialSize = materialSize;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}
}