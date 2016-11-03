package com.clever.common.service.impl;

import com.clever.common.domain.AdSequence;
import com.clever.common.domain.AdvertisementRollMain;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.AdvertisementRollMainManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.AdvertisementRollMainManageService;
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
@Service("AdvertisementRollMainManageService")
@Transactional
public class AdvertisementRollMainManageServiceImpl extends BaseService implements AdvertisementRollMainManageService {

    private static final Logger logger = LoggerFactory.getLogger(AdvertisementRollMainManageServiceImpl.class);

    @Autowired
    private AdvertisementRollMainManageDao advertisementRollMainManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return advertisementRollMainManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = advertisementRollMainManageDao.count(paginationView);
        Collection list = advertisementRollMainManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return advertisementRollMainManageDao.count(paginationView);
    }

    public int countExist(PaginationView paginationView) {
        return advertisementRollMainManageDao.countExist(paginationView);
    }


    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return advertisementRollMainManageDao.list(paginationView);
    }

    public void addEntityBySeq(AdvertisementRollMain a){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("C_Advertisement_Roll_Main"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            a.setAdvertisementMainId(seqNext);
            advertisementRollMainManageDao.addEntity(a);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public List<AdvertisementRollMain> getEntities(AdvertisementRollMain a){
        return advertisementRollMainManageDao.getEntities(a);
    }
}
