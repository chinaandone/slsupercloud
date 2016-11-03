package com.clever.common.service;

import com.clever.common.service.base.BaseService;
import com.clever.common.service.base.IBaseService;
import com.clever.common.view.PaginationView;

import java.util.Collection;

public interface PaginationService extends IBaseService {

	void listInPage(PaginationView paginationView);

	int count(PaginationView paginationView);

	@SuppressWarnings("rawtypes")
	Collection list(PaginationView paginationView);
}
