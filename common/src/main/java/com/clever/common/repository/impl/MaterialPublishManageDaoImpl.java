package com.clever.common.repository.impl;

import com.clever.common.client.view.MaterialView;
import com.clever.common.domain.Material;
import com.clever.common.domain.MaterialPublish;
import com.clever.common.domain.MaterialPublishDTView;
import com.clever.common.domain.VideoPublish;
import com.clever.common.repository.MaterialPublishManageDao;
import com.clever.common.repository.VideoPublishManageDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import com.clever.common.view.PaginationView;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Info: clever
 * ser: chay@clever-m.com
 * Date: 2016-04-20
 * Time: 18:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Repository
public class MaterialPublishManageDaoImpl extends BaseMybatisDAO<MaterialPublish, Long>  implements MaterialPublishManageDao {

    private static final String sql_mapper = "com.clever.common.repository.MaterialPublishManageDao";

    @Override
    public int count(PaginationView paginationView) {
        return this.getMasterSqlSessionTemplate().selectOne(getMapperKey(getNamespace(), paginationView.loadCountSqlName()), paginationView.loadCountFilter());
    }

    @Override
    public int countExist(PaginationView paginationView){
        return this.getMasterSqlSessionTemplate().selectOne(getMapperKey(getNamespace(), "countExist"), paginationView.loadCountFilter());
    }

    @SuppressWarnings({ "rawtypes" })
    @Override
    public Collection list(PaginationView paginationView) {
        return this.getMasterSqlSessionTemplate().selectList(getMapperKey(getNamespace(), paginationView.loadListSqlName()), paginationView.loadListFilter());
    }

    @Override
    public List<MaterialPublishDTView> getEntities(MaterialPublish v) {
        Map<String,Object> map = new HashMap();
        map.put("orgId",v.getOrgId());
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "getEntities"), map);
    }

    @Override
    public String getNamespace() {
        return sql_mapper;
    }

}
