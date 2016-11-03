package com.clever.common.eum;

/**
 * INFO: ${todo}
 * User: zhaokai@mail.qianwang365.com
 * Date: 2014/9/17
 * Time: 14:17
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public enum ProductAuditEnum {
    INIT(1, "待审核"),
    PASS(2, "审核通过"),
    NO_PASS(3, "审核不通过"),
    PASS_LIMIT(4, "审核通过,但建议人工审核"),
    ERROR(-1, "错误");

    private String stringValue;
    private int intVlue;

    //构造函数必须为private的,防止意外调用
    private ProductAuditEnum(int intVlue, String stringValue) {

        this.stringValue = stringValue;
        this.intVlue = intVlue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getIntVlue() {
        return intVlue;
    }

    public static ProductAuditEnum getEnum(int intVlue) {

        for (ProductAuditEnum em : values()) {
            if (em.getIntVlue() == intVlue) {
                return em;
            }
        }
        return ERROR;
    }
}
