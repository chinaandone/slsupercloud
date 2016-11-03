package com.clever.common.service.impl;

import com.clever.common.bean.page.Pager;
import com.clever.common.bean.page.WebPage;
import com.clever.common.domain.MenuInfo;
import com.clever.common.repository.PaginationDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.PaginationService;
import com.clever.common.service.base.BaseService;
import com.clever.common.view.PaginationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class PaginationServiceImpl extends BaseService implements PaginationService {
	@Autowired
    private PaginationDao paginationDao;

	public void setPaginationDao(PaginationDao paginationDao) {
		this.paginationDao = paginationDao;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void listInPage(PaginationView paginationView) {
		int count = paginationDao.count(paginationView);
		Collection list = paginationDao.list(paginationView);
		paginationView.setAaData(list);
		paginationView.setITotalDisplayRecords(count);
		paginationView.setITotalRecords(count);
	}

	public int count(PaginationView paginationView) {
		return paginationDao.count(paginationView);
	}
	
	@SuppressWarnings("rawtypes")
	public Collection list(PaginationView paginationView) {
		return paginationDao.list(paginationView);
	}

	@Override
	protected IBaseMapperDao getMapperDao() {
		return paginationDao;
	}
}
