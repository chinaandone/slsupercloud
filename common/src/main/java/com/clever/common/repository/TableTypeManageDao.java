package com.clever.common.repository;

import com.clever.common.domain.TableTypeBean;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.view.PaginationView;

import java.util.Collection;
import java.util.List;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-20
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface TableTypeManageDao extends IBaseMapperDao<TableTypeBean, Long> {
    int count(PaginationView paginationView);

    Collection list(PaginationView paginationView);

    public List<TableTypeBean> getEntities(TableTypeBean t);

}
