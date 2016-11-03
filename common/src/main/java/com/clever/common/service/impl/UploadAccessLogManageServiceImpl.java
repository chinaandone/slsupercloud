package com.clever.common.service.impl;

import com.clever.common.domain.AdSequence;
import com.clever.common.domain.Pictrue;
import com.clever.common.domain.UploadAccessLog;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.PictrueManageDao;
import com.clever.common.repository.UploadAccessLogManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.PictrueManageService;
import com.clever.common.service.UploadAccessLogManageService;
import com.clever.common.service.base.BaseService;
import com.clever.common.view.PaginationView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
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
@Service("UploadAccessLogManageService")
@Transactional
public class UploadAccessLogManageServiceImpl extends BaseService implements UploadAccessLogManageService {

    private static final Logger logger = LoggerFactory.getLogger(UploadAccessLogManageServiceImpl.class);

    @Autowired
    private UploadAccessLogManageDao uploadAccessLogManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return uploadAccessLogManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = uploadAccessLogManageDao.count(paginationView);
        Collection list = uploadAccessLogManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return uploadAccessLogManageDao.count(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return uploadAccessLogManageDao.list(paginationView);
    }

    public Collection getLevel1Analysis(PaginationView paginationView){
        return uploadAccessLogManageDao.getLevel1Analysis(paginationView);
    }

    public Collection getLevel2Analysis(PaginationView paginationView){
        return uploadAccessLogManageDao.getLevel2Analysis(paginationView);
    }

    public Long countTimeStay(PaginationView paginationView){
        return uploadAccessLogManageDao.countTimeStay(paginationView);
    }

    public Long countClick(PaginationView paginationView){
        return uploadAccessLogManageDao.countClick(paginationView);
    }

    public Long countLv2TimeStay(PaginationView paginationView){
        return uploadAccessLogManageDao.countLv2TimeStay(paginationView);
    }

    public Long countLv2Click(PaginationView paginationView){
        return uploadAccessLogManageDao.countLv2Click(paginationView);
    }

    public void addEntityBySeq(UploadAccessLog u){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("C_Upload_Access_Log"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            u.setUploadAccessId(seqNext);
            uploadAccessLogManageDao.addEntity(u);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public List<UploadAccessLog> getEntities(UploadAccessLog u){
        return uploadAccessLogManageDao.getEntities(u);
    }
}
