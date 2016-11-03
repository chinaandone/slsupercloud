package com.clever.common.service.impl;

import com.clever.common.client.view.MaterialView;
import com.clever.common.domain.AdSequence;
import com.clever.common.domain.MaterialBusiness;
import com.clever.common.domain.MaterialBusinessDTView;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.MaterialBusinessManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.MaterialBusinessManageService;
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
 * Date: 2016-04-19
 * Time: 15:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("MaterialBusinessManageService")
@Transactional
public class MaterialBusinessManageServiceImpl extends BaseService implements MaterialBusinessManageService {

    private static final Logger logger = LoggerFactory.getLogger(MaterialBusinessManageServiceImpl.class);

    @Autowired
    private MaterialBusinessManageDao materialBusinessManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return materialBusinessManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = materialBusinessManageDao.count(paginationView);
        Collection list = materialBusinessManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return materialBusinessManageDao.count(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return materialBusinessManageDao.list(paginationView);
    }

    public void addEntityBySeq(MaterialBusiness v){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("C_Material_Business"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            v.setMaterialBusinessId(seqNext);
            materialBusinessManageDao.addEntity(v);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public List<MaterialBusinessDTView> getEntities(MaterialBusiness v){
        return materialBusinessManageDao.getEntities(v);
    }

    public List<MaterialBusinessDTView> getBinEntities(MaterialBusiness v){
        return materialBusinessManageDao.getBinEntities(v);
    }
}
