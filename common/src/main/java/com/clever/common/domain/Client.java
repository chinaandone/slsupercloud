package com.clever.common.domain;

import com.clever.common.bean.BaseBean;
import com.clever.common.client.view.ClientView;
import com.clever.common.util.DateTime;

import java.util.Date;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-03-07
 * Time: 10:47
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class Client extends BaseBean {

	//BaseBean已经包含基本公有参数：clientId，orgId,active,createdBy,created,updatedBy,updated
	private String name;

	private String description;

	public Client(){

	}

	public Client(Long clientId, Long orgId){
		if( null != clientId ){
			this.setClientId(clientId);
		}
		if( null != orgId ){
			this.setOrgId(orgId);
		}
	}

	public Client(ClientView clientView, User user){
		if( null != clientView ){
			this.setClientId(clientView.getClientId());
			this.description = clientView.getDescription();
			this.name = clientView.getName();
			this.setActive(clientView.getActive());
		}

		if(null != user){
			this.setCreatedBy(user.getUserId());
			this.setUpdatedBy(user.getUserId());
		}

		Date tempDate = new Date();
		this.setCreated(tempDate);
		this.setUpdated(tempDate);
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
}