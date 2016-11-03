package com.clever.common.repository.impl;

import com.clever.common.client.view.MaterialView;
import com.clever.common.domain.MaterialBusiness;
import com.clever.common.domain.MaterialBusinessDTView;
import com.clever.common.repository.MaterialBusinessManageDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import com.clever.common.view.PaginationView;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * User: chay@clever-m.com
 * Date: 2016-04-19
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Repository
public class MaterialBusinessManageDaoImpl extends BaseMybatisDAO<MaterialBusiness, Long>  implements MaterialBusinessManageDao {

    private static final String sql_mapper = "com.clever.common.repository.MaterialBusinessManageDao";

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
    public List<MaterialBusinessDTView> getEntities(MaterialBusiness v) {
        Map<String,Object> map = new HashMap();
        map.put("orgId",v.getOrgId());
        map.put("type",v.getType());
        map.put("nowDate",new Date());
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "getEntities"), map);
    }

    @Override
    public List<MaterialBusinessDTView> getBinEntities(MaterialBusiness v) {
        Map<String,Object> map = new HashMap();
        map.put("type",v.getType());
        map.put("fileKindName",v.getFileKindName());
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "getBinEntities"), map);
    }

    @Override
    public String getNamespace() {
        return sql_mapper;
    }

}
