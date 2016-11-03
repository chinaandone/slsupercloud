package com.clever.common.repository;


import com.clever.common.domain.MenuInfo;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.view.PaginationView;

import java.util.Collection;

public interface PaginationDao extends IBaseMapperDao<MenuInfo, Long> {

	int count(PaginationView paginationView);

	@SuppressWarnings("rawtypes")
	Collection list(PaginationView paginationView);

}
