package com.clever.common.service.impl;

import com.clever.common.domain.ComputerInfo;
import com.clever.common.domain.InfoPhone;
import com.clever.common.domain.bean.*;
import com.clever.common.repository.MonitorManageDao;
import com.clever.common.repository.PhoneManageDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.MonitorManageService;
import com.clever.common.service.PhoneManageService;
import com.clever.common.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Info: 手环（大学士）
 * User: Ddido.deng@clever-m.com
 * Date: 2016-04-20
 * Time: 10:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("PhoneManageService")
@Transactional
public class PhoneManageServiceImpl extends BaseService implements PhoneManageService {

    @Autowired
    private PhoneManageDao phoneManageDao;

    @Override
    protected IBaseMapperDao<InfoPhone, Long> getMapperDao() {
        return phoneManageDao;
    }


    /**
     * 根据shopid获取所有的手环
     *
     * @param c
     * @return
     */
    @Override
    public List<InfoPhone> getEntities(InfoPhone c) {
        return phoneManageDao.getEntities(c);
    }
}
