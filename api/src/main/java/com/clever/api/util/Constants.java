package com.clever.api.util;

public class Constants {

    public static final String NO_INTERCEPTOR_PATH = ".*/((login)).*";	//不对匹配该值的访问路径拦截（正则）

    public static final String SESSION_USER = "sessionUser";

    public static final String OFF_ALLOW_EXCEL = "((doc)|(wps)|(vsd)|(docx))";	//只允许符合此规则的excel（正则）

    public static final String DOWNLOAD_HOME = System.getProperty("cleverm.push.dirs");

    public static final Integer VIDEO_ROLLMAIN = 1;//视频类别：待机广告视频

    public static final Integer VIDEO_DEMAND = 2;//视频类别：待机广告视频

    public static final int ACTION_ORDER                    = 1;//点餐加菜
    public static final int ACTION_WATER                    = 2;//添加茶水
    public static final int ACTION_PAPER                    = 3;//湿巾纸巾
    public static final int ACTION_CALL_PAY                 = 4;//呼叫结账
    public static final int ACTION_OTHER                    = 5;//其他服务
    public static final int ACTION_DEMO                     = 6;//DEMO引导
    public static final int ACTION_EVALUATE                 = 7;//服务评价
    public static final int ACTION_PAY                      = 8;//自助买单
    public static final int ACTION_DISCOUNT                 = 9;//优惠专区
    public static final int ACTION_DRIVING                  = 10;//代驾服务
    public static final int ACTION_PROMOTION                = 11;//本店推介
    public static final int ACTION_WEATHER                  = 12;//今日天气
    public static final int ACTION_NEWS                     = 13;//今日头条
    public static final int ACTION_SURROUND_ENTERTAINMENT   = 14;//周边玩乐
    public static final int ACTION_STORE_ONLINE             = 15;//在线商场
    public static final int ACTION_SURROUND_DISCOUNT        = 16;//周边优惠
    public static final int ACTION_VIDEO_FUN                = 17;//视频娱乐
    public static final int ACTION_MAGAZINE                 = 18;//电子杂志
    public static final int ACTION_GAME                     = 19;//手游试玩
    public static final int ACTION_SETTING                  = 20;//设置
    public static final int ACTION_ACTIVITY_DISCOUNT        = 21;//优惠专区活动
    public static final int ACTION_ACTIVITY_STORE           = 22;//本店推介活动
    public static final int ACTION_DEVICE_START             = 23;//开机时间
    public static final int ACTION_DEVICE_CLOSE             = 24;//关机时间
    public static final int ACTION_EVALUATE_SUBMIT          = 25;//评价提交
    public static final int ACTION_REWARD                   = 26;//打赏
    public static final int ACTION_VIDEO_PLAY               = 27;//视频界面
    public static final int ACTION_TIDY                     = 28;//收拾桌面
    public static final int ACTION_ADD_SOUP                 = 29;//火锅加汤
    public static final int ACTION_CHANGE_DISHWARE          = 30;//更换餐具
    public static final int ACTION_VIDEO_AD_CLICK           = 31;//视频广告详情
    public static final int ACTION_RESERVE                  = 100;//预留功能
    public static final int ACTION_DRIVING_URL              = 1000;//代驾跳转

}
