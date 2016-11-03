package com.clever.common.service.impl;

import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.PictrueManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.AdSequenceManageService;
import com.clever.common.service.PictrueManageService;
import com.clever.common.service.base.BaseService;
import com.clever.common.view.PaginationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 15:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("AdSequenceManageService")
@Transactional
public class AdSequenceManageServiceImpl extends BaseService implements AdSequenceManageService {
    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return adSequenceManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = adSequenceManageDao.count(paginationView);
        Collection list = adSequenceManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return adSequenceManageDao.count(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return adSequenceManageDao.list(paginationView);
    }
}
