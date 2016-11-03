package com.clever.common.repository.impl;

import com.clever.common.domain.AccessLog;
import com.clever.common.domain.Path;
import com.clever.common.repository.AccessLogManageDao;
import com.clever.common.repository.PathManageDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import com.clever.common.view.PaginationView;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Repository
public class AccessLogManageDaoImpl extends BaseMybatisDAO<AccessLog, Long>  implements AccessLogManageDao {

    private static final String sql_mapper = "com.clever.common.repository.AccessLogManageDao";

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
