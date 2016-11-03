package com.clever.common.constant;

/**
 * Created by zhuzhongbiao on 14-9-23.
 */
public class ResponseCodeConstant {

     //1000 :  访问正常  ,
    // 1001：当前接口弃用需要客户端强制升级 ,
    // 1002：维护中 (提示消息放入errorMsg 字段中),
    // 1003：当前访问的接口有新版本可使用,
    // 1004： jsession失效 ,
    // 1005：接口异常或错误

    public static final int SUCCESS = 1000;

    public static final int FORCED_UPGRADE = 1001;

    public static final int MAINTAIN = 1002;

    public static final int NEW_RELEASE= 1003;

    public static final int SESSION_INVAILD = 1004;

    public static final int INTERFACE_ERROR = 1005;




}
