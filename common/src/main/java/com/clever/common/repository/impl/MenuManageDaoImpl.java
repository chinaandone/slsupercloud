package com.clever.common.repository.impl;

import com.clever.common.domain.MenuInfo;
import com.clever.common.domain.MenuInfoView;
import com.clever.common.repository.MenuManageDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-20
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Repository
public class MenuManageDaoImpl extends BaseMybatisDAO<MenuInfo, Long> implements MenuManageDao {

    private static final String sql_mapper = "com.clever.common.repository.MenuManageDao";

    @Override
    public String getNamespace() {
        return sql_mapper;
    }

    public List<MenuInfoView> queryAllMenuInfoByShopId(long shopId) {
        return this.getMasterSqlSessionTemplate().selectList(this.getMapperKey(sql_mapper, "queryAllMenuInfoByShopId"),shopId);
    }

    public int updateMenu(long id,String menuId, String scanCode,String price,String name,String photo,String status,String subMenuId) {
        Map<String, Object> map = new HashMap();
        map.put("id", id);
        map.put("menuId", menuId);
        map.put("scanCode", scanCode);
        map.put("price", price);
        map.put("name", name);
        map.put("photo", photo);
        map.put("status",status);
        map.put("subMenuId", subMenuId);
        map.put("updateTime", new Date());
        return this.getMasterSqlSessionTemplate().update(this.getMapperKey(sql_mapper, "updateMenu"), map);
    }

    public int deleteByShopId(long shopId) {
        return this.getMasterSqlSessionTemplate().delete(this.getMapperKey(sql_mapper,"deleteByShopId"),shopId);
    }

}
