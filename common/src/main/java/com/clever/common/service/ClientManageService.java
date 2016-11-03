package com.clever.common.service;

import com.clever.common.domain.Client;
import com.clever.common.domain.Org;
import com.clever.common.service.base.IBaseService;
import com.clever.common.view.PaginationView;

import java.util.Collection;
import java.util.List;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 *          <p>20160303 14:13增加List<Org> getEntities方法</p>
 */
public interface ClientManageService extends IBaseService {
    void listInPage(PaginationView paginationView);

    int count(PaginationView paginationView);

    @SuppressWarnings("rawtypes")
    Collection list(PaginationView paginationView);

    void addEntityBySeq(Client c);

    public List<Client> getEntities(Client c);

}
