package com.clever.common.eum;

/**
 * INFO: 数据是否被过滤或限制类想
 * User: zhaokai@mail.qianwang365.com
 * Date: 2014/9/17
 * Time: 14:17
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public enum WebPageLimitTypeEnum {
    NO_LIMIT(0, "没有限制"),
    LIMIT(1, "黑名单黑名单"),
    LIMIT_PASS(2, "商品信息部分下架，不影响前端");

    private String stringValue;
    private int intVlue;

    //构造函数必须为private的,防止意外调用
    private WebPageLimitTypeEnum(int intVlue, String stringValue) {

        this.stringValue = stringValue;
        this.intVlue = intVlue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getIntVlue() {
        return intVlue;
    }

    public static WebPageLimitTypeEnum getEnum(int intVlue) {

        for (WebPageLimitTypeEnum em : values()) {
            if (em.getIntVlue() == intVlue) {
                return em;
            }
        }
        return null;
    }
}
