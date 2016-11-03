package com.clever.common.repository.impl;

import com.clever.common.domain.Pictrue;
import com.clever.common.domain.RollMain;
import com.clever.common.repository.PictrueManageDao;
import com.clever.common.repository.RollMainManageDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import com.clever.common.view.PaginationView;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Repository
public class RollMainManageDaoImpl extends BaseMybatisDAO<RollMain, Long>  implements RollMainManageDao {

    private static final String sql_mapper = "com.clever.common.repository.RollMainManageDao";

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
    public List<RollMain> getEntities(RollMain r) {
        Map<String,Object> map = new HashMap();
        map.put("orgId",r.getOrgId());
        map.put("type",r.getType());
        map.put("nowDate",new Date());
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "getEntities"), map);
    }

    @Override
    public String getNamespace() {
        return sql_mapper;
    }

}
