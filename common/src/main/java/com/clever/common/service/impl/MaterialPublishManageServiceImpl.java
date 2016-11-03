package com.clever.common.service.impl;

import com.clever.common.client.view.MaterialView;
import com.clever.common.domain.*;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.MaterialPublishManageDao;
import com.clever.common.repository.VideoPublishManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.MaterialPublishManageService;
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
 * User: chay@clever-m.com
 * Date: 2016-04-20
 * Time: 15:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("MaterialPublishManageService")
@Transactional
public class MaterialPublishManageServiceImpl extends BaseService implements MaterialPublishManageService {

    private static final Logger logger = LoggerFactory.getLogger(MaterialPublishManageServiceImpl.class);

    @Autowired
    private MaterialPublishManageDao materialPublishManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return materialPublishManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = materialPublishManageDao.count(paginationView);
        Collection list = materialPublishManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return materialPublishManageDao.count(paginationView);
    }

    public int countExist(PaginationView paginationView){return materialPublishManageDao.countExist(paginationView);}

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return materialPublishManageDao.list(paginationView);
    }

    public void addEntityBySeq(MaterialPublish v){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("C_Material_Publish"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            v.setMaterialPublishId(seqNext);
            materialPublishManageDao.addEntity(v);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public List<MaterialPublishDTView> getEntities(MaterialPublish v){
        return materialPublishManageDao.getEntities(v);
    }
}
