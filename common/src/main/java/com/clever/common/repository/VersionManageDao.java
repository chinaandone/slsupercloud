package com.clever.common.repository;

import com.clever.common.domain.VersionInfo;
import com.clever.common.repository.base.IBaseMapperDao;

/**
 * Info: app版本管理
 * User: gary.zhang@clever-m.com
 * Date: 2016-03-30
 * Time: 10:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface VersionManageDao extends IBaseMapperDao<VersionInfo, Long>  {

    VersionInfo queryVersionInfo(long shopId);
}
