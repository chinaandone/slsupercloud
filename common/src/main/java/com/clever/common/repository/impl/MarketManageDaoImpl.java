package com.clever.common.repository.impl;

import com.clever.common.client.view.MarketView;
import com.clever.common.domain.Market;
import com.clever.common.domain.MarketMonitor;
import com.clever.common.repository.MarketManageDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import com.clever.common.view.PaginationView;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-07-26
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Repository
public class MarketManageDaoImpl extends BaseMybatisDAO<Market, Long>  implements MarketManageDao {

    private static final String sql_mapper = "com.clever.common.repository.MarketManageDao";

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
    public List<MarketView> getEntities(Market r) {
        Map<String,Object> map = new HashMap();
        map.put("company",r.getCompany());
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "getEntities"), map);
    }

    //获取推广关联的图片列表
    @Override
    public List<MarketMonitor> getMarketMonitorEntities(Market r) {
        Map<String,Object> map = new HashMap();
        map.put("marketId", r.getMarketId());
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "getMarketMonitorEntities"), map);
    }

    //增加一条关联
    @Override
    public void addMarketMonitorEntity(MarketMonitor marketMonitor) {
        this.getMasterSqlSessionTemplate().insert(getMapperKey(getNamespace(), "addMarketMonitorEntity"), marketMonitor);
    }

    //根据marketID删除所有关联
    @Override
    public void deleteMarketMonitorByMarket(Market r) {
        this.getMasterSqlSessionTemplate().delete(getMapperKey(getNamespace(), "deleteMarketMonitorEntityByMarket"), r);
    }

    @Override
    public String getNamespace() {
        return sql_mapper;
    }



}
