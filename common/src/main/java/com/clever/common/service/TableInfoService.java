package com.clever.common.service;

import com.clever.common.domain.TableInfo;

import java.util.List;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-20
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface TableInfoService {
    //分页查询商家菜品
    List<TableInfo> queryAllTableInfoByShopId(long shopId);


}
