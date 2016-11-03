package com.clever.common.repository.impl;

import com.clever.common.domain.Advertisement;
import com.clever.common.domain.AdvertisementRollMain;
import com.clever.common.repository.AdvertisementManageDao;
import com.clever.common.repository.AdvertisementRollMainManageDao;
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
public class AdvertisementRollMainManageDaoImpl extends BaseMybatisDAO<AdvertisementRollMain, Long>  implements AdvertisementRollMainManageDao {

    private static final String sql_mapper = "com.clever.common.repository.AdvertisementRollMainManageDao";

    @Override
    public int count(PaginationView paginationView) {
        return this.getMasterSqlSessionTemplate().selectOne(getMapperKey(getNamespace(), paginationView.loadCountSqlName()), paginationView.loadCountFilter());
    }

    @Override
    public int countExist(PaginationView paginationView) {
        return this.getMasterSqlSessionTemplate().selectOne(getMapperKey(getNamespace(), "countExist"), paginationView.loadCountFilter());
    }

    @SuppressWarnings({ "rawtypes" })
    @Override
    public Collection list(PaginationView paginationView) {
        return this.getMasterSqlSessionTemplate().selectList(getMapperKey(getNamespace(), paginationView.loadListSqlName()), paginationView.loadListFilter());
    }

    @Override
    public List<AdvertisementRollMain> getEntities(AdvertisementRollMain a) {
        Map<String,Object> map = new HashMap();
        map.put("rollMainId", a.getRollMainId());
        map.put("advertisementId", a.getAdvertisementId());
        map.put("title",a.getTitle());
        map.put("created",a.getCreated());
        map.put("orgId",a.getOrgId());
        map.put("clientId",a.getClientId());
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "getEntities"), map);
    }

    @Override
    public String getNamespace() {
        return sql_mapper;
    }

}
