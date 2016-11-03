package com.clever.common.repository.impl;

import com.clever.common.domain.ComputerInfo;
import com.clever.common.domain.InfoPhone;
import com.clever.common.repository.MonitorManageDao;
import com.clever.common.repository.PhoneManageDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import org.springframework.stereotype.Repository;

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
public class PhoneManageDaoImpl extends BaseMybatisDAO<InfoPhone, Long> implements PhoneManageDao {
    private static final String sql_mapper = "com.clever.common.repository.PhoneManageDao";


    @Override
    public String getNamespace() {
        return sql_mapper;
    }

    @Override
    public List<InfoPhone> getEntities(InfoPhone c) {
        Map<String,Object> map = new HashMap();
        map.put("orgId",c.getOrgId());
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "getEntities"), map);
    }

}
