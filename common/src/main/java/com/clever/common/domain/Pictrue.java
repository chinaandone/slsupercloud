package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.domain.enums.PictrueKindType;
import com.clever.common.util.DateTime;

import java.util.Date;


public class Pictrue extends BaseBean {

	private Long pictrueId;

	private String pictruePath;

	private String qiniuPath;

	private Integer year;

	private Integer month;

	private Integer day;

	private Integer kind;

	private String original;

	private Integer isFile;

	private String folderName;

	private Integer type;

	public Pictrue(){

	}

	public Pictrue(Long pictrueId){
		this.pictrueId = pictrueId;
	}

	public Pictrue(String pictruePath){
		this.pictruePath = pictruePath;
	}

	public Pictrue(Long clientId, Long orgId, String pictruePath, String qiniuPath, Integer kind, String original, Integer type){
		this.setClientId(clientId);
		this.setOrgId(orgId);
		this.pictruePath = pictruePath;
		this.qiniuPath = qiniuPath;
		this.original = original;
		this.kind = kind;
		this.setCreatedBy(orgId);
		this.year = DateTime.getYear();
		this.month = DateTime.getYearMonth();
		this.day = DateTime.getYearMonthDay();
		this.setCreated(new Date());
		this.type = type;

	}

	public Long getPictrueId() {
		return pictrueId;
	}

	public void setPictrueId(Long pictrueId) {
		this.pictrueId = pictrueId;
	}

	public String getPictruePath() {
		return pictruePath;
	}

	public void setPictruePath(String pictruePath) {
		this.pictruePath = pictruePath;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public Integer getIsFile() {
		return isFile;
	}

	public void setIsFile(Integer isFile) {
		this.isFile = isFile;
	}

	public String getFolderName() {
		if(folderName != null){
			return folderName;
		}else{
			return PictrueKindType.getName(kind);
		}
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getQiniuPath() {
		return qiniuPath;
	}

	public void setQiniuPath(String qiniuPath) {
		this.qiniuPath = qiniuPath;
	}
}