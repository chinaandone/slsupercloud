package com.clever.common.repository;

import com.clever.common.domain.Client;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.view.PaginationView;

import java.util.Collection;
import java.util.List;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-03-07
 * Time: 10:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface ClientManageDao extends IBaseMapperDao<Client, Long>  {

    int count(PaginationView paginationView);

    Collection list(PaginationView paginationView);

    public List<Client> getEntities(Client c);
}
