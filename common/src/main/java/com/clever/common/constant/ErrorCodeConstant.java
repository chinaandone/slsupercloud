package com.clever.common.constant;

/**
 * Created by zhuzhongbiao on 14-9-23.
 */
public class ErrorCodeConstant {
    /**
     * 成功默认值
     */
    public static final int SUCCESS_DEFAULT = 0;
    /**
     * 失败默认值
     */
    public static final int FAILED_DEFAULT = 1000;
    /**
     * 库存不足
     */
    public static final int GOODS_STOCK_NOT_ENOUGH = 1001;
    /**
     * 该商品已经收藏过
     */
    public static final int GOODS_HAS_COLLECT = 1002;
    /**
     * 该商品不存在
     */
    public static final int GOODS_NO_EXIST = 1003;
    /**
     * 此商品已经被举报过，正在处理中
     */
    public static final int GOODS_PRODUCT_HAS_REPORT = 1004;

    /**
     * 商家被锁定
     */
    public static final int USER_BLACK = 1005;

    /**
     * 调用接口服务IP错误
     */
    public static final int ACCESS_FORBIDDEN_MSG = 1006;

}
