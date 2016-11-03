package com.clever.common.service.impl;

import com.clever.common.domain.AccessLog;
import com.clever.common.domain.AdSequence;
import com.clever.common.domain.Path;
import com.clever.common.repository.AccessLogManageDao;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.PathManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.AccessLogManageService;
import com.clever.common.service.PathManageService;
import com.clever.common.service.base.BaseService;
import com.clever.common.view.PaginationView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 15:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("AccessLogManageService")
@Transactional
public class AccessLogManageServiceImpl extends BaseService implements AccessLogManageService {

    private static final Logger logger = LoggerFactory.getLogger(AccessLogManageServiceImpl.class);

    @Autowired
    private AccessLogManageDao accessLogManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return accessLogManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = accessLogManageDao.count(paginationView);
        Collection list = accessLogManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return accessLogManageDao.count(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return accessLogManageDao.list(paginationView);
    }

    public void addEntityBySeq(AccessLog a){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("C_Access_Log"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            a.setAccessId(seqNext);
            accessLogManageDao.addEntity(a);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }
}
