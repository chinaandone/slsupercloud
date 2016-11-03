package com.clever.common.repository;

import com.clever.common.domain.TableInfo;
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
public interface TableInfoDao extends IBaseMapperDao<TableInfo, Long> {
    //分页查询商家菜品列表
    List<TableInfo> queryAllTableInfoByShopId(long shopId) ;

}
