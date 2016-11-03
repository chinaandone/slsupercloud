package com.clever.common.service.impl;

import com.clever.common.domain.VersionInfo;
import com.clever.common.repository.VersionManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.VersionManageService;
import com.clever.common.service.base.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Info: app版本管理
 * User: gary.zhang@clever-m.com
 * Date: 2016-03-30
 * Time: 10:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("VersionManageService")
@Transactional
public class VersionManageServiceImpl extends BaseService implements VersionManageService {

    private static final Logger logger = LoggerFactory.getLogger(VersionManageServiceImpl.class);

    @Autowired
    private VersionManageDao versionManageDao;

    @Override
    protected IBaseMapperDao getMapperDao() {
        return versionManageDao;
    }

    @Override
    public VersionInfo queryVersionInfo(long shopId){
        return versionManageDao.queryVersionInfo(shopId);
    }

}
