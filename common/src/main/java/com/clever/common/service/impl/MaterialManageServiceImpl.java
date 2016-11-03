package com.clever.common.service.impl;

import com.clever.common.domain.AdSequence;
import com.clever.common.domain.Material;
import com.clever.common.domain.MaterialDTView;
import com.clever.common.domain.Video;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.MaterialManageDao;
import com.clever.common.repository.VideoManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.MaterialManageService;
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
 * User: chay.ni@clever-m.com
 * Date: 2016-04-18
 * Time: 15:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("MaterialManageService")
@Transactional
public class MaterialManageServiceImpl extends BaseService implements MaterialManageService {

    private static final Logger logger = LoggerFactory.getLogger(MaterialManageServiceImpl.class);

    @Autowired
    private MaterialManageDao materialManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return materialManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = materialManageDao.count(paginationView);
        Collection list = materialManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return materialManageDao.count(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return materialManageDao.list(paginationView);
    }

    public void addEntityBySeq(Material v){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("C_Material"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            v.setMaterialId(seqNext);
            materialManageDao.addEntity(v);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public List<MaterialDTView> getEntities(Material v){
        return materialManageDao.getEntities(v);
    }
}
