package com.clever.web.resource;

import com.clever.common.domain.User;
import com.clever.web.util.Constants;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;


public class SessionManager implements Serializable{
	//********************************************************************************
	//							后台的session操作
	//********************************************************************************
	
	static public void setCurrentOperator(HttpServletRequest request, User user) {
		request.getSession().setAttribute(Constants.SESSION_USER, user);
	}
	
	static public User getCurrentOperator(HttpServletRequest request) {
		if (request.getSession().getAttribute(Constants.SESSION_USER)!=null){
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER);
			return user;
		}
		return null;
	}
    

}
