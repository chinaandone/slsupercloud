package com.clever.common.repository.impl;

import com.clever.common.domain.Table;
import com.clever.common.domain.TablePhone;
import com.clever.common.repository.TableManageDao;
import com.clever.common.repository.TablePhoneManageDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import com.clever.common.view.PaginationView;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Repository
public class TablePhoneManageDaoImpl extends BaseMybatisDAO<TablePhone, Long>  implements TablePhoneManageDao {

    private static final String sql_mapper = "com.clever.common.repository.TablePhoneManageDao";

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
    public List<TablePhone> getEntities(TablePhone r) {
        Map<String,Object> map = new HashMap();
        map.put("orgId",r.getOrgId());
        map.put("clientId",r.getClientId());
        map.put("tableId",r.getTableId());
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "getEntities"), map);
    }

    @Override
    public String getNamespace() {
        return sql_mapper;
    }

}
