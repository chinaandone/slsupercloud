package com.clever.common.view;

import java.util.HashMap;
import java.util.Map;

public class PaginationOption {
	public static final String KEY_SKIP_NUM = "skipNum";
	public static final String KEY_ROW_NUM = "rowNum";
	public static final String SQL_COUNT_PREFIX = "Pagination.count_";
	public static final String SQL_LIST_PREFIX = "Pagination.list_";
	private String domain;
	private int skipNum = 0;
	private int rowNum = 20;
	private Map<String, Object> filter = new HashMap<String, Object>();

	public Map<String, Object> getFilter() {
		return filter;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getSkipNum() {
		return skipNum;
	}

	public int getRowNum() {
		return rowNum;
	}

	public String getCountSqlName() {
		return SQL_COUNT_PREFIX + domain;
	}

	public String getListSqlName() {
		return SQL_LIST_PREFIX + domain;
	}

	public Map<String, Object> getCountFilter() {
		return filter;
	}

	public Map<String, Object> getListFilter() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(KEY_SKIP_NUM, skipNum);
		map.put(KEY_ROW_NUM, rowNum);
		map.putAll(filter);
		return map;
	}

	public void nextPage() {
		skipNum += rowNum;
	}
	
	public void restPage() {
		skipNum = 0;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public void setSkipNum(int skipNum) {
		this.skipNum = skipNum;
	}
}
