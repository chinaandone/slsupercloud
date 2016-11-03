package com.clever.common.util;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyStringUtils extends StringUtils {
	
	/** 把字符串里的数字都提取出来	*/
	public static String collectNumber(String str) {
		if (str == null) {
			return str;
		}
        char[] chars = str.toCharArray();
        int length = chars.length;
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < length; i++){
			 if (chars[i] >= '0' && chars[i] <= '9'){
				 sb.append(chars[i]);
			 }
		}
		return sb.toString();
	}
	
	/** 判断str字符串中是否包含searchStrArray数组中任一字符串	*/
	public static boolean containsAnyIgnoreCase(String str, String[] searchStrArray) {
        if (str == null || str.length() == 0 || searchStrArray == null || searchStrArray.length == 0) {
            return false;
        }
		for (String searchStr : searchStrArray) {
			if (containsIgnoreCase(str, searchStr.trim())){
				return true;
			}
		}
		return false;
	}

	/**按指定长度进行字符串截断*/
	public static String cut(String str, int len) {
		if (str == null) {
			return "";
		}
		if (str.length() > len){
			return str.substring(0, len) + "...";
		}
		return str;
	}

	/**判断字符串是否包含中文*/
	public static boolean isContainChinese(String str){
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

}
