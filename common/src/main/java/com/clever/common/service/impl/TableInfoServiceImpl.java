package com.clever.common.service.impl;

import com.clever.common.domain.TableInfo;
import com.clever.common.repository.TableInfoDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.TableInfoService;
import com.clever.common.service.base.BaseService;
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
@Service("TableInfoService")
@Transactional
public class TableInfoServiceImpl extends BaseService<TableInfo,Long> implements TableInfoService{
    @Autowired
    private TableInfoDao tableInfoDao;

    @Override
    protected IBaseMapperDao<TableInfo, Long> getMapperDao() {
        return tableInfoDao;
    }

    @Override
    public List<TableInfo> queryAllTableInfoByShopId(long shopId) {
        return tableInfoDao.queryAllTableInfoByShopId(shopId);
    }

}
