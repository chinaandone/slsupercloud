package com.clever.common.repository.impl;

import com.clever.common.domain.OrderInfo;
import com.clever.common.repository.OrderInfoDao;
import com.clever.common.repository.base.BaseMybatisDAO;
import org.springframework.stereotype.Repository;

/**
 * Info: clever
 * User: Gary.zhang@clever-m.com
 * Date: 2016-01-20
 * Time: 14:29
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
@Repository
public class OrderInfoDaoImpl extends BaseMybatisDAO<OrderInfo, Long> implements OrderInfoDao {

    private static final String sql_mapper = "com.clever.common.repository.OrderInfoDao";

    @Override
    public String getNamespace() {
        return sql_mapper;
    }

    @Override
    public int createOrder(OrderInfo orderInfo) {
        return 0;
    }
}
