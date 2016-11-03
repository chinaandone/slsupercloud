package com.clever.common.service.impl;

import com.clever.common.domain.TableType;
import com.clever.common.domain.TableTypeView;
import com.clever.common.repository.TableTypeDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.TableTypeService;
import com.clever.common.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Info: 餐桌类型
 * User: Gary.zhang@clever-m.com
 * Date: 2016-02-04
 * Time: 15:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("TableTypeService")
@Transactional
public class TableTypeServiceImpl extends BaseService implements TableTypeService {
    @Autowired
    private TableTypeDao tableTypeDao;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return tableTypeDao;
    }

    @Override
    public List<TableTypeView> queryAllTableTypeByShopId(long shopId) {
        return tableTypeDao.queryAllTableTypeByShopId(shopId);
    }

}
