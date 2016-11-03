package com.clever.common.constant;

/**
 * Info: 用户流水类型
 * User: zhangxinglong@rui10.com
 * Date: 15/5/25
 * Time: 20:26
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public enum UserBillType {

    CONSUME(1,"消费"),

    REFUND(2,"退款");

    public int value;
    public String name;

    UserBillType(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
