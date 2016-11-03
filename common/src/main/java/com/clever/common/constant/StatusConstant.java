package com.clever.common.constant;

/**
 * Info: 状态常量
 * User: zhangxinglong@rui10.com
 * Date: 5/3/15
 * Time: 11:24
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */
public class StatusConstant {

    /**
     * 启用
     */
    public static final int STATUS_ON = 1;

    /**
     * 停用
     */
    public static final int STATUS_OFF = 2;

    /**
     * 待审
     */
    public static final int STATUS_PENDING = 3;

    /**
     * 送红包触发类型（注册）
     */
    public static final int REGISTER = 1;

    /**
     * 送红包触发类型（消费后）
     */
    public static final int CONSUME = 2;

    /**
     * 订单是否验证状态0未验证
     */
    public static final int UN_VERIFY = 0;

    /**
     * 订单是否验证状态1已验证
     */
    public static final int VERIFY_FINISH = 1;

    /**
     * 商户市场类型 10已安装硬件
     */
    public static final int MARKET_TYPE_ONE = 10;

    /**
     * 商户市场类型 20签合同未安装
     */
    public static final int MARKET_TYPE_TWO = 20;

    /**
     * 商户市场类型 30未安装未签合同
     */
    public static final int MARKET_TYPE_THREE = 30;

    /**
     * 商户市场类型 40导入
     */
    public static final int MARKET_TYPE_FOUR = 40;

   // 1 未支付
   public static final int NON_PAY_STATUS = 1;
   // 2 已支付
   public static final int PAY_SUCCESS = 2;
    //3 已退款
    public static final int REFUND = 3;
   // 4 待受理（运营）
   public static final int SUSPENSE  = 4;

    //      5 已受理（运营） 待受理（财务）
    public static final int UNPROCESSED = 5;
    //        6 处理中（微信或支付宝)
    public static final int HANDLING = 6;
     //       7 退款成功（微信或支付宝）
     public static final int REFUND_SUCCESS = 7;
    //       8 退款失败（微信或支付宝）
    public static final int REFUND_FAIL = 8;

    /**
     * 全部、省、市、区、商圈、商户
     */
    //全部
    public static final int LEV_0 = 0;
    //省
    public static final int LEV_1 = 1;
    //市
    public static final int LEV_2 = 2;
    //区
    public static final int LEV_3 = 3;
    //商圈
    public static final int LEV_4 = 4;
    //商户
    public static final int LEV_5 = 5;

    /**
     * 扫位硬件状态 0 正常
     */
    public static final int SHOP_NODE_STATUS_NORMAL = 0;
}
