package com.clever.common.repository;

import com.clever.common.client.view.MaterialView;
import com.clever.common.domain.MaterialBusiness;
import com.clever.common.domain.MaterialBusinessDTView;
import com.clever.common.domain.VideoBusiness;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.view.PaginationView;

import java.util.Collection;
import java.util.List;

/**
 * Info: clever
 * User: chay@clever-m.com
 * Date: 2016-04-19
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface MaterialBusinessManageDao extends IBaseMapperDao<MaterialBusiness, Long>  {

    int count(PaginationView paginationView);

    Collection list(PaginationView paginationView);

    List<MaterialBusinessDTView> getEntities(MaterialBusiness v);

    List<MaterialBusinessDTView> getBinEntities(MaterialBusiness v);

}
