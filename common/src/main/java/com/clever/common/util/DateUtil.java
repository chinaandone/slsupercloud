package com.clever.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public final class DateUtil {
	// 默认显示日期的格式
	public static final String DATAFORMAT_STR = "yyyy-MM-dd";
	public static final String DATAFORMAT_YYYY_MM_STR = "yyyy-MM";
	// 默认显示日期的格式
	public static final String YYYY_MM_DATAFORMAT_STR = "yyyy-MM";
	// 默认显示日期时间的格式
	public static final String DATATIMEF_STR = "yyyy-MM-dd HH:mm:ss";
	// 默认显示日期时间的格式 精确到毫秒
	public static final String DATATIMEF_STR_MIS = "yyyyMMddHHmmssSSS";
	// 默认显示日期时间的格式 精确到分钟
	public static final String DATATIMEF_STR_MI = "yyyy-MM-dd HH:mm";

	public static final String DATATIMEF_STR_MDHm = "MM.dd HH:mm";

    public static final String HH_STR = "HH";
	
	//精确到秒
	public static final String DATATIMEF_STR_SEC = "yyyyMMddHHmmss";
	// 默认显示简体中文日期的格式
	public static final String ZHCN_DATAFORMAT_STR = "yyyy年MM月dd日";
	// 默认显示简体中文日期时间的格式
	public static final String ZHCN_DATATIMEF_STR = "yyyy年MM月dd日HH时mm分ss秒";
	// 默认显示简体中文日期时间的格式
	public static final String ZHCN_DATATIMEF_STR_4yMMddHHmm = "yyyy年MM月dd日HH时mm分";

	// 默认显示月份和日期的格式
	public static final String MONTHANDDATE_STR = "MM.dd";

	public static java.text.DateFormat dateFormat = null;

	public static java.text.DateFormat dateTimeFormat = null;
	
	public static java.text.DateFormat dateTimeMISFormat = null;
	
	public static java.text.DateFormat dateTimeMIFormat = null;

	public static java.text.DateFormat zhcnDateFormat = null;

	public static java.text.DateFormat zhcnDateTimeFormat = null;
	
	public static final int ONE_SECOND = 1000;

	private DateUtil() {
	}

	static {		dateFormat = new SimpleDateFormat(DATAFORMAT_STR);
		dateTimeFormat = new SimpleDateFormat(DATATIMEF_STR);
		dateTimeMISFormat = new SimpleDateFormat(DATATIMEF_STR_MIS);
		dateTimeMIFormat = new SimpleDateFormat(DATATIMEF_STR_MI);
		zhcnDateFormat = new SimpleDateFormat(ZHCN_DATAFORMAT_STR);
		zhcnDateTimeFormat = new SimpleDateFormat(ZHCN_DATATIMEF_STR);
	}

	public static Date now() {
		return Calendar.getInstance(Locale.CHINESE).getTime();
	}

	public static Date daysAfter(Date baseDate, int increaseDate) {
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		calendar.setTime(baseDate);
		calendar.add(Calendar.DATE, increaseDate);
		return calendar.getTime();
	}

	public static Date monthAfter(Date baseDate, int increaseMonths) {
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		calendar.setTime(baseDate);
		calendar.add(Calendar.MONTH, increaseMonths);
		return calendar.getTime();
	}
	
	public static Date getInternalDateByDay(Date d, int days) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.DATE, days);
		return now.getTime();
	}

	public static Date getInternalDateByMinute(Date d, int minutes) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		now.add(Calendar.MINUTE, minutes);
		return now.getTime();
	}

	/**
	 * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToDateString(Date date) {
		return dateToDateString(date, DATATIMEF_STR);
	}

	/**
	 * 将Date转换成formatStr格式的字符串
	 * 
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String dateToDateString(Date date, String formatStr) {
        if (date == null) {
            return null;
        }
        java.text.DateFormat df = getDateFormat(formatStr);
		return date!=null?df.format(date):"";
	}

	/**
	 * 按照默认formatStr的格式，转化dateTimeStr为Date类型 dateTimeStr必须是formatStr的形式
	 * 
	 * @param dateTimeStr
	 * @param formatStr
	 * @return
	 */
	public static Date getDate(String dateTimeStr, String formatStr) {
		try {
			if (dateTimeStr == null || dateTimeStr.equals("")) {
				return null;
			}
			java.text.DateFormat sdf = new SimpleDateFormat(formatStr);
			Date d = sdf.parse(dateTimeStr);
			return d;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Date toDate(String dateTimeStr) {
		return getDate(dateTimeStr, DATATIMEF_STR);
	}

	public static String getCurDate() {
		return dateToDateString(Calendar.getInstance().getTime(), DATAFORMAT_STR);
	}
	public static String getMM(int m) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.add(Calendar.MONTH, m);
		return dateToDateString(now.getTime(), "yyyy-MM");
	}
	
	public static String getCurHour() {
		return dateToDateString(Calendar.getInstance().getTime(), HH_STR);
	}

	public static int getThisMonth() {
		Calendar c = Calendar.getInstance(Locale.CHINESE);
		int month = c.get(Calendar.MONTH) + 1;
		return month;

	}

	public static int getThisWeek() {
		Calendar c = Calendar.getInstance(Locale.CHINESE);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		int week = c.get(Calendar.WEEK_OF_YEAR);
		return week;

	}

	public static Date weekStart(Date baseDate) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(baseDate);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return startOneDay(c.getTime());
	}

	public static Date weekEnd(Date baseDate) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(baseDate);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY + 7); // Sunday
		return endOneDay(c.getTime());
	}

	public static Date startOneDay(Date date) {
		try {
			String halfFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(halfFormat + DateFormat.HOUR_START);
		} catch (ParseException e) {
			e.printStackTrace();
			return date;
		}
	}

	public static Date startOneDay(String startDate) {
		try {
			return DateFormat.parse(startDate + DateFormat.HOUR_START);
		} catch (ParseException e) {
			return now();
		}
	}
	
	public static Date endOneDay(String endDate) {
		try {
			return DateFormat.parse(endDate + DateFormat.HOUR_END);
		} catch (ParseException e) {
			return now();
		}
	}

	public static Date endOneDay(Date date) {
		try {
			String halfFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(halfFormat + DateFormat.HOUR_END);
		} catch (ParseException e) {
			e.printStackTrace();
			return date;
		}
	}

	public static Date minutesAfter(Date baseDate, int increaseMinutes) {
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		calendar.setTime(baseDate);
		calendar.add(Calendar.MINUTE, increaseMinutes);
		return calendar.getTime();
	}
	
	public static Date secondsAfter(Date baseDate, int increaseMinutes) {
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		calendar.setTime(baseDate);
		calendar.add(Calendar.SECOND, increaseMinutes);
		return calendar.getTime();
	}
	
	private static java.text.DateFormat getDateFormat(String formatStr) {
		if (formatStr.equalsIgnoreCase(DATAFORMAT_STR)) {
			return dateFormat;
		} else if (formatStr.equalsIgnoreCase(DATATIMEF_STR)) {
			return dateTimeFormat;
		} else if (formatStr.equalsIgnoreCase(ZHCN_DATAFORMAT_STR)) {
			return zhcnDateFormat;
		} else if (formatStr.equalsIgnoreCase(ZHCN_DATATIMEF_STR)) {
			return zhcnDateTimeFormat;
		} else if (formatStr.equalsIgnoreCase(DATATIMEF_STR_MIS)) {
			return dateTimeMISFormat;
		} else if (formatStr.equalsIgnoreCase(DATATIMEF_STR_MI)) {
			return dateTimeMIFormat;
		} else {
			return new SimpleDateFormat(formatStr);
		}
	}

	@SuppressWarnings("deprecation")
	public static String getFirstDateOfMonth(Date now) {
		SimpleDateFormat df1 = new SimpleDateFormat(DATATIMEF_STR);
		Date da = new Date(now.getYear(), now.getMonth(), 01);
		return df1.format(da);
	}

	@SuppressWarnings("deprecation")
	public static String getLastDateOfMonth(Date now) {
		SimpleDateFormat df1 = new SimpleDateFormat(DATATIMEF_STR);
		Date da = new Date(now.getYear(), now.getMonth(), 31);
		return df1.format(da);
	}

	
	/**
	 * 获取本月的第一天 00:00:00.0
	 * @return
	 */
	public static Date getCurrMonthFirstDay(){
		Calendar cal = Calendar.getInstance();   
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);   
        Date beginTime=cal.getTime(); 
        Date start = DateFormat.startOneDay(dateFormat.format(beginTime));
        return start;
	}
	
	/**
	 * 获取本月最后一天23:59:59.0
	 * @return
	 */
	public static Date getCurrMonthLastDay(){
		Calendar cal = Calendar.getInstance();   
		 //当前月的最后一天     
        cal.set( Calendar.DATE, 1 );  
        cal.roll(Calendar.DATE, - 1 );  
        Date endTime=cal.getTime(); 
        Date LastDay = DateFormat.endOneDay(dateFormat.format(endTime));
        return LastDay;
	}
	
	/**
	 * 获取两个毫秒间隔的分钟
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static int getMinutesBetweenMillis(long t1,long t2){
		return (int) ((t1 - t2) / (60 * 1000));
	}
	
	/**
	 * 判断目标时间是否处于某一时间段内
	 * @param target
	 * @param begin
	 * @param end 
	 * @return
	 */
	public static boolean compareTargetTime(Date target,String begin,String end){
		//格式化时间 暂时不考虑传入参数的判断，其他地方如果要调用，最好扩展判断一下入参问题
		String targetTime = dateToDateString(target,DATATIMEF_STR).substring(11);//HH:mm:ss
		if (targetTime.compareTo(begin) >= 0 && end.compareTo(targetTime) >= 0){ 
			  return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param time1
	 * @param time2
	 * @return   time1 小于 time 2  返回 true
	 */
	public static boolean compareTime(Date time1,Date time2){
		long start =  time1.getTime();
        long end = time2.getTime();
        if(start < end){
        	return true;
        }
		
		return false;
	}
	
	/** 
	* 取得两个时间段的时间间隔 
	* return t2 与t1的间隔天数 
	* throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常 
	*/ 
	public static int getBetweenDays(String t1,String t2) throws ParseException{ 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		int betweenDays = 0; 
		Date d1 = format.parse(t1); 
		Date d2 = format.parse(t2); 
		Calendar c1 = Calendar.getInstance(); 
		Calendar c2 = Calendar.getInstance(); 
		c1.setTime(d1); 
		c2.setTime(d2); 
		// 保证第二个时间一定大于第一个时间 
		if(c1.after(c2)){ 
			c1 = c2; 
			c2.setTime(d1); 
		} 
		int betweenYears = c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR); 
		betweenDays = c2.get(Calendar.DAY_OF_YEAR)-c1.get(Calendar.DAY_OF_YEAR); 
		for(int i=0;i<betweenYears;i++){ 
			c1.set(Calendar.YEAR,(c1.get(Calendar.YEAR)+1)); 
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR); 
		} 
		return betweenDays; 
	}
	
	/**
	 * 格式化时间  yyyy-MM-dd
	 * @return
	 */
	public static String getFormatDate(Date date){
		return dateFormat.format(date);
	}
	
	/**
	 * 按照默认formatStr的格式，转化dateTimeStr为Date类型 dateTimeStr必须是formatStr的形式
	 * 
	 * @param dateTimer
	 * @param formatStr
	 * @return
	 */
	public static Date getFormatDate(Date dateTimer, String formatStr) {
		try {
			if (dateTimer == null) {
				return null;
			}
			java.text.DateFormat sdf = new SimpleDateFormat(formatStr);
			String timeStr = sdf.format(dateTimer);
			Date formateDate = sdf.parse(timeStr);
			return formateDate;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取两个时间之间相差的天数
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long getQuot(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}
	
	/**
	 * 获取和当前时间相差的分钟数
	 * @param begin
	 * @return
	 */
	public static long getDiffenceValue(Date begin){
		long value = 0;
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		value = (now.getTime()-begin.getTime())/1000/60;
		return value;
	}
	
	public static long getMillsBetweenTwoDate(Date date1,Date date2){
		return date1.getTime()-date2.getTime();
	}
	
	/**
	 * 求多少天前/后的日期
	 * 
	 * @param field 单位：年，月，日
	 * @param day  多少天
	 * @return
	 */
	public static final Date addDate(int field,int day){
		Calendar nowCalendar = Calendar.getInstance(Locale.CHINESE);
		nowCalendar.setTime(DateUtil.now());
		nowCalendar.add(field, day);
		return  nowCalendar.getTime();
	}

    /**
     * 获取订单编号
     *
     * @return
     */
    public static String getOrderNum() {
        Calendar nowCalendar = Calendar.getInstance(Locale.CHINESE);
        nowCalendar.setTime(DateUtil.now());
        return dateTimeMISFormat.format(nowCalendar.getTime())+RandomUtil.threeRandom();
    }
}


