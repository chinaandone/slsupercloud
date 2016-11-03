package com.clever.common.service;

import com.clever.common.domain.Pictrue;
import com.clever.common.domain.UploadAccessLog;
import com.clever.common.service.base.IBaseService;
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
public interface UploadAccessLogManageService extends IBaseService {
    void listInPage(PaginationView paginationView);

    int count(PaginationView paginationView);

    @SuppressWarnings("rawtypes")
    Collection list(PaginationView paginationView);

    void addEntityBySeq(UploadAccessLog u);

    List<UploadAccessLog> getEntities(UploadAccessLog u);

    Collection getLevel1Analysis(PaginationView paginationView);

    Collection getLevel2Analysis(PaginationView paginationView);

    Long countTimeStay(PaginationView paginationView);

    Long countClick(PaginationView paginationView);

    Long countLv2TimeStay(PaginationView paginationView);

    Long countLv2Click(PaginationView paginationView);
}
