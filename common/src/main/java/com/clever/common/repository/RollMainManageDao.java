package com.clever.common.repository;

import com.clever.common.domain.Pictrue;
import com.clever.common.domain.RollMain;
import com.clever.common.repository.base.IBaseMapperDao;
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
 */
public interface RollMainManageDao extends IBaseMapperDao<RollMain, Long>  {

    int count(PaginationView paginationView);

    Collection list(PaginationView paginationView);

    public List<RollMain> getEntities(RollMain r);

}
