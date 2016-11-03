package com.clever.common.service.impl;

import com.clever.common.domain.AdSequence;
import com.clever.common.domain.Pictrue;
import com.clever.common.domain.Video;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.PictrueManageDao;
import com.clever.common.repository.VideoManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.PictrueManageService;
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
@Service("VideoManageService")
@Transactional
public class VideoManageServiceImpl extends BaseService implements VideoManageService {

    private static final Logger logger = LoggerFactory.getLogger(VideoManageServiceImpl.class);

    @Autowired
    private VideoManageDao videoManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return videoManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = videoManageDao.count(paginationView);
        Collection list = videoManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return videoManageDao.count(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return videoManageDao.list(paginationView);
    }

    public void addEntityBySeq(Video v){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("C_Video"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            v.setVideoId(seqNext);
            videoManageDao.addEntity(v);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public List<Video> getEntities(Video v){
        return videoManageDao.getEntities(v);
    }
}
