package com.clever.common.repository.impl;

import com.clever.common.domain.VideoBusiness;
import com.clever.common.repository.VideoBusinessManageDao;
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
public class VideoBusinessManageDaoImpl extends BaseMybatisDAO<VideoBusiness, Long>  implements VideoBusinessManageDao {

    private static final String sql_mapper = "com.clever.common.repository.VideoBusinessManageDao";

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
    public List<VideoBusiness> getEntities(VideoBusiness v) {
        Map<String,Object> map = new HashMap();
        map.put("orgId",v.getOrgId());
        map.put("type",v.getType());
        map.put("nowDate",new Date());
        map.put("kind",v.getKind());
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "getEntities"), map);
    }

    @Override
    public String getNamespace() {
        return sql_mapper;
    }

}
