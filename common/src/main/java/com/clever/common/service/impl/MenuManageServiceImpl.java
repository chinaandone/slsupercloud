package com.clever.common.service.impl;

import com.clever.common.domain.MenuInfo;
import com.clever.common.domain.MenuInfoView;
import com.clever.common.repository.MenuManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.MenuManageService;
import com.clever.common.service.base.BaseService;
import com.clever.common.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-20
 * Time: 15:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("MenuManageService")
@Transactional
public class MenuManageServiceImpl extends BaseService<MenuInfo,Long> implements MenuManageService{
    @Autowired
    private MenuManageDao menuManageDao;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected IBaseMapperDao<MenuInfo, Long> getMapperDao() {
        return menuManageDao;
    }

    @Override
    public List<MenuInfoView> queryAllMenuInfoByShopId(long shopId) {
        return menuManageDao.queryAllMenuInfoByShopId(shopId);
    }

    @Override
    public void addMenuInfo(MenuInfo menuInfo) {
        menuManageDao.addEntity(menuInfo);
    }

    @Override
    public void updateMenu(long id,String menuId, String scanCode, String orgId,String price, String name,String photo,String status,String subMenuId) {
        menuManageDao.updateMenu(id,menuId,scanCode,price,name,photo,status,subMenuId);
        //清空缓存
        redisUtil.delete("shop:menu:info:" + orgId);
    }

    @Override
    public void deleteByShopId(long shopId) {
        menuManageDao.deleteByShopId(shopId);
    }
}
