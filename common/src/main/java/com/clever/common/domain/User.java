package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.UserView;
import com.clever.common.util.DateTime;

import java.io.Serializable;
import java.util.Date;


public class User extends BaseBean {

	private Long userId;

	private Long partnerId;

	private String loginId;

	private String password;

	private String clientName;

	private String orgName;

	private String username;

	private String phone;

	private String mobile;

	private String email;

	private String roleType;

	private Date birthday;

	public User(){

	}

	public User(Long userId){
		this.setUserId(userId);
	}

	public User(User user){
		if(user != null){
			this.loginId = user.getLoginId();
			this.password = user.getPassword();
		}
	}

	public User(UserView userView,User user){
		if(userView != null){
			this.setUserId(userView.getUserId());
			this.setOrgId(userView.getOrgId());
			this.setClientId(userView.getClientId());
			this.setLoginId(userView.getLoginId());
			this.setUsername(userView.getName());
			this.setPassword(userView.getPassword());
			this.setPhone(userView.getPhone());
			this.setMobile(userView.getMobile());
			this.setBirthday(DateTime.toMillis(userView.getBirthday()));
			this.setEmail(userView.getEmail());
			this.setRoleType(userView.getRoleType());
		}
		if(user != null){
			this.setCreatedBy(user.getUserId());
			this.setUpdatedBy(user.getUserId());
		}
		Date tempDate = new Date();
		this.setCreated(tempDate);
		this.setUpdated(tempDate);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}