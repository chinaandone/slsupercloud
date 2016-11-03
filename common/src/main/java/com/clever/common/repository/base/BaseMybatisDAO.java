package com.clever.common.repository.base;

import com.clever.common.bean.page.WebPage;
import com.clever.common.domain.RollMain;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseMybatisDAO<E, PK> {
    private static final Logger logger = LoggerFactory.getLogger(BaseMybatisDAO.class);
    private static final String   LEAF  = "LEAF";
    @Resource(name = "masterSqlSessionTemplate")
    private SqlSessionTemplate masterSqlSessionTemplate;

    public SqlSessionTemplate getMasterSqlSessionTemplate() {
        return masterSqlSessionTemplate;
    }

    protected String getMapperKey(String mapperKey, String key) {
        return mapperKey + "." + key;
    }

    /**
     * 返回当前实体的命名空间字符串名称
     */
    public abstract String getNamespace();


    /**
     * @param e
     * @description 详细说明
     */
    public void addEntity(final E e) {
        this.getMasterSqlSessionTemplate().insert(getMapperKey(getNamespace(), "addEntity"), e);
    }


    /**
     * @param id
     * @description 详细说明
     */
    public void removeEntity( final PK id) {
        this.getMasterSqlSessionTemplate().delete(getMapperKey(getNamespace(), "removeEntity"), id);
    }

    /**
     * @param e
     * @description 详细说明
     */
    public void updateEntity(final E e) {
        this.getMasterSqlSessionTemplate().update(getMapperKey(getNamespace(), "updateEntity"), e);
    }

    /**
     * @param id
     * @return
     * @throws Exception
     * @description 详细说明
     */
    public E getEntity( final PK id) {
        return (E)this.getMasterSqlSessionTemplate().selectOne(getMapperKey(getNamespace(), "getEntity"), id);
    }

    /**
     * @param page
     * @param e
     * @return 此方法不再用于分页，因为只能根据id来分，其他不支持
     * @description 详细说明
     */
    public List<E> getEntities( WebPage<E> page,  final E e) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("e", e);
        return this.getMasterSqlSessionTemplate().selectList(getMapperKey(getNamespace(), "getEntities"), map);
    }

}
