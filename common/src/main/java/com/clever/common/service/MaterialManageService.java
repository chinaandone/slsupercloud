package com.clever.common.service;

import com.clever.common.domain.Material;
import com.clever.common.domain.MaterialDTView;
import com.clever.common.domain.Video;
import com.clever.common.service.base.IBaseService;
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
public interface MaterialManageService extends IBaseService {
    void listInPage(PaginationView paginationView);

    int count(PaginationView paginationView);

    @SuppressWarnings("rawtypes")
    Collection list(PaginationView paginationView);

    void addEntityBySeq(Material p);

    List<MaterialDTView> getEntities(Material m);

}
