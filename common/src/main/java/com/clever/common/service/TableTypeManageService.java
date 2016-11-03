package com.clever.common.service;

import com.clever.common.domain.TableTypeBean;
import com.clever.common.service.base.IBaseService;
import com.clever.common.view.PaginationView;

import java.util.Collection;
import java.util.List;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-03-18
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface TableTypeManageService extends IBaseService {

    void listInPage(PaginationView paginationView);

    int count(PaginationView paginationView);

    @SuppressWarnings("rawtypes")
    Collection list(PaginationView paginationView);

    void addEntityBySeq(TableTypeBean p);

    public List<TableTypeBean> getEntities(TableTypeBean t);

}
