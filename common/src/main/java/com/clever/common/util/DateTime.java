package com.clever.common.util;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 * @author enva.liang@clever-m.com
 * 时间类型定义：<br/>
 * 1、normalDate："yyyy-MM-dd"，String<br/>
 * 2、normalDateTime:"yyyy-MM-dd HH:mm:ss"，String<br/>
 * 2、millis： 毫秒格式，Long<br/>
 * 3、shortDate： 20151218，Integer<br/>
 * 4、shortDateTime： 20151218101203，Long<br/>
 * 5、date： Date对象<br/>
 */
public class DateTime{
	
	private final static Logger logger = Logger.getLogger(DateTime.class);
	
	/**	yyyyMM	*/
	public final static String PATTERN_SHORTMONTH = "yyyyMM";
	/**	yyyy-MM	*/
	public final static String PATTERN_YEAR_MONTH = "yyyy-MM";
	/**	yyyy-MM-dd	*/
	public final static String PATTERN_DATE = "yyyy-MM-dd";
	/**	yyyyMMdd	*/
	public final static String PATTERN_SHORTDATE = "yyyyMMdd";
	/**	yyyy-MM-dd HH:mm:ss	*/
	public final static String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
	/**	yyyy-MM-dd_HH-mm-ss	用于文件名的格式化*/
	public final static String PATTERN_NAME_DATETIME = "yyyy-MM-dd_HH-mm-ss";
	/**	yyyyMMddHHmmss	*/
	public final static String PATTERN_SHORTDATETIME = "yyyyMMddHHmmss";
	/**	HH:mm:ss	*/
//	private static String PATTERN_TIME = "HH:mm:ss";
	
	/**	can't Construct	*/
	private DateTime() {
	}
	
	/**	返回yyyy-MM-dd格式的字符串	*/
	public static String getNormalDate() {
		return toNormalDate(new Date());
	}

	/**	返回yyyy-MM-dd HH:mm:ss格式的字符串	*/
	public static String getNormalDateTime() {
		return toNormalDateTime(new Date());
	}

	/**	返回yyyy-MM-dd_HH-mm-ss格式的字符串	*/
	public static String getNormalNameDateTime() {
		return DateFormatUtils.format(new Date(), PATTERN_NAME_DATETIME);
	}

	/**	返回yyyyMMdd格式的字符串	*/
	public static String getShortDate() {
		return toShortDate(new Date());
	}
	
	/**	返回yyyyMMddHHmmss格式的字符串	*/
	public static String getShortDateTime() {
		return toShortDateTime(new Date());
	}
	
	/** 返回yyyyMMddHHmmss格式的长整型 */
	public static Long getShortDateTimeL() {
		return toShortDateTimeL(new Date());
	}
	
	/** 返回毫秒格式的秒数 */
	public static Long getSecondsOfCurrentMillis() {
		return System.currentTimeMillis() / 1000;
	}

	/**返回yyyyMM格式 */
	public static Integer getYearMonth(){
		String yearMonth = getYear() + getMonth();
		return TypeConverter.toInteger(yearMonth);
	}

	/**返回yyyy格式Integer */
	public static Integer getYear() {
		return TypeConverter.toInteger(getYear(new Date()));
	}

	/**返回yyyyMMdd格式Integer*/
	public static Integer getYearMonthDay(){
		return TypeConverter.toInteger(toShortDate(new Date()));
	}

	/**返回yyyy格式 字符串*/
	public static String getYear(Date date) {
		if(date == null){
			return null;
		}
		return TypeConverter.toString(date.getYear() + 1900);//Date类年份是从1900年算的
	}

	/**返回MM格式字符串 */
	public static String getMonth() {
		return getNormalDate().substring(5, 7);
	}
	
	/**返回MM格式的整型 */
	public static Integer getMonthInt() {		
		return TypeConverter.toInteger(getMonth());
	}

	/**返回dd格式的字符串 */
	public static String getDay(String datestr) {
		if(datestr == null){
			return null;
		}
		return datestr.substring(8, 10);
	}

	/**返回dd格式的字符串 */
	public static String getDay() {
		return getDay(getNormalDate());
	}	
	
	/**	返回Date对象,yyyyMM格式,yyyyMMdd格式，yyyyMMddHHmmss格式，yyyy-MM格式，yyyy-MM-dd格式，yyyy-MM-dd HH:mm:ss格式*/
	public static Date toDate(String dateTime) {
		if(dateTime == null){
			return null;
		}

		try {
			Date d = null;
			if(dateTime.length() == 6){
				d = DateUtils.parseDate(dateTime, new String[]{PATTERN_SHORTMONTH});
			}else if(dateTime.length() == 7){
				d = DateUtils.parseDate(dateTime, new String[]{PATTERN_YEAR_MONTH});
			}else if(dateTime.length() == 8){
				d = DateUtils.parseDate(dateTime, new String[]{PATTERN_SHORTDATE});
			}else if(dateTime.length() == 10){
				d = DateUtils.parseDate(dateTime, new String[]{PATTERN_DATE});
			}else if(dateTime.length() == 14){
				d = DateUtils.parseDate(dateTime, new String[]{PATTERN_SHORTDATETIME});
			}else if(dateTime.length() == 19){
				d = DateUtils.parseDate(dateTime, new String[]{PATTERN_DATETIME});
			}
			return d;
		} catch (ParseException e) {
			logger.warn("toDate(String dateTime) error...");
			e.printStackTrace();
		}
		return null;
	}
	
	/**	返回Date对象,可以输入yyyyMMdd格式和yyyyMMddHHmmss格式*/
	public static Date toDate(Long dateTime) {
		if(dateTime == null){
			return null;
		}
		String dateStr = String.valueOf(dateTime);
		Date d = toDate(dateStr);
		return d;
	}
	
	/**	返回yyyy-MM-dd格式的字符串	*/
	public static String toNormalDate(Integer shortDate) {
		if(shortDate == null || shortDate.intValue() == 0){
			return null;
		}
		try {
			Date d = DateUtils.parseDate(shortDate.toString(), new String[]{"yyyyMMdd"});
			return DateFormatUtils.format(d, PATTERN_DATE);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**	返回yyyy-MM-dd格式的字符串	*/
	public static String toNormalDate(Date dateTime) {
		if(dateTime == null){
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_DATE);
	}

	/**	返回HH:mm:ss格式的字符串	*/
	public static String toNormalTime(Date dateTime) {
		if(dateTime == null){
			return null;
		}
		String[] temp = toNormalDateTime(dateTime).split(" ");
		if(temp.length == 2)
		    return temp[1];
		else
			return null;
	}

	/**	返回yyyy-MM-dd HH:mm:ss格式的字符串	*/
	public static String toNormalDateTime(Date dateTime) {
		if(dateTime == null){
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_DATETIME);
	}

	/**	返回yyyy-MM-dd HH:mm:ss格式的字符串,可以输入yyyyMMdd格式和yyyyMMddHHmmss格式*/
	public static String toNormalDateTime(Long dateTime) {
		if(dateTime == null){
			return null;
		}
		Date d = toDate(dateTime);
		if(d == null){
			return null;
		}
		return DateFormatUtils.format(d, PATTERN_DATETIME);
	}

	/**	返回yyyyMMdd格式的字符串	*/
	public static String toShortDate(Date dateTime) {
		if(dateTime == null){
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_SHORTDATE);
	}

	/** 返回yyyyMMdd格式的整型,可以输入yyyy-MM-dd格式和yyyy-MM-dd HH:mm:ss格式*/
	public static Integer toShortDate(String dateTime) {
		Date d = toDate(dateTime);
		return TypeConverter.toInteger(toShortDate(d));
	}

	/**	返回yyyyMMddHHmmss格式的字符串	*/
	public static String toShortDateTime(Date dateTime) {
		if(dateTime == null){
			return null;
		}
		return DateFormatUtils.format(dateTime, PATTERN_SHORTDATETIME);
	}
	
	/**	返回yyyyMMddHHmmss格式的长整型	*/
	public static Long toShortDateTimeL(Date dateTime) {
		String dateStr = toShortDateTime(dateTime);
		if(dateTime == null){
			return null;
		}
		return Long.valueOf(dateStr);
	}
	
	/**	返回yyyyMMddHHmmss格式的长整型	,可以输入毫秒格式时间*/
	public static Long toShortDateTimeL(Long millis) {
		if(millis == null){
			return null;
		}
		return toShortDateTimeL(new Date(millis));
	}

	/**	返回Date类型	,可以输入毫秒格式时间*/
	public static Date toMillis(Long millis) {
		if(millis == null){
			return null;
		}
		return new Date(millis);
	}

	/** 返回yyyyMMddHHmmss格式的长整型,可以输入毫秒格式时间，yyyyMMdd格式，yyyyMMddHHmmss格式，yyyy-MM-dd格式，yyyy-MM-dd HH:mm:ss格式*/
	public static Long toShortDateTimeL(String dateTime) {
		return toShortDateTimeL(toDate(dateTime));
	}
	
	/**
	 * @param //Long shortFormatTime yyyyMMdd格式或yyyyMMddHHmmss格式
	 * @return	毫秒数
	 */
	public static Long toMillisFromShort(Long shortFormatTime){
		Date time = toDate(shortFormatTime);
		if(time == null){
			return null;
		}
		return time.getTime();
	}

	/**
	 * 判断当前日期是星期几
	 *
	 * @param pTime 修要判断的时间
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static int dayForWeek(String pTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if(c.get(Calendar.DAY_OF_WEEK) == 1){
			dayForWeek = 7;
		}else{
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 数字转星期几
	 */
	public static String intWeekToChinese(int intWeek){
		switch (intWeek){
			case 1:
				return  "星期一";
			case 2:
				return  "星期二";
			case 3:
				return  "星期三";
			case 4:
				return  "星期四";
			case 5:
				return  "星期五";
			case 6:
				return  "星期六";
			case 7:
				return  "星期日";
			default:
				return "";
		}
	}

	/**	返回当前时间是一周中的第几天，从周日到周六分别是1-7	*/
	public static String getDayOfWeek(){
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		String dayofweek = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
		return dayofweek ;
	}
	
	/**	返回当前时间是一年中的第几周，若未指定时间，则默认为当前时间，从1-52周	*/
	public static Integer getCurrentWeekOfYear(){
		return getWeekOfYear(new Date());
	}
	
	/**	返回给定时间是一周中的第几周，若未指定时间，则默认为当前时间，从1-52周	*/
	public static Integer getWeekOfYear(Date date){
		if(date == null){
			return null;
		}
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		if(null != date)
			cal.setTime(date);
		Integer weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
		return weekOfYear ;
	}
	
	/**	获取指定年份的最大周数	*/
	public static Integer getMaxWeekOfYear(final int year) {
		int maxWeek = 52;
		int tempWeek = 52;
		String strDate = year + "-12" + "-";
		for (int i = 31; i >= 31 - 7; i--) {//从12月31号向前寻找一个星期所在周序号，最大的序号，即为本年的最大周（最多不超过53周）
			Date date = toDate(strDate + i);
			tempWeek = getWeekOfYear(date);
			if (tempWeek > maxWeek) {
				maxWeek = 53;
				break;
			}
		}
		return maxWeek;
	}
	
	/** 获取指定日期的起始时间，截至时间的String数组
	 * @param day 格式(yyyyMMdd)
	 * @return 格式:{"yyyyMMddHHmmss", "yyyyMMddHHmmss"}
	 */
	public static String[] getRangeOfDay(String day){
		if(day == null || day.length() != 8){
			return null;
		}
		
		String[] range = new String[2];
		range[0] = day + "000000";
		
//		Date startDate = toDate(day + "000000");
//		String nextDate = toShortDate(getNextDate(startDate, 1));
		range[1] = day + "235959";
		
		return range;
	}
	
	/** 获取当年指定周数获取该周的起始日期，截至日期的String数组,格式(yyyyMMddHHmmss)** */
	public static String[] getRangeOfWeek(int week){
		int year = getYear().intValue();
		return getRangeOfWeek(year,week);
	}
	
	/**获取指定年份，和年周数获取该周的起始日期，截至日期的String数组,格式(yyyyMMdd)***/
	public static String[] getRangeOfWeek(int year,int week){
		String[] dateRange = new String[2];
		Calendar calFirstDayOfTheYear = new GregorianCalendar(year,Calendar.JANUARY, 1);
		calFirstDayOfTheYear.add(Calendar.DATE, 7 * (week - 1));
		
		int dayOfWeek = calFirstDayOfTheYear.get(Calendar.DAY_OF_WEEK) - 1; // 转换为周一到星期天的范围模式（默认是周日到周一）
		Calendar calFirstDayInWeek = (Calendar) calFirstDayOfTheYear.clone();
		calFirstDayInWeek.add(Calendar.DATE, calFirstDayOfTheYear.getActualMinimum(Calendar.DAY_OF_WEEK) - dayOfWeek);
		Date firstDayInWeek = calFirstDayInWeek.getTime();		

		Calendar calLastDayInWeek = (Calendar) calFirstDayOfTheYear.clone();
		calLastDayInWeek.add(Calendar.DATE, calFirstDayOfTheYear.getActualMaximum(Calendar.DAY_OF_WEEK) - dayOfWeek);
		Date lastDayInWeek = calLastDayInWeek.getTime();
		
		dateRange[0] = DateFormatUtils.format(firstDayInWeek, PATTERN_SHORTDATE);
		dateRange[1] = DateFormatUtils.format(lastDayInWeek, PATTERN_SHORTDATE);
	    
//	    System.out.println(year + "年第" + week + "个礼拜的第一天是" + dateRange[0]);
//	    System.out.println(year + "年第" + week + "个礼拜的第七天是" + dateRange[1]);
	    return dateRange;
	}
	
	
	/**获取指定日期所在月的起始日期，截至日期的String数组
	 * @return {20130201000000, 20130228235959}
	 */
	public static String[] getRangeOfMonth(Date date){
		if(date == null){
			return null;
		}
		String[] dateRange = new String[2];
		dateRange[0] = getMonthFirstDay(date, PATTERN_SHORTDATE) + "000000";
		dateRange[1] = getMonthLastDay(date, PATTERN_SHORTDATE) + "235959";
	    return dateRange;
	}
	
	/**   
	 * 得到本月的第一天   
	 * @return yyyy-MM-dd
	 */
	public static String getMonthFirstDay(Date date) {
		return getMonthFirstDay(date, null);
	}/**   
	 * 得到本月的第一天   
	 * @return pattern格式的字符串
	 */
	public static String getMonthFirstDay(Date date, String pattern) {
		if(date == null){
			return null;
		}
		if (MyStringUtils.isBlank(pattern)){
			pattern = PATTERN_DATE;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return DateFormatUtils.format(calendar.getTime(), pattern);
	}
	
	/**   
	 * 得到本月的最后一天   
	 * @return yyyy-MM-dd
	 */  
	public static String getMonthLastDay(Date date) {
		return getMonthLastDay(date, null);
	}  
	
	/**   
	 * 得到本月的最后一天   
	 * @return pattern格式的字符串
	 */  
	public static String getMonthLastDay(Date date, String pattern) {
		if(date == null){
			return null;
		}
		if (MyStringUtils.isBlank(pattern)){
			pattern = PATTERN_DATE;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return DateFormatUtils.format(calendar.getTime(), pattern);
	}
	
	/**
	 * 计算毫秒类型的时间距离现在的分钟差
	 * @param millisTime
	 * @return -1：时间参数为null
	 */
	public static long getMinuteDifferenceToNow(Long millisTime){
		if (millisTime == null){
			return -1;
		}
		long currentMillis = System.currentTimeMillis();
		int diff = new Long((currentMillis - millisTime)/1000l/60).intValue();
		return diff;
	}
	
	/**
	 * 计算两个Date类型的时间差，单位转换成秒
	 * @param starttime
	 * @param endtime
	 * @return -1：时间参数有一个为null； 其他：endtime-starttime的时间差，单位是秒
	 */
	public static long getSecondDifference(Date starttime, Date endtime){
		if (starttime == null || endtime == null){
			return -1;
		}
		int diff = new Long((endtime.getTime() - starttime.getTime())/1000l).intValue();
		return diff;
	}

	/**
	 * 计算两个Date类型的时间差，单位转换成小时
	 * @param //starttime
	 * @param //endtime
	 * @return -1：时间参数有一个为null； 其他：endtime-starttime的时间差，单位是秒
	 */
	public static long getHourDifference(Long starttime_short, Long endtime_short){
		Date starttime = toDate(starttime_short);
		Date endtime = toDate(endtime_short);
		if (starttime == null || endtime == null){
			return -1;
		}
		int diff = new Long((endtime.getTime() - starttime.getTime())/1000l/3600l).intValue();
		return diff;
	}
	
	/**	得到两个String类型日期之间的天数差
	 * 示例：starttime="2008-12-03 15:21:03"   endtime="2008-12-27 15:21:03"	 * 
	 * 	*/
	public static int getDaysInterval(String starttime, String endtime)  {
        return getDaysInterval( toDate(starttime), toDate(endtime) ) ;
    }
	/**	得到两个Date类型日期之间的天数差
	 * 示例：starttime="2008-12-03 15:21:03"   endtime="2008-12-27 15:21:03"
	 * 	*/
	public static int getDaysInterval(Date starttime, Date endtime)  {
		if (starttime == null || endtime == null){
			return -1;
		}
        return new Long((endtime.getTime() - starttime.getTime()) / 86400000).intValue() ;
    }
	
	/**
	 * 根据TimeDurationFormat.Format定义的格式来格式化时间数据，例如：“XX天XX小时XX分钟”、“hh:mm:ss”等
	 * @param time		时间，单位（秒）
	 * @param format	时间格式
	 * @return
	 */
	public static String getTimeInterval(long time, TimeDurationFormat.Format format) {
		TimeDurationFormat timeFormat = new TimeDurationFormat(format);
		return timeFormat.format(time);
	}
	
	/**
	 * 获取两个日期(字符串)之间的时间差，显示格式根据TimeDurationFormat.Format对象定义的格式显示
	 * @param starttime		开始日期字符串
	 * @param endtime		结束日期字符串
	 * @param format		时间格式
	 * @return
	 */
	public static String getTimeInterval(String starttime, String endtime, TimeDurationFormat.Format format) {
		return getTimeInterval(toDate(starttime), toDate(endtime), format);
	}
	
	/**
	 * 获取两个日期之间的时间差，显示格式根据TimeDurationFormat.Format对象定义的格式显示
	 * @param starttime		开始时间
	 * @param endtime		结束时间
	 * @param format		时间格式
	 * @return	String
	 */
	public static String getTimeInterval(Date starttime, Date endtime, TimeDurationFormat.Format format) {
		TimeDurationFormat timeFormat = new TimeDurationFormat(format);
		return timeFormat.format(starttime, endtime);
	}
	
	/**	得到给定日期n天前的日期	*/
	public static Date getHistoryDate(Date starttime, Integer daysAgo) {
		if(starttime == null || daysAgo == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(starttime);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)-daysAgo);
		return calendar.getTime();
	}
	
	/**	得到给定日期n小时前的时间	*/
	public static Date getHistoryDateTime(Date starttime, Integer hours) {
		if(starttime == null || hours == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(starttime);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)-hours);
		return calendar.getTime();
	}

	/**	计算出给定日期后n天的时间	*/
	public static Date getNextDate(Date starttime, Integer days) {
		if(starttime == null || days == null){
			return null;
		}
        //return new Date(new Long(starttime.getTime() + 86400000L*days)) ;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(starttime);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)+days);
		return calendar.getTime();
    }
	
	/**	计算出给定时间加上x毫秒的时间 */
	public static Date increaseDateTime(Date starttime, Integer seconds) {
		if(starttime == null || seconds == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(starttime);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
    }

	/**	得到历史时间和当前时间的间隔，根据时间长短返回不同的描述
	 * 比如：12分钟前；5小时前；2天前；3个月前	*/
	public static String getIntervalHistoryToNow(Date historyTime) {
		if (historyTime==null){
			return null;
		}
		long secondDifference =  getSecondDifference(historyTime, new Date());
		if ( secondDifference<=60 ){
			//如果时间在一分钟内，则按秒计算
			return secondDifference + "秒前";
		}else if ( secondDifference<=3600 ){
			//如果时间在一小时内，则按分钟计算
			return (secondDifference/60+1) + "分钟前";
		}else if( secondDifference<=3600*24 ){
			//如果在一天内，则按小时计算
			return (secondDifference/3600) + "小时前";			
		}else if( secondDifference<=3600*24*31 ){
			//如果在一个月内，则按天计算
			return (secondDifference/(3600*24)) + "天前";			
		}else{
			//剩余的都按月计算
			return (secondDifference/(3600*24*30)) + "个月前";			
		}
	}	

	/**	得到未来时间和当前时间的间隔，根据时间长短返回不同的描述
	 * 比如：12分钟；5小时；2天；3个月	*/
	public static String getIntervalFutureToNow(Date futureTime) {
		if (futureTime==null){
			return null;
		}
		long secondDifference =  getSecondDifference(new Date(), futureTime);
		if ( secondDifference<=3600 ){
			//如果时间在一小时内，则按分钟计算
			return (secondDifference/60+1) + "分钟";
		}else if( secondDifference<=3600*24 ){
			//如果在一天内，则按小时计算
			return (secondDifference/3600+1) + "小时";			
		}else if( secondDifference<=3600*24*31 ){
			//如果在一个月内，则按天计算
			return (secondDifference/(3600*24)+1) + "天";			
		}else{
			//剩余的都按月计算
			return (secondDifference/(3600*24*30)+1) + "个月";			
		}
	}	
	
	/**	得到两个Date类型日期之间的月份差	*/
	public static int getMonthInterval(Date starttime, Date endtime)  {
		if (starttime==null || endtime==null){
			return 0;
		}
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(starttime);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endtime);
		return (endCal.get(Calendar.YEAR)-startCal.get(Calendar.YEAR))*12 
		+ (endCal.get(Calendar.MONTH)-startCal.get(Calendar.MONTH));
    }
	
	/**
	 * 初始化最近天数，放到list中
	 * @param days		初始化天数
	 * @return
	 */
	public static List<String> initDayList(int days) {
		List<String> dayList = new ArrayList<String>();
		for (int i=days-1; i>=0; i--) {
			String day = DateTime.toNormalDate(DateTime.getHistoryDate(new Date(), i));
			dayList.add(day);
		}
		
		return dayList;
	}
	
	/**
	 * 初始化一个时间段的天数，放到list中
	 * @param startDate		起始时间，字符串：“2012-06-18”
	 * @param endDate		结束时间，字符串：“2012-06-20”
	 * @return
	 */
	public static List<String> initDayList(String startDate, String endDate) {
		Date sDate = DateTime.toDate(startDate);
		Date eDate = DateTime.toDate(endDate);
		return initDayList(sDate, eDate);
	}
	
	/**
	 * 初始化一个时间段的天数，放到list中
	 * @param startDate		起始时间，Date对象
	 * @param endDate		结束时间，Date对象
	 * @return
	 */
	public static List<String> initDayList(Date startDate, Date endDate) {
		if (startDate == null || endDate == null)
			return null;
		List<String> dayList = new ArrayList<String>();
		int interval = getDaysInterval(startDate, endDate) + 1;
		for (int i=0; i<interval; i++) {
			Date currentDate = getNextDate(startDate, i);
			dayList.add(DateTime.toNormalDate(currentDate));
		}
		
		return dayList;
	}
	
	/**
	 * 根据Date日期，返回该日期所在的月份的第一天
	 * @param date	日期
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		if (date == null)
			throw new IllegalArgumentException("date can't be null");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);   

		return cal.getTime();  
	}
	
	/**
	 * 根据Date日期，返回该日期所在的月份的最后一天
	 * @param date	日期
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		if (date == null)
			throw new IllegalArgumentException("date can't be null");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set( Calendar.DATE, 1 );  
        cal.roll(Calendar.DATE, - 1 );  
        
        return cal.getTime();
	}
	
	/**
	 * 判断两个日期是否为同一天
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		String strDate1 = DateTime.toShortDate(date1);
		String strDate2 = DateTime.toShortDate(date2);
		
		if (strDate1 == null || strDate2 == null) {
			throw new IllegalArgumentException("时间参数为空");
		}
		if (strDate1.equals(strDate2)) {
			return true;
		} else{
			return false;
		}
	}
	
	/**
	 * 判断一个日期是上午还是下午
	 * @param date
	 * @return am,pm
	 */
	public static String toAmOrPm(Date date) {
		String dataTime = toShortDateTime(date);
		if (dataTime == null) {
			throw new IllegalArgumentException("时间参数为空");
		}
		
		int hour = TypeConverter.toInteger(dataTime.substring(8,10));
		if (hour >= 12) {
			return "pm";
		} else{
			return "am";
		}
	}
	
	/**
	 * 判断一个日期是白天还是夜晚
	 * @param date
	 * @return day,night
	 */
	public static String toDayOrNight(Date date) {
		String dataTime = toShortDateTime(date);
		if (dataTime == null) {
			throw new IllegalArgumentException("时间参数为空");
		}
		
		int hour = TypeConverter.toInteger(dataTime.substring(8,10));
		if (hour >= 6 && hour <= 23) {
			return "day";
		} else{
			return "night";
		}
	}
	
	/**
	 * 是否包含当天
	 * @param startDate	格式：20120712
	 * @param endDate	格式：20120712
	 * @return
	 */
	public static boolean hasCurrentDay(int startDate , int endDate) {
		int currentDate = TypeConverter.toInteger(DateTime.getShortDate());
		if (currentDate >= startDate && currentDate <= endDate) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否包含当天
	 * @param dates	格式：20121105
	 * @return
	 */
	public static boolean hasCurrentDay(Integer[] dates) {
		if (dates == null)
			return false;
		int currentDate = TypeConverter.toInteger(DateTime.getShortDate());
		for (Integer date : dates) {
			if (date != null && date.intValue() == currentDate)
				return true;
		}
		
		return false;
	}
	
	/**
	 * 判断是否包含当前月
	 * @param startMonth	开始月份
	 * @param endMonth	结束月份
	 * @return
	 */
	public static boolean hasCurrentMonth(int startMonth, int endMonth) {
		int currentMonth = getYearMonth();
		
		if (currentMonth >= startMonth && currentMonth <= endMonth) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否包含当前月
	 * @param months	月份集合，格式：201302
	 * @return
	 */
	public static boolean hasCurrentMonth(Integer[] months) {
		if (months == null)
			return false;
		
		int currentMonth = getYearMonth();
		for (Integer month : months) {
			if (month != null && month.intValue() == currentMonth) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		Integer[] months = {201512, 201501, 201503};
//		Integer[] months = new Integer[1];
		System.out.println("sssssssssssss="+DateTime.toMillisFromShort(20160309163506L));
		System.out.println("sssssssssssss111="+DateTime.toMillisFromShort(20160315163506L));
		System.out.print(hasCurrentMonth(months));
	}
	
}