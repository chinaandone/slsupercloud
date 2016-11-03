package com.clever.common.repository;

import com.clever.common.domain.Pictrue;
import com.clever.common.domain.UploadAccessLog;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.view.PaginationView;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface UploadAccessLogManageDao extends IBaseMapperDao<UploadAccessLog, Long>  {

    int count(PaginationView paginationView);

    Collection list(PaginationView paginationView);

    public List<UploadAccessLog> getEntities(UploadAccessLog u);

    Collection getLevel1Analysis(PaginationView paginationView);

    Collection getLevel2Analysis(PaginationView paginationView);

    Long countTimeStay(PaginationView paginationView);

    Long countClick(PaginationView paginationView);

    Long countLv2TimeStay(PaginationView paginationView);

    Long countLv2Click(PaginationView paginationView);
}
