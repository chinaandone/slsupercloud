package com.clever.common.service.impl;

import com.clever.common.domain.AdSequence;
import com.clever.common.domain.TableTypeBean;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.TableTypeManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.TableTypeManageService;
import com.clever.common.service.base.BaseService;
import com.clever.common.view.PaginationView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Info: 餐桌类型
 * User: Gary.zhang@clever-m.com
 * Date: 2016-02-04
 * Time: 15:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("TableTypeManageService")
@Transactional
public class TableTypeManageServiceImpl extends BaseService implements TableTypeManageService {
    private static final Logger logger = LoggerFactory.getLogger(TableTypeManageServiceImpl.class);

    @Autowired
    private TableTypeManageDao tableTypeManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return tableTypeManageDao;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = tableTypeManageDao.count(paginationView);
        Collection list = tableTypeManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return tableTypeManageDao.count(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return tableTypeManageDao.list(paginationView);
    }

    public void addEntityBySeq(TableTypeBean t){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("R_Table_Type"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            t.setTableTypeId(seqNext);
            tableTypeManageDao.addEntity(t);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public List<TableTypeBean> getEntities(TableTypeBean t){
        return tableTypeManageDao.getEntities(t);
    }
}
