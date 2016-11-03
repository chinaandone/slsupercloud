package com.clever.common.repository;

import com.clever.common.domain.Material;
import com.clever.common.domain.MaterialDTView;
import com.clever.common.domain.Video;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.view.PaginationView;

import java.util.Collection;
import java.util.List;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-04-18
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface MaterialManageDao extends IBaseMapperDao<Material, Long>  {

    int count(PaginationView paginationView);

    Collection list(PaginationView paginationView);

    List<MaterialDTView> getEntities(Material v);

}
