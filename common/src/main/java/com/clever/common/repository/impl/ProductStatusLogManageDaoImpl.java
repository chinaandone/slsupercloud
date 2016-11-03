package com.clever.common.repository.impl;

import com.clever.common.domain.ProductStatusLog;
import com.clever.common.repository.ProductStatusLogManageDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import com.clever.common.view.PaginationView;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-08-17
 * Time: 10:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Repository
public class ProductStatusLogManageDaoImpl extends BaseMybatisDAO<ProductStatusLog, Long>  implements ProductStatusLogManageDao {

    private static final String sql_mapper = "com.clever.common.repository.ProductStatusLogManageDao";

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
    public List<ProductStatusLog> getEntities(ProductStatusLog c) {
        Map<String,Object> map = new HashMap();
        map.put("productStatusLogId",c.getProductStatusLogId());
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "getEntities"), map);
    }

    @Override
    public String getNamespace() {
        return sql_mapper;
    }

}
