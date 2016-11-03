package com.clever.common.repository.impl;

import com.clever.common.domain.TasteInfo;
import com.clever.common.repository.TasteManageDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Info: 小超人菜品口味
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-20
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Repository
public class TasteManageDaoImpl extends BaseMybatisDAO<TasteInfo, Long> implements TasteManageDao {

    private static final String sql_mapper = "com.clever.common.repository.TasteManageDao";

    @Override
    public String getNamespace() {
        return sql_mapper;
    }

    public List<TasteInfo> queryAllMenuTaste() {
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "queryAllMenuTaste"));
    }
}
