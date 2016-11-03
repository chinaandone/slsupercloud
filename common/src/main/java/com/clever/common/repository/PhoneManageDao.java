package com.clever.common.repository;

import com.clever.common.domain.ComputerInfo;
import com.clever.common.domain.InfoPhone;
import com.clever.common.repository.base.IBaseMapperDao;

import java.util.List;

/**
 * Info: 实时状态监控（大学士）
 * User: Ddido.deng@clever-m.com
 * Date: 2016-04-28
 * Time: 10:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface PhoneManageDao extends IBaseMapperDao<InfoPhone, Long> {
    public List<InfoPhone> getEntities(InfoPhone c);

}
