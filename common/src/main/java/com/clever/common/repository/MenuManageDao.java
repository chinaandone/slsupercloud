package com.clever.common.repository;

import com.clever.common.domain.MenuInfo;
import com.clever.common.domain.MenuInfoView;
import com.clever.common.repository.base.IBaseMapperDao;

import java.util.List;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-20
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface MenuManageDao extends IBaseMapperDao<MenuInfo, Long> {
    //分页查询商家菜品列表
    List<MenuInfoView> queryAllMenuInfoByShopId(long shopId) ;

    //修改菜品
    int updateMenu(long id,String menuId, String scanCode, String price, String name, String photo, String status, String subMenuId);

    //根据shopId删除菜品
    int deleteByShopId(long shopId);

}
