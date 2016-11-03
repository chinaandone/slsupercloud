package com.clever.common.service.impl;

import com.clever.common.domain.TasteInfo;
import com.clever.common.repository.TasteManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.TasteManageService;
import com.clever.common.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Info: 小超人菜品口味
 * User: Gary.zhang@clever-m.com
 * Date: 2016-02-04
 * Time: 15:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("TasteManageService")
@Transactional
public class TasteManageServiceImpl extends BaseService<TasteInfo,Long> implements TasteManageService {
    @Autowired
    private TasteManageDao tasteManageDao;

    @Override
    protected IBaseMapperDao<TasteInfo, Long> getMapperDao() {
        return tasteManageDao;
    }

    @Override
    public List<TasteInfo> queryAllMenuTaste() {
        return tasteManageDao.queryAllMenuTaste();
    }

}
