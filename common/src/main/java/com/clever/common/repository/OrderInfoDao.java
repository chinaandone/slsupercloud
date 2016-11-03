package com.clever.common.repository;

import com.clever.common.domain.OrderInfo;
import com.clever.common.repository.base.IBaseMapperDao;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-20
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface OrderInfoDao extends IBaseMapperDao<OrderInfo, Long> {
    int createOrder(OrderInfo orderInfo);

}
