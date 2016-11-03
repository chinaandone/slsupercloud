package com.clever.common.eum;

/**
 * INFO: ${todo}
 * User: zhaokai@mail.qianwang365.com
 * Date: 2014/9/17
 * Time: 14:17
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public enum ProductPublishEnum {
    UP(1, "已发布"),
    DOWN(2, "已下架"),
    DELETE(3, "已删除"),
    ERROR(-1, "错误");

    private String stringValue;
    private int intVlue;

    //构造函数必须为private的,防止意外调用
    private ProductPublishEnum(int intVlue, String stringValue) {

        this.stringValue = stringValue;
        this.intVlue = intVlue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getIntVlue() {
        return intVlue;
    }

    public static ProductPublishEnum getEnum(int intVlue) {
        for (ProductPublishEnum em : values()) {
            if (em.getIntVlue() == intVlue) {
                return em;
            }
        }
        return ERROR;
    }
}
