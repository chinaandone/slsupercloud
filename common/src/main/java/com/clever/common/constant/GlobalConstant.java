package com.clever.common.constant;

/**
 * Info: 全局Constant
 * User: zhaoky
 * Data: 2015-08-27
 * Time: 15:31
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class GlobalConstant {

    /**
     * 图片格式
     */
    public static final String[] imgTypes = new String[]{"BMP", "PNG", "GIF","JPG","JPEG"};

    /**
     * 上传图片所允许的最大字节数
     */
    public static final Integer MAX_SIZE = 1024 * 1024*2;

    /**
     * 7牛云存储分配的token参数
     */
    public static final String IMG_WEB_DOMAIN = "http://shop-picture.qiniudn.com";

    /**
     * 7牛云存储分配的token参数
     */
    public static final String FW_IMG_WEB_DOMAIN  = "http://7xlawd.com1.z0.glb.clouddn.com/";


    // 请确保该bucket已经存在
    public static final String QINIU_Config_bucketName = "shop-picture";

    /**
     * 7牛云存储分配的token参数
     */
    public static final String QINIU_Config_ACCESS_KEY = "-HB6tjGqSqOBZ8sLatJo0MgaXRn6SEIFIFTuCQPo";
    public static final String QINIU_Config_SECRET_KEY = "2iOSIXKrYbdNbmB0ZpB3OcUIIDaN_iIaTyT5myDS";

    //微信APP支付appid
    public static final String APPID = "wx1ba731267ef6c797";
    //微信APP支付商户ID
    public static final String MCHID = "1236821801";

    //微信wap支付appid
    public static final String WAP_APPID = "wx0b188b43187e8629";
    //微信wap支付商户ID
    public static final String WAP_MCHID = "1229105402";

    //商户号对应的密钥
    public static final String PARTNERKEY = "b089d2d3eee1bfcdede4573020dce3cb";

    public static final String ROLL_SUPERADMIN = "SUPERADMIN";

    public static final String ROLL_CLIENTUSER = "CLIENTUSER";

    public static final String ROLL_BRANDUSER = "BRANDUSER";

    public static final String ROLL_JF = "JF";//服务修改桌子对应手机号的权限

    public static final Integer NOTMUYERESOURCE = 0;//非木爷的,或木爷推送的,已经不是木爷资源的

    public static final Integer ISMUYERESOURCE = 1;//木爷的,或木爷资源的

    public static final Integer VIDEO_ROLLMAIN = 1;//视频类别：待机广告视频

    public static final Integer VIDEO_DEMAND = 2;//视频类别：待机广告视频

}
