package com.clever.common.repository;

import com.clever.common.domain.ComputerInfo;
import com.clever.common.domain.OrderInfo;
import com.clever.common.domain.Table;
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
public interface MonitorManageDao extends IBaseMapperDao<ComputerInfo, Long> {
    public List<ComputerInfo> getEntities(ComputerInfo c);

}
