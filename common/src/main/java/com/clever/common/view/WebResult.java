package com.clever.common.view;

import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class WebResult {
	public static final String KEY_ERROR = "error";
	public static final String KEY_MESSAGE = "message";

	private static final ModelAndView generateNavigationData(String page, Map<String, Object> data, int... index) {
		if (index != null) {
			for (int i = 0; i < index.length; i++) {
				data.put("index_" + i, index[i]);
			}
		}
		return new ModelAndView(page, data);
	}

	public static final ModelAndView success(String page, int... index) {
		return generateNavigationData(page, new HashMap<String, Object>(), index);
	}

	public static final ModelAndView success(String page, Map<String, Object> data, int... index) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.putAll(data);
		return generateNavigationData(page, p, index);
	}

	public static final ModelAndView success(String page, String message, int... index) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put(KEY_MESSAGE, message);
		return generateNavigationData(page, p, index);
	}
	
	public static final ModelAndView success(String page, String message, Map<String, Object> data, int... index) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put(KEY_MESSAGE, message);
		p.putAll(data);
		return generateNavigationData(page, p, index);
	}

	public static final ModelAndView failed(String page, String error, int... index) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put(KEY_ERROR, error);
		return generateNavigationData(page, p, index);
	}
	
	public static final ModelAndView failed(String page, String error, Map<String, Object> data, int... index) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put(KEY_ERROR, error);
		p.putAll(data);
		return generateNavigationData(page, p, index);
	}
	
	public static final ModelAndView failed(String page, Map<String, Object> data, int... index) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.putAll(data);
		return generateNavigationData(page, p, index);
	}
}
