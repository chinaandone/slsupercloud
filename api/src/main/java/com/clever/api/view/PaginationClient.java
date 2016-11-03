package com.clever.api.view;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class PaginationClient {
	public static final String SQL_LIST_PREFIX = "PaginationClient.list_";
	public static final String SQL_LIST_COUNT = "PaginationClient.count_";
	public static final String SQL_LIST_MONEY = "PaginationClient.money_";

	public static final String LENGTH = "length";
	private String domainSql;
	private String domainSqlCount;
	private String moneySql;

	private int totalcount;
	private String totalMoney = "";
	private int length = 20;
	private Collection dataList;
	private Map<String, Object> filter = new HashMap<String, Object>();

    private boolean needCount =false;
    private boolean needMoney =false;

    public boolean isNeedCount() {
        return needCount;
    }

    public boolean isNeedMoney() {
        return needMoney;
    }

    public void setDomainSql(String domainSql) {
		this.domainSql = domainSql;
	}

	public void setDomainSqlCount(String domainSqlCount) {
		this.domainSqlCount = domainSqlCount;
        this.needCount=true;
	}

	public void setDataList(Collection dataList) {
		this.dataList = dataList;
	}

	public void setMoneySql(String moneySql) {
		this.moneySql = moneySql;
        this.needMoney=true;
	}

	public String loadListSqlCount() {
		return SQL_LIST_COUNT + domainSqlCount;
	}

	public String loadListSqlName() {
		return SQL_LIST_PREFIX + domainSql;
	}

	public String loadMoneySql() {
		return SQL_LIST_MONEY + moneySql;
	}

	public Map<String, Object> loadFilter() {
		return filter;
	}

	public Map<String, Object> loadListFilter() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(LENGTH, length);
		map.putAll(filter);
		return map;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	// 返回参数

	public Collection getDataList() {
		return dataList;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public String getTotalcount() {
		return String.valueOf(totalcount);
	}

}