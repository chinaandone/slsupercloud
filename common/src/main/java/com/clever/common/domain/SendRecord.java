package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.domain.enums.PictrueKindType;
import com.clever.common.util.DateTime;

import java.util.Date;


public class SendRecord extends BaseBean {

	private Long recordId;

	private Long tableId;

	private Integer flag;

	private String templateNum;

	private String description;

	private Integer partner;

	public SendRecord(){

	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getTemplateNum() {
		return templateNum;
	}

	public void setTemplateNum(String templateNum) {
		this.templateNum = templateNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPartner() {
		return partner;
	}

	public void setPartner(Integer partner) {
		this.partner = partner;
	}
}