package com.clever.common.service.impl;

import com.clever.common.domain.AdSequence;
import com.clever.common.domain.Video;
import com.clever.common.domain.VideoBusiness;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.VideoBusinessManageDao;
import com.clever.common.repository.VideoManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.VideoBusinessManageService;
import com.clever.common.service.VideoManageService;
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
@Service("VideoBusinessManageService")
@Transactional
public class VideoBusinessManageServiceImpl extends BaseService implements VideoBusinessManageService {

    private static final Logger logger = LoggerFactory.getLogger(VideoBusinessManageServiceImpl.class);

    @Autowired
    private VideoBusinessManageDao videoBusinessManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return videoBusinessManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = videoBusinessManageDao.count(paginationView);
        Collection list = videoBusinessManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return videoBusinessManageDao.count(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return videoBusinessManageDao.list(paginationView);
    }

    public void addEntityBySeq(VideoBusiness v){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("C_Video_Business"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            v.setBusinessId(seqNext);
            videoBusinessManageDao.addEntity(v);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public List<VideoBusiness> getEntities(VideoBusiness v){
        return videoBusinessManageDao.getEntities(v);
    }
}
