package com.clever.common.service.impl;

import com.clever.common.domain.AdSequence;
import com.clever.common.domain.TableWatch;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.TableWatchManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.TableWatchManageService;
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
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-03-17
 * Time: 15:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("TableWatchManageService")
@Transactional
public class TableWatchManageServiceImpl extends BaseService implements TableWatchManageService {

    private static final Logger logger = LoggerFactory.getLogger(TableWatchManageServiceImpl.class);

    @Autowired
    private TableWatchManageDao tableWatchManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return tableWatchManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = tableWatchManageDao.count(paginationView);
        Collection list = tableWatchManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return tableWatchManageDao.count(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return tableWatchManageDao.list(paginationView);
    }

    public void addEntityBySeq(TableWatch t){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("R_Info_Watch"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            t.setTableWatchId(seqNext);
            tableWatchManageDao.addEntity(t);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }


    public List<TableWatch> getEntities(TableWatch p) {
        return tableWatchManageDao.getEntities(p);
    }


}
