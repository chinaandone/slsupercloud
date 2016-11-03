package com.clever.common.repository;

import com.clever.common.domain.TasteInfo;
import com.clever.common.repository.base.IBaseMapperDao;

import java.util.List;

/**
 * Info: 小超人菜品口味
 * User: Gary.zhang@clever-m.com
 * Date: 2016-02-04
 * Time: 15:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface TasteManageDao extends IBaseMapperDao<TasteInfo, Long> {
    List<TasteInfo> queryAllMenuTaste() ;


}
