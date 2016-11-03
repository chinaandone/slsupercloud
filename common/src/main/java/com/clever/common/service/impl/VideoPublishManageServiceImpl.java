package com.clever.common.service.impl;

import com.clever.common.domain.AdSequence;
import com.clever.common.domain.VideoBusiness;
import com.clever.common.domain.VideoPublish;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.VideoBusinessManageDao;
import com.clever.common.repository.VideoPublishManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.VideoBusinessManageService;
import com.clever.common.service.VideoPublishManageService;
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
@Service("VideoPublishManageService")
@Transactional
public class VideoPublishManageServiceImpl extends BaseService implements VideoPublishManageService {

    private static final Logger logger = LoggerFactory.getLogger(VideoPublishManageServiceImpl.class);

    @Autowired
    private VideoPublishManageDao videoPublishManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return videoPublishManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = videoPublishManageDao.count(paginationView);
        Collection list = videoPublishManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return videoPublishManageDao.count(paginationView);
    }

    public int countExist(PaginationView paginationView){return videoPublishManageDao.countExist(paginationView);}

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return videoPublishManageDao.list(paginationView);
    }

    public void addEntityBySeq(VideoPublish v){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("C_Video_Publish"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            v.setVideoPublishId(seqNext);
            videoPublishManageDao.addEntity(v);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public List<VideoPublish> getEntities(VideoPublish v){
        return videoPublishManageDao.getEntities(v);
    }
}
