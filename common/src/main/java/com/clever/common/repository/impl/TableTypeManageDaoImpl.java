package com.clever.common.repository.impl;

import com.clever.common.domain.TableTypeBean;
import com.clever.common.client.view.TableTypeBeanView;
import com.clever.common.repository.TableTypeManageDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import com.clever.common.view.PaginationView;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-20
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Repository
public class TableTypeManageDaoImpl extends BaseMybatisDAO<TableTypeBean, Long> implements TableTypeManageDao {

    private static final String sql_mapper = "com.clever.common.repository.TableTypeManageDao";

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
    public List<TableTypeBean> getEntities(TableTypeBean r) {
        Map<String,Object> map = new HashMap();
        map.put("orgId",r.getOrgId());
        map.put("clientId",r.getClientId());
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "getEntities"), map);
    }

    @Override
    public String getNamespace() {
        return sql_mapper;
    }

}
