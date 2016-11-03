package com.clever.common.service;

import com.clever.common.domain.MenuInfo;
import com.clever.common.domain.MenuInfoView;

import java.util.List;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-20
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface MenuManageService {
    //分页查询商家菜品
    List<MenuInfoView> queryAllMenuInfoByShopId(long shopId);

    //单条添加菜品
    void addMenuInfo(MenuInfo menuInfo);

    //修改菜品
    void updateMenu(long id, String menuId,String scanCode, String orgId, String price, String name,String photo,String status,String subMenuId);

    //根据shopId删除
    void deleteByShopId (long shopId);

}
