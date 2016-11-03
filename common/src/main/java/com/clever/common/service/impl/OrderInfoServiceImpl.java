package com.clever.common.service.impl;

import com.clever.common.domain.OrderInfo;
import com.clever.common.repository.OrderInfoDao;
import com.clever.common.repository.base.IBaseMapperDao;
import com.clever.common.service.OrderInfoService;
import com.clever.common.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-20
 * Time: 15:12
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Service("OrderInfoService")
@Transactional
public class OrderInfoServiceImpl extends BaseService<OrderInfo,Long> implements OrderInfoService {

    @Autowired
    private OrderInfoDao orderInfoDao;

    @Override
    protected IBaseMapperDao<OrderInfo, Long> getMapperDao() {
        return orderInfoDao;
    }

    @Override
    public int createOrder(OrderInfo orderInfo) {
        return 0;
    }

    @Override
    public OrderInfo queryOrderByOrderId(String orderId) {
        return null;
    }

    @Override
    public void update(OrderInfo orderInfo) {
        orderInfoDao.updateEntity(orderInfo);
    }
}
