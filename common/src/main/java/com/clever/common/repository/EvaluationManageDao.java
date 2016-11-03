package com.clever.common.repository;

import com.clever.common.domain.Evaluation;
import com.clever.common.domain.Pictrue;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.view.PaginationView;

import java.util.Collection;
import java.util.Map;

/**
 * Info: clever
 * User: enva.liang@clever-m.com
 * Date: 2016-01-25
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface EvaluationManageDao extends IBaseMapperDao<Evaluation, Long>  {

    int count(PaginationView paginationView);

    Collection list(PaginationView paginationView);

    Collection getFeelAverage(PaginationView paginationView);
}
