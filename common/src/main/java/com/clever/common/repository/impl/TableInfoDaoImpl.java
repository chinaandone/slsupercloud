package com.clever.common.repository.impl;

import com.clever.common.domain.TableInfo;
import com.clever.common.repository.TableInfoDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import org.springframework.stereotype.Repository;

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
public class TableInfoDaoImpl extends BaseMybatisDAO<TableInfo, Long> implements TableInfoDao {

    private static final String sql_mapper = "com.clever.common.repository.TableInfoDao";

    @Override
    public String getNamespace() {
        return sql_mapper;
    }

    public List<TableInfo> queryAllTableInfoByShopId(long shopId) {
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "queryAllTableInfoByShopId"),shopId);
    }

}
