package com.clever.common.service.impl;

import com.clever.common.client.view.MarketView;
import com.clever.common.domain.AdSequence;
import com.clever.common.domain.Market;
import com.clever.common.domain.MarketMonitor;
import com.clever.common.domain.RollMain;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.MarketManageDao;
import com.clever.common.repository.RollMainManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.MarketManageService;
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
 * User: chay.ni@clever-m.com
 * Date: 2016-07-26
 * Time: 15:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("MarketManageService")
@Transactional
public class MarketManageServiceImpl extends BaseService implements MarketManageService {

    private static final Logger logger = LoggerFactory.getLogger(MarketManageServiceImpl.class);

    @Autowired
    private MarketManageDao marketManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return marketManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = marketManageDao.count(paginationView);
        Collection list = marketManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return marketManageDao.count(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return marketManageDao.list(paginationView);
    }

    public Long addEntityBySeq(Market r){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("C_Market"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            r.setMarketId(seqNext);
            marketManageDao.addEntity(r);
            return seqNext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public List<MarketView> getEntities(Market r){
        return marketManageDao.getEntities(r);
    }

    public List<MarketMonitor> getMarketMonitorEntities(Market r){
        return marketManageDao.getMarketMonitorEntities(r);
    }

    public void addMarketMonitorEntity(MarketMonitor marketMonitor){
        marketManageDao.addMarketMonitorEntity(marketMonitor);
    }

    public void deleteMarketMonitorByMarket(Market r){
        marketManageDao.deleteMarketMonitorByMarket(r);
    }
}
