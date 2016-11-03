package com.clever.common.eum;

/**
 * INFO: 标签类型
 * User: zhaokai@mail.qianwang365.com
 * Date: 2014/9/17
 * Time: 14:17
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public enum LabelTypeEnum {
    SYS(0, "系统内置"),
    USER(1, "用户行为");

    private String stringValue;
    private int intVlue;

    //构造函数必须为private的,防止意外调用
    private LabelTypeEnum(int intVlue, String stringValue) {

        this.stringValue = stringValue;
        this.intVlue = intVlue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getIntVlue() {
        return intVlue;
    }

    public static LabelTypeEnum getEnum(int intVlue) {
        if (SYS.getIntVlue() == intVlue) {
            return SYS;
        } else if (USER.getIntVlue() == intVlue) {
            return USER;

        }
        return null;
    }
}
