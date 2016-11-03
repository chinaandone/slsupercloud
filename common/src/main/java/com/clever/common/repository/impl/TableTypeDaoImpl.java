package com.clever.common.repository.impl;

import com.clever.common.domain.TableType;
import com.clever.common.domain.TableTypeView;
import com.clever.common.repository.TableTypeDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-20
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Repository
public class TableTypeDaoImpl extends BaseMybatisDAO<TableType, Long> implements TableTypeDao {

    private static final String sql_mapper = "com.clever.common.repository.TableTypeDao";

    @Override
    public String getNamespace() {
        return sql_mapper;
    }

    public List<TableTypeView> queryAllTableTypeByShopId(long shopId) {
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "queryAllTableTypeByShopId"),shopId);
    }

}
