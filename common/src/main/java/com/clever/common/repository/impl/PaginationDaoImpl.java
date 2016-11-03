package com.clever.common.repository.impl;

import com.clever.common.domain.MenuInfo;
import com.clever.common.repository.PaginationDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import com.clever.common.view.PaginationView;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class PaginationDaoImpl extends BaseMybatisDAO<MenuInfo, Long> implements PaginationDao {

    private static final String sql_mapper = "com.clever.common.repository.PaginationDao";

	@Override
	public int count(PaginationView paginationView) {
        return this.getMasterSqlSessionTemplate().selectOne(getMapperKey(getNamespace(), paginationView.loadCountSqlName()), paginationView.loadCountFilter());
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Collection list(PaginationView paginationView) {
		return this.getMasterSqlSessionTemplate().selectList(getMapperKey(getNamespace(), paginationView.loadListSqlName()), paginationView.loadListFilter());
	}

    @Override
    public String getNamespace() {
        return sql_mapper;
    }
}
