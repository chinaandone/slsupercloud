package com.clever.common.repository.impl;

import com.clever.common.domain.Org;
import com.clever.common.domain.Pictrue;
import com.clever.common.repository.OrgManageDao;
import com.clever.common.repository.PictrueManageDao;
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
public class OrgManageDaoImpl extends BaseMybatisDAO<Org, Long>  implements OrgManageDao {

    private static final String sql_mapper = "com.clever.common.repository.OrgManageDao";

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
    public List<Org> getEntities(Org r) {
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
