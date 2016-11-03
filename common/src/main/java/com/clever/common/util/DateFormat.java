package com.clever.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
	/**
	 * 如果只在页面上使用，用jquery.dateFormat-1.0.js里面的$.format.date(time, 'yyyy-MM-dd
	 * HH:mm:ss')方法
	 */

	private static final String EMPTY = "";
	public static final String UNKNOWN_DATE = "N/A";
	public static final String PATTERN_FULL_MILI_SECOND_FORMAT = "MM-dd HH:mm:ss.SSS";
	public static final String PATTERN_FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_NOT_EMPTY_FORMAT = "yyyyMMddHHmmss";
	public static final String PATTERN_COMMON_DATE_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String PATTERN_HALF_FORMAT = "yyyy-MM-dd";
	public static final String PATTERN_TIME_FORMAT = "HH:mm";
	public static final String PATTERN_SIMPLE_DATE_FORMAT = "MM-dd HH:mm";
	public static final String PATTERN_CN_FORMAT = "yyyy年MM月dd日";
	public static final String PATTERN_MONTH_FORMAT = "yyyy-MM";
	public  static final String HOUR_END = " 23:59:59";
	public  static final String HOUR_START = " 00:00:00";
	public static final String PATTERN_NO_DELIMITER_FORMAT = "yyyyMMdd";
	
	public static final SimpleDateFormat fullMiliSecondFormat = new SimpleDateFormat(PATTERN_FULL_MILI_SECOND_FORMAT);
	public static final SimpleDateFormat fullDateFormat = new SimpleDateFormat(PATTERN_FULL_DATE_FORMAT);
	public static final SimpleDateFormat notEmptyFormat = new SimpleDateFormat(PATTERN_NOT_EMPTY_FORMAT);
	public static final SimpleDateFormat commonDateFormat = new SimpleDateFormat(PATTERN_COMMON_DATE_FORMAT);
	public static final SimpleDateFormat halfFormat = new SimpleDateFormat(PATTERN_HALF_FORMAT);
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat(PATTERN_TIME_FORMAT);
	public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_SIMPLE_DATE_FORMAT);
	public static final SimpleDateFormat cnFormat = new SimpleDateFormat(PATTERN_CN_FORMAT);
	public static final SimpleDateFormat monthFormat = new SimpleDateFormat(PATTERN_MONTH_FORMAT);

	public static Date parse(String time) throws ParseException {
		return fullDateFormat.parse(time);
	}

	public static final String simpleFormat(Date date) {
		return doFormat(simpleDateFormat, date);
	}

	public static final String halfFormat(Date date) {
		return doFormat(halfFormat, date);
	}

	public static final String fullFormat(Date date) {
		return doFormat(fullDateFormat, date);
	}

	public static String cnFormat(Date date) {
		return doFormat(cnFormat, date);
	}

	public static String commonFormat(Date date) {
		return doFormat(commonDateFormat, date);
	}

	public static String timeFormat(Date date) {
		return doFormat(timeFormat, date);
	}

	public static String fullMiliSecondFormat(Date date) {
		return doFormat(fullMiliSecondFormat, date);
	}

	private static String doFormat(SimpleDateFormat format, Date date) {
		if (date == null) {
			return EMPTY;
		}
		return format.format(date);
	}

	public static Date halfFormatParse(String time) throws ParseException {
		return halfFormat.parse(time);
	}

	public static Date endOneDay(Date date) {
		try {
			String halfFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(halfFormat + HOUR_END);
		} catch (ParseException e) {
			return date;
		}
	}
	
	public static Date startOneDay(Date date) {
		try {
			String halfFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(halfFormat + HOUR_START);
		} catch (ParseException e) {
			return date;
		}
	}
	
	
	public static Date startOneDay(String startDate) {
		try {
			return DateFormat.parse(startDate + HOUR_START);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	public static Date endOneDay(String endDate) {
		try {
			return DateFormat.parse(endDate + HOUR_END);
		} catch (ParseException e) {
			return new Date();
		}
	}

	public static final String notEmptyFormat(Date date) {
		return doFormat(notEmptyFormat, date);
	}

}
