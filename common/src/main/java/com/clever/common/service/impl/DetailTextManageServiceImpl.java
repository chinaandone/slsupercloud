package com.clever.common.service.impl;

import com.clever.common.domain.AdSequence;
import com.clever.common.domain.DetailText;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.DetailTextManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.DetailTextManageService;
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
@Service("DetailTextManageService")
@Transactional
public class DetailTextManageServiceImpl extends BaseService implements DetailTextManageService {

    private static final Logger logger = LoggerFactory.getLogger(DetailTextManageServiceImpl.class);

    @Autowired
    private DetailTextManageDao detailTextManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return detailTextManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = detailTextManageDao.count(paginationView);
        Collection list = detailTextManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return detailTextManageDao.count(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return detailTextManageDao.list(paginationView);
    }

    public void addEntityBySeq(DetailText d){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("C_Detail_Text"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            d.setDetailTextId(seqNext);
            detailTextManageDao.addEntity(d);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public List<DetailText> getEntities(DetailText d){
        return detailTextManageDao.getEntities(d);
    }
}
