package com.clever.common.repository;

import com.clever.common.client.view.MaterialView;
import com.clever.common.domain.Material;
import com.clever.common.domain.MaterialPublish;
import com.clever.common.domain.MaterialPublishDTView;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.view.PaginationView;

import java.util.Collection;
import java.util.List;

/**
 * Info: clever
 * User: chay@clever-m.com
 * Date: 2016-04-20
 * Time: 18:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface MaterialPublishManageDao extends IBaseMapperDao<MaterialPublish, Long>  {

    int count(PaginationView paginationView);

    Collection list(PaginationView paginationView);

    List<MaterialPublishDTView> getEntities(MaterialPublish v);

    int countExist(PaginationView paginationView);
}
