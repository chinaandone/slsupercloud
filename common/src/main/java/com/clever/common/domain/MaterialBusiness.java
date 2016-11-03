package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.MaterialView;
import com.clever.common.util.DateTime;

import java.util.Date;


public class MaterialBusiness extends BaseBean {

	private Long materialBusinessId;

	private Long materialId;

	private Long tableId;

	private Integer kind;//素材种类,0:未定义;1:apk升级素材

	private String previewPath;

	private String materialPath;

	private String qiniuPath;

	private Long materialSize;

	private Date timeStart;

	private Date timeEnd;

	private Integer type;

	private String description;

	private String packageName;//包名

	private String version;//版本号

	private Integer playSecond;

	private String fileKindName; //文件类型名称

	private String original;//原始文件名

	public MaterialBusiness(){

	}

	public MaterialBusiness(Long materialId){
		this.materialId = materialId;
	}

	public MaterialBusiness(User user, MaterialView view){
		if(user != null){
			this.setOrgId(user.getOrgId());
			this.setClientId(user.getClientId());
			this.setCreatedBy(user.getUserId());
			this.setCreated(new Date());
			this.setUpdatedBy(user.getUserId());
			this.setUpdated(new Date());
		}
		if(view != null){
			this.materialBusinessId = view.getMaterialBusinessId();
			this.materialId = view.getMaterialId();
			this.tableId = view.getTableId();
			this.kind = view.getKind();
			this.type = view.getType();
			this.timeEnd = DateTime.toMillis(view.getTimeEnd());
			this.timeStart = DateTime.toMillis(view.getTimeStart());
			this.description = view.getDescription();
			this.packageName = view.getPackageName();
			this.version = view.getVersion();
			this.setActive(view.getActive());
		}
	}

	public MaterialBusiness(Long materialBusinessId, Long orgId, Integer type){
		this.setOrgId(orgId);
		this.type = type;
		this.materialBusinessId = materialBusinessId;
	}

	public MaterialBusiness(String fileKindName){
		this.fileKindName = fileKindName;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getFileKindName() {
		return fileKindName;
	}

	public void setFileKindName(String fileKindName) {
		this.fileKindName = fileKindName;
	}

	public Integer getPlaySecond() {
		return playSecond;
	}

	public void setPlaySecond(Integer playSecond) {
		this.playSecond = playSecond;
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