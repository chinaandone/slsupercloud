package com.clever.common.service.impl;

import com.clever.common.domain.AdSequence;
import com.clever.common.domain.RollDetail;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.RollDetailManageDao;
import com.clever.common.repository.RollMainManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.RollDetailManageService;
import com.clever.common.service.RollMainManageService;
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
@Service("RollDetailManageService")
@Transactional
public class RollDetailManageServiceImpl extends BaseService implements RollDetailManageService {

    private static final Logger logger = LoggerFactory.getLogger(RollDetailManageServiceImpl.class);

    @Autowired
    private RollDetailManageDao rollDetailManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return rollDetailManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = rollDetailManageDao.count(paginationView);
        Collection list = rollDetailManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return rollDetailManageDao.count(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return rollDetailManageDao.list(paginationView);
    }

    public void addEntityBySeq(RollDetail r){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("C_Roll_Detail"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            r.setRollDetailId(seqNext);
            rollDetailManageDao.addEntity(r);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public List<RollDetail> findList(RollDetail r){
        return rollDetailManageDao.findList(r);
    }
}
