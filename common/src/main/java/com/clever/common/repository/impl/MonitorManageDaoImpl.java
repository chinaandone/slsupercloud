package com.clever.common.repository.impl;

import com.clever.common.domain.ComputerInfo;
import com.clever.common.domain.Table;
import com.clever.common.repository.MonitorManageDao;
import com.clever.common.repository.TableManageDao;
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
public class MonitorManageDaoImpl extends BaseMybatisDAO<ComputerInfo, Long> implements MonitorManageDao {
    private static final String sql_mapper = "com.clever.common.repository.MonitorManageDao";


    @Override
    public String getNamespace() {
        return sql_mapper;
    }

    @Override
    public List<ComputerInfo> getEntities(ComputerInfo c) {
        Map<String,Object> map = new HashMap();
        map.put("orgId",c.getOrgId());
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "getEntities"), map);
    }

}
