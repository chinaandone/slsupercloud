package com.clever.common.service.impl;

import com.clever.common.domain.AdSequence;
import com.clever.common.domain.RollMain;
import com.clever.common.domain.RollPublish;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.RollMainManageDao;
import com.clever.common.repository.RollPublishManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.RollMainManageService;
import com.clever.common.service.RollPublishManageService;
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
 * Date: 2016-01-25
 * Time: 15:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("RollPublishManageService")
@Transactional
public class RollPublishManageServiceImpl extends BaseService implements RollPublishManageService {

    private static final Logger logger = LoggerFactory.getLogger(RollPublishManageServiceImpl.class);

    @Autowired
    private RollPublishManageDao rollPublishManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return rollPublishManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = rollPublishManageDao.count(paginationView);
        Collection list = rollPublishManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return rollPublishManageDao.count(paginationView);
    }

    public int countExist(PaginationView paginationView) {
        return rollPublishManageDao.countExist(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return rollPublishManageDao.list(paginationView);
    }

    public void addEntityBySeq(RollPublish r){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("C_Roll_Publish"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            r.setRollPublishId(seqNext);
            rollPublishManageDao.addEntity(r);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public List<RollPublish> getEntities(RollPublish r){
        return rollPublishManageDao.getEntities(r);
    }

}
