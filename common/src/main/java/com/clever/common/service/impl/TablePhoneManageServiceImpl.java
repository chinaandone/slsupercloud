package com.clever.common.service.impl;

import com.clever.common.domain.AdSequence;
import com.clever.common.domain.Table;
import com.clever.common.domain.TablePhone;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.TableManageDao;
import com.clever.common.repository.TablePhoneManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.TableManageService;
import com.clever.common.service.TablePhoneManageService;
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
@Service("TablePhoneManageService")
@Transactional
public class TablePhoneManageServiceImpl extends BaseService implements TablePhoneManageService {

    private static final Logger logger = LoggerFactory.getLogger(TablePhoneManageServiceImpl.class);

    @Autowired
    private TablePhoneManageDao tablePhoneManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return tablePhoneManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = tablePhoneManageDao.count(paginationView);
        Collection list = tablePhoneManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return tablePhoneManageDao.count(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return tablePhoneManageDao.list(paginationView);
    }

    public void addEntityBySeq(TablePhone t){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("R_Info_Phone"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            t.setTablePhoneId(seqNext);
            tablePhoneManageDao.addEntity(t);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public List<TablePhone> getEntities(TablePhone r){
        return tablePhoneManageDao.getEntities(r);
    }
}
