package com.clever.common.client.view;

import com.clever.common.bean.BaseBean;
import com.clever.common.domain.User;

import java.util.Date;

/**
 * material,materialBusiness,materialPublish三个接口通用的前端输入接收类型
 */
public class MaterialView{

	private Long materialId; //素材ID

	private Long materialPublishId;//素材推送ID

	private Long materialBusinessId; //素材业务ID

	private Long tableId;//餐桌ID（不需要传入）

	private String previewPath; //素材预览路径

	private String materialPath; //素材存储路径

	private String qiniuPath;//素材七牛路径

	private Integer playSecond;//素材播放时长(如果素材能播放的话)

	private Integer type;//素材应用类型，0：商户，1：商业（默认0）

	private Integer kind;//素材种类,0:未定义;1:apk升级素材,2：微信支付二维码3：支付宝支付二维码......

	private String fileKindName;//素材文件类型名称;

	private Long timeStart;//素材业务有效期开始日期时间

	private Long timeEnd;//素材业务有效期截止日期时间

	private String description;//描述

	private String original;//原文件名

	private String packageName;//包名

	private String version;//版本号

	private Long publishTime;//素材推送发布日期

	private int active;//是否启用

	public MaterialView(){

	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Long getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Long publishTime) {
		this.publishTime = publishTime;
	}

	public Long getMaterialPublishId() {
		return materialPublishId;
	}

	public void setMaterialPublishId(Long materialPublishId) {
		this.materialPublishId = materialPublishId;
	}

	public String getFileKindName() {
		return fileKindName;
	}

	public void setFileKindName(String fileKindName) {
		this.fileKindName = fileKindName;
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

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
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
}