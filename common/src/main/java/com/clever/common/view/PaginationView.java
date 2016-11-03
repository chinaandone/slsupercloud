package com.clever.common.view;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Info: clever
 * User:enva.liang@clever-m.com
 * 		chay.ni@clever-m.com
 * Date: 2016-03-17
 * Time: 15:11
 * Version: 1.0
 * History:
 * <p>如果有修改过程，请记录</P>
 * <p>20160321增加一个page_all参数，为0或空或''表示分页list，为1表示不分页list</P>
 */
@SuppressWarnings("rawtypes")
public class PaginationView {
	public static final String KEY_SKIP_NUM = "skipNum";
	public static final String KEY_ROW_NUM = "rowNum";
	public static final String SQL_COUNT_PREFIX = "count_";
	public static final String SQL_LIST_PREFIX = "list_";
	public static final int MAX_ROW_NUM = 100;
	public static final int DISPLAY_ALL = 1;
	public static final int DISPLAY_IN_PAGE = 0;
	private String domain;
	private int iDisplayStart = 0;
	private int iDisplayLength = 15;
	private int iDisplayAll = 0;
	private String sEcho;
	private int iTotalRecords;
	private int iTotalDisplayRecords;
	private Collection aaData;
	private Map<String, Object> filter = new HashMap<String, Object>();

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Collection getAaData() {
		return aaData;
	}

	public void setAaData(Collection aaData) {
		this.aaData = aaData;
	}

	public void setFilter(Map<String, Object> filter) {
		this.filter = filter;
	}

	public Map<String, Object> loadFilter() {
		return filter;
	}

	public int getITotalRecords() {
		return iTotalRecords;
	}

	public void setITotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getITotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setITotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public int loadIDisplayStart() {
		return iDisplayStart;
	}

	public void setIDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public int loadIDisplayLength() {
		return iDisplayLength;
	}

	public void setIDisplayLength(int iDisplayLength) {
		if (iDisplayLength > MAX_ROW_NUM) {
			iDisplayLength = MAX_ROW_NUM;
		}
		this.iDisplayLength = iDisplayLength;
	}

	public void setIDisplayAll(int iDisplayAll){
		if(iDisplayAll != DISPLAY_ALL
				&& iDisplayAll != DISPLAY_IN_PAGE){
			iDisplayAll = DISPLAY_IN_PAGE;
		}
		this.iDisplayAll = iDisplayAll;
	}

	public int getIDisplayAll() {
		return iDisplayAll;
	}

	public String getSEcho() {
		return sEcho;
	}

	public void setSEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public Map<String, Object> loadCountFilter() {
		return filter;
	}

	public Map<String, Object> loadListFilter() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(KEY_SKIP_NUM, iDisplayStart);
		map.put(KEY_ROW_NUM, iDisplayLength);
		map.putAll(filter);
		return map;
	}

	public String loadCountSqlName() {
		return SQL_COUNT_PREFIX + domain;
	}

	public String loadListSqlName() {
		return SQL_LIST_PREFIX + domain;
	}

}
