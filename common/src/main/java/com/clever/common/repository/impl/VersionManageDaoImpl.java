package com.clever.common.repository.impl;

import com.clever.common.domain.VersionInfo;
import com.clever.common.repository.VersionManageDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import org.springframework.stereotype.Repository;

/**
 * Info: app版本管理
 * User: gary.zhang@clever-m.com
 * Date: 2016-03-30
 * Time: 10:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Repository
public class VersionManageDaoImpl extends BaseMybatisDAO<VersionInfo, Long>  implements VersionManageDao {

    private static final String sql_mapper = "com.clever.common.repository.VersionManageDao";

    @Override
    public String getNamespace() {
        return sql_mapper;
    }

    @Override
    public VersionInfo queryVersionInfo(long shopId) {
        return this.getMasterSqlSessionTemplate().selectOne(this.getMapperKey(sql_mapper, "queryVersionInfoByShopId"),shopId);
    }
}
