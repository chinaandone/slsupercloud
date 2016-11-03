package com.clever.common.service;

import com.clever.common.client.view.MaterialView;
import com.clever.common.domain.MaterialPublish;
import com.clever.common.domain.MaterialPublishDTView;
import com.clever.common.service.base.IBaseService;
import com.clever.common.view.PaginationView;

import java.util.Collection;
import java.util.List;

/**
 * Info: clever
 * User: chay@clever-m.com
 * Date: 2016-04-20
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface MaterialPublishManageService extends IBaseService {
    void listInPage(PaginationView paginationView);

    int count(PaginationView paginationView);

    @SuppressWarnings("rawtypes")
    Collection list(PaginationView paginationView);

    void addEntityBySeq(MaterialPublish m);

    List<MaterialPublishDTView> getEntities(MaterialPublish m);

    int countExist(PaginationView paginationView);
}
