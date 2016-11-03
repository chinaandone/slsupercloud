package com.clever.common.service;

import com.clever.common.domain.OrderInfo;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-20
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public interface OrderInfoService {
    int createOrder(OrderInfo orderInfo);

    OrderInfo queryOrderByOrderId(String orderId);

    void update(OrderInfo orderInfo);

}
