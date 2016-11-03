package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.OrgView;
import com.clever.common.domain.enums.PictrueKindType;
import com.clever.common.util.DateTime;

import java.util.Date;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 *       chay.ni@clever-m.com
 * Date: 2016-03-03
 * Time: 15:11
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 * 			<p>20160303增加Org(Long clientId,Long OrgId)构造函数</p>
 */
public class Org extends BaseBean {

	private Long locationId;

	private String name;

	private String clientName;

	private String description;

	private String phone;

	private String fax;

	private Date dateFound;

	private int active;

	public Org(){

	}

	public Org(Long clientId,Long orgId){
		if( null != clientId ){
			this.setClientId(clientId);
		}
		if( null != orgId ){
			this.setOrgId(orgId);
		}
	}

	public Org(OrgView orgView,User user){
		if( null != orgView ){
			this.setOrgId(orgView.getOrgId()) ;
			this.setClientId(orgView.getClientId());
			if(null != orgView.getDateFound() && !"".equals(orgView.getDateFound()) ) {
				this.dateFound = DateTime.toMillis(orgView.getDateFound());
			}
			this.description = orgView.getDescription();
			this.fax = orgView.getFax();
			this.locationId = orgView.getLocationId();
			this.name = orgView.getName();
			this.phone = orgView.getPhone();
			this.setActive(orgView.getActive());
		}

		if(null != user){
			this.setCreatedBy(user.getUserId());
			this.setUpdatedBy(user.getUserId());
		}

		Date tempDate = new Date();
		this.setCreated(tempDate);
		this.setUpdated(tempDate);
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Date getDateFound() {
		return dateFound;
	}

	public void setDateFound(Date dateFound) {
		this.dateFound = dateFound;
	}

	@Override
	public int getActive() {
		return active;
	}

	@Override
	public void setActive(int active) {
		this.active = active;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
}