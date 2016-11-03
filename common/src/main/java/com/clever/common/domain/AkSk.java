package com.clever.common.domain;

import com.clever.common.bean.BaseBean;


public class AkSk extends BaseBean {

	private Long akSkId;

	private String accessKey;

	private String secretKey;

	private String companyName;

	private String companyIp;

	public AkSk(){

	}

	public AkSk(String accessKey){
		this.accessKey = accessKey;
	}

	public Long getAkSkId() {
		return akSkId;
	}

	public void setAkSkId(Long akSkId) {
		this.akSkId = akSkId;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyIp() {
		return companyIp;
	}

	public void setCompanyIp(String companyIp) {
		this.companyIp = companyIp;
	}
}