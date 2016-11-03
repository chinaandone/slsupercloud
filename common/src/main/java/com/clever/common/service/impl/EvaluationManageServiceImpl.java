package com.clever.common.service.impl;

import com.clever.common.domain.AdSequence;
import com.clever.common.domain.Evaluation;
import com.clever.common.domain.Pictrue;
import com.clever.common.repository.AdSequenceManageDao;
import com.clever.common.repository.EvaluationManageDao;
import com.clever.common.repository.PictrueManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.EvaluationManageService;
import com.clever.common.service.PictrueManageService;
import com.clever.common.service.base.BaseService;
import com.clever.common.view.PaginationView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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
@Service("EvaluationManageService")
@Transactional
public class EvaluationManageServiceImpl extends BaseService implements EvaluationManageService {

    private static final Logger logger = LoggerFactory.getLogger(EvaluationManageServiceImpl.class);

    @Autowired
    private EvaluationManageDao evaluationManageDao;

    @Autowired
    private AdSequenceManageDao adSequenceManageDao;

    Lock lock=new ReentrantLock();
    volatile long seqNext;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return evaluationManageDao;
    }

	@SuppressWarnings("rawtypes")
    @Override
    public void listInPage(PaginationView paginationView) {
        int count = evaluationManageDao.count(paginationView);
        Collection list = evaluationManageDao.list(paginationView);
        paginationView.setAaData(list);
        paginationView.setITotalDisplayRecords(count);
        paginationView.setITotalRecords(count);
    }

    public int count(PaginationView paginationView) {
        return evaluationManageDao.count(paginationView);
    }

    @SuppressWarnings("rawtypes")
    public Collection list(PaginationView paginationView) {
        return evaluationManageDao.list(paginationView);
    }

    public void addEntityBySeq(Evaluation v){
        lock.lock();
        try {
            AdSequence ad = (AdSequence)adSequenceManageDao.getEntity(new AdSequence("C_Evaluation"));
            AtomicLong count = new AtomicLong(1);
            seqNext = (ad.getCurrentnextsys() + count.getAndIncrement());
            ad.setCurrentnextsys(seqNext);
            adSequenceManageDao.updateEntity(ad);
            v.setEvaluationId(seqNext);
            evaluationManageDao.addEntity(v);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public Collection getFeelAverage(PaginationView paginationView){
        return evaluationManageDao.getFeelAverage(paginationView);
    }
}
