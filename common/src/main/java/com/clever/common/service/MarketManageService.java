package com.clever.common.service;

import com.clever.common.client.view.MarketView;
import com.clever.common.domain.Market;
import com.clever.common.domain.MarketMonitor;
import com.clever.common.domain.RollMain;
import com.clever.common.service.base.IBaseService;
import com.clever.common.view.PaginationView;

import java.util.Collection;
import java.util.List;

/**
 * Info: clever
 * User: chay.ni@clever-m.com
 * Date: 2016-07-26
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface MarketManageService extends IBaseService {
    void listInPage(PaginationView paginationView);

    int count(PaginationView paginationView);

    @SuppressWarnings("rawtypes")
    Collection list(PaginationView paginationView);

    Long addEntityBySeq(Market r);

    List<MarketView> getEntities(Market r);

    List<MarketMonitor> getMarketMonitorEntities(Market r);

    void addMarketMonitorEntity(MarketMonitor marketMonitor);

    void deleteMarketMonitorByMarket(Market r);

}
