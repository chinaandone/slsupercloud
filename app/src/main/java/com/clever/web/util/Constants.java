package com.clever.web.util;

import com.clever.common.util.DateTime;
import com.clever.common.util.TypeConverter;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String REGISTER_PHONE_CODE = "register_phone_code";// 注册验证码

    public static final String REGISTER_EMAIL_CODE = "register_email_code";// 注册验证码

    public static final String LOGIN_PHONE_CODE = "login_phone_code";// 后台登陆验证码


    public static final String MEMBER_ORIGIN_PASSWORD = "123456";// 会员初始化密码

    public static final String MEMBER_LOGIN_SECURITY_CODE = "member_login_security_code";// 会员登陆验证码

    public static final String MEMBER_REGISTER_SECURITY_CODE = "member_register_security_code";// 会员登陆验证码

    public static final String MEMBER_FORGETPASSWORD_SECURITY_CODE = "member_forgetpassword_security_code";// 忘记密码验证码

    public static final String MEMBER_LOGIN_SECURITY_CODE_4SITE = "member_login_security_code_4site";// m站会员登陆验证码

    public static final String MEMBER_REGISTER_SECURITY_CODE_4SITE = "member_register_security_code_4site";// m站会员注册验证码

    public static final String ACCESS_TOKEN = "access_token";     // 登录access_token

    public static final String MEMBER_PHONECODE_4SITE = "member_phonecode_4site";// m站会员手机验证码

    public static final String UNBINDING_PHONE_CODE = "unbinding_phone_code";// 接粗手机绑定验证码

    public static final String MEMBER_PHONECODE_4APP = "member_phonecode_4app";// APP站会员手机验证码

    public static final String DOWNLOAD_HOME = System.getProperty("cleverm.push.dirs");

    public static final String DOWNLOAD_URL = System.getProperty("cleverm.push.http");

    public static final String UPLOAD_PICTRUE_PATH = "pictrue/";

    public static final String UPLOAD_VIDEO_PATH = "video/";

    public static final String UPLOAD_MATERIAL_PATH = "material/";

    public static final String NO_INTERCEPTOR_PATH = ".*/((login)).*";	//不对匹配该值的访问路径拦截（正则）

    public static final String SESSION_USER = "sessionUser";

    public static final String OFF_ALLOW_PICTRUE = "((jpg)|(png)|(bmp))";	//只允许符合此规则的图片（正则）

    public static final String OFF_ALLOW_VIDEO = "((rmvb)|(mp4)|(avi)|(wav))";	//只允许符合此规则的视频（正则）

    public static final String OFF_ALLOW_AUDIO = "((mp3)|(vmv)|(wav))";	//只允许符合此规则的音频（正则）

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
    public static final int ACTION_PRIZE_GOIN               = 32;//抽奖活动
    public static final int ACTION_PRIZE_CLICKDRAW          = 33;//点击抽奖
    public static final int ACTION_PRIZE_INPUTPHONE         = 34;//输入手机号
    public static final int ACTION_PRIZE_RECEIVEGOODS       = 35;//领取奖品
    public static final int ACTION_PRIZE_SERVER_LUCKYID     = 36;//云端奖券
    public static final int ACTION_RESERVE                  = 100;//预留功能
    public static final int ACTION_DRIVING_URL              = 1000;//代驾跳转

    public final static Map materialUseMap = new HashMap() {{
        put(0, "未定义");
        put(1, "apk升级文件");
        put(2, "微信支付二维码");
        put(3, "支付宝支付二维码");
        put(100, "升级固件(升级专用)");
    }};//素材作用列表

    public final static Map<Integer,String> mediaFormMap = new HashMap<Integer,String>() {{
        put(1, "屏幕广告");
        put(2, "板块冠名");
        put(3, "主题定制");
        put(4, "互动游戏");
        put(5, "桌贴广告");
    }};//推广媒体形式列表

    public final static Map<Integer,String> adLengthMap = new HashMap<Integer,String>() {{
        put(1, "15秒");
        put(2, "30秒");
    }};//推广广告长度列表

    public final static Map<Integer,String> playFreqMap = new HashMap<Integer,String>() {{
        put(1, "120次");
        put(2, "240次");
        put(100, "全天");
    }};//推广播放频次列表
}
