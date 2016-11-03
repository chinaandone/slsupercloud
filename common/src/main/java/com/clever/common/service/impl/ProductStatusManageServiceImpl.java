package com.clever.common.service.impl;

import com.clever.common.domain.AdSequence;
import com.clever.common.domain.ProductStatusLog;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.ProductStatusLogManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.ProductStatusManageService;
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
 * Date: 2016-08-17
 * Time: 10:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("ProductStatusLogManageService")
@Transactional
public class ProductStatusManageServiceImpl extends BaseService implements ProductStatusManageService {

    private static final Logger logger = LoggerFactory.getLogger(ProductStatusManageServiceImpl.class);

    @Autowired
    private ProductStatusLogManageDao productStatusLogManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return productStatusLogManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = productStatusLogManageDao.count(paginationView);
        Collection list = productStatusLogManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return productStatusLogManageDao.count(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return productStatusLogManageDao.list(paginationView);
    }

    public void addEntityBySeq(ProductStatusLog c){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("C_Product_Status_Log"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            c.setProductStatusLogId(seqNext);
            productStatusLogManageDao.addEntity(c);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public List<ProductStatusLog> getEntities(ProductStatusLog c){
        return productStatusLogManageDao.getEntities(c);
    }
}
