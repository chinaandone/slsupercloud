package com.clever.common.repository;

import com.clever.common.domain.ProductStatusLog;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.view.PaginationView;

import java.util.Collection;
import java.util.List;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-08-17
 * Time: 10:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface ProductStatusLogManageDao extends IBaseMapperDao<ProductStatusLog, Long>  {

    int count(PaginationView paginationView);

    Collection list(PaginationView paginationView);

    public List<ProductStatusLog> getEntities(ProductStatusLog c);
}
