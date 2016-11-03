package com.clever.common.repository.impl;

import com.clever.common.domain.Pictrue;
import com.clever.common.domain.UploadAccessLog;
import com.clever.common.repository.PictrueManageDao;
import com.clever.common.repository.UploadAccessLogManageDao;
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
public class UploadAccessLogManageDaoImpl extends BaseMybatisDAO<UploadAccessLog, Long>  implements UploadAccessLogManageDao {

    private static final String sql_mapper = "com.clever.common.repository.UploadAccessLogManageDao";

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
    public Collection getLevel1Analysis(PaginationView paginationView) {
        return this.getMasterSqlSessionTemplate().selectList(getMapperKey(getNamespace(), "getLevel1Analysis"), paginationView.loadListFilter());
    }

    @Override
    public Collection getLevel2Analysis(PaginationView paginationView) {
        return this.getMasterSqlSessionTemplate().selectList(getMapperKey(getNamespace(), "getLevel2Analysis"),paginationView.loadListFilter());
    }

    @Override
    public Long countTimeStay(PaginationView paginationView){
        return this.getMasterSqlSessionTemplate().selectOne(getMapperKey(getNamespace(), "countTimeStay"), paginationView.loadListFilter());
    }

    @Override
    public Long countClick(PaginationView paginationView){
        return this.getMasterSqlSessionTemplate().selectOne(getMapperKey(getNamespace(), "countClick"), paginationView.loadListFilter());
    }

    @Override
    public Long countLv2TimeStay(PaginationView paginationView){
        return this.getMasterSqlSessionTemplate().selectOne(getMapperKey(getNamespace(), "countLv2TimeStay"), paginationView.loadListFilter());
    }

    @Override
    public Long countLv2Click(PaginationView paginationView){
        return this.getMasterSqlSessionTemplate().selectOne(getMapperKey(getNamespace(), "countLv2Click"), paginationView.loadListFilter());
    }

    @Override
    public List<UploadAccessLog> getEntities(UploadAccessLog u) {
        Map<String,Object> map = new HashMap();
        map.put("orgId",u.getOrgId());
        map.put("nowDate","");
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "getEntities"), map);
    }

    @Override
    public String getNamespace() {
        return sql_mapper;
    }

}
