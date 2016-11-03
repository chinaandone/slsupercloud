package com.clever.common.constant;

/**
 * Info: 用户流水类型
 * User: zhangxinglong@rui10.com
 * Date: 15/5/25
 * Time: 20:26
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public enum MerchantActivityType {

    ACTIVITY_VOUCHER(1, "抵食券");

    public int id;
    public String name;

    MerchantActivityType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
