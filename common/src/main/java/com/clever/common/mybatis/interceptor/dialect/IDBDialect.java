package com.clever.common.mybatis.interceptor.dialect;

/**
 * @author Yate
 * @date 2013年9月24日
 * @description 数据库方言接口
 * @version 1.0
 */
public interface IDBDialect {
	public static enum Type {
		MYSQL
	}

	/**
	 * @description 详细说明
     *
     * @param originalSql
     * @param page
     * @param size
     * @return
     */
	String getPaginationSQL(final String originalSql, final int page,
                            final int size);
}
