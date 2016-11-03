package com.clever.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.clever.common.client.view.RollMainView;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class FastJsonUtil {
	private final static Logger logger = Logger.getLogger(FastJsonUtil.class);
	


	private static Map processMapObject(Map valueObjMap, Map clazzMap) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		for (Object key : valueObjMap.keySet()) {
			Object valueObj = valueObjMap.get(key);
			if (valueObj == null || valueObj.toString() == null) {
				dataMap.put(key.toString(), null);
				continue;
			}
			
			String strKey = null;
			if(key instanceof String){
				strKey = key.toString();
				//只能解析key为string的entry
			}else{
				dataMap.put(key.toString(), valueObj);
				continue;
			}

			Class clazz = (Class) clazzMap.get(strKey);
			Serializable seriObj = processSameTypeObject(valueObj, clazz);
			dataMap.put(strKey, seriObj);
		}
		
		return dataMap;
	}

	private static Serializable processMapObject(Map valueObjMap, Class clazz) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		for (Object key : valueObjMap.keySet()) {
			Object valueObj = valueObjMap.get(key);
			if (valueObj == null) {
				dataMap.put(key.toString(), null);
				continue;
			}
			
			String strKey = null;
			if(key instanceof String){
				strKey = key.toString();
				//只能解析key为string的entry
			}else{
				dataMap.put(key.toString(), valueObj);
				continue;
			}

			Serializable seriObj = null;
			try {
				seriObj = toJavaObject((JSONObject)valueObj, clazz);
			} catch (Exception e) {
				logger.warn("---processMapObject() exception:" + e.getMessage());
			}
			dataMap.put(strKey, seriObj);
		}
		
		return (Serializable) dataMap;
	}

	private static Serializable processSameTypeObject(Object valueObj, Class clazz) {
		Serializable seriObj = null;
		
		if(clazz != null){
			if (valueObj instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray) valueObj;
				List<Serializable> valueList = toJavaObjectList(jsonArray, clazz);
				seriObj = (Serializable)valueList;
			} else if (valueObj instanceof JSONObject) {
				Set entrySet = ((JSONObject) valueObj).entrySet();
				if (entrySet != null && entrySet.size() > 0) {
					Entry firstEntry = (Entry) entrySet.toArray()[0];
					Object firstValue = firstEntry.getValue();
					if (firstValue != null && !(firstValue instanceof JSONObject)) {
						try {
							seriObj = toJavaObject((JSONObject)valueObj, clazz);
						} catch (Exception e) {
							logger.warn("---processSameTypeObject() exception:" + e.getMessage());
						}
					} else{
						Map valueObjMap = (Map) valueObj;
						seriObj = processMapObject(valueObjMap, clazz);
					}
				} else{
					try {
						seriObj = toJavaObject((JSONObject)valueObj, clazz);
					} catch (Exception e) {
						logger.warn("---processSameTypeObject() exception:" + e.getMessage());
					}
				}
			} else {
				//未纳入解析范围的JSON数据类型
				seriObj = toSerializable(valueObj);
			}
		}else{
			seriObj = toSerializable(valueObj);
		}
		
		return seriObj;
	}

	private static Serializable toSerializable(Object valueObj) {
		Serializable seriObj = null;
		try {
			seriObj = (Serializable) valueObj;
		} catch (Exception e) {
			logger.warn("---toSerializable() exception:" + e.getMessage());
		}
		return seriObj;
	}

	private static List<Serializable> toJavaObjectList(JSONArray jsonArray, Class clazz){
		List<Serializable> valueList = new ArrayList<Serializable>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject value = (JSONObject) jsonArray.get(i);
			Serializable obj = null;
			try {
				obj = toJavaObject(value, clazz);
				valueList.add(obj);
			} catch (Exception e) {
				logger.warn("---toJavaObjectList() exception:" + e.getMessage());
			}
		}
		return valueList;
	}

	public static Serializable toJavaObject(JSONObject value, Class clazz) throws Exception{
		Serializable javaObj = null;
		if (value == null || value.toString() == null) {
			return null;
		}
		
		String valueJson = value.toString();
		javaObj = (Serializable) JSON.parseObject(valueJson, clazz);
		return javaObj;
	}


}
