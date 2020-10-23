package com.study.web.util;

/**
 * @author zsc
 * @date 2020/10/17 0017 17:52
 */
public class Constants {
    /**
     * 未上架
     */
    public static final int NO_ONLINE = 2;
    /**
     * 已上架
     */
    public static final int YES_ONLINE = 1;

    /**
     * 已下架
     */
    public static final int YES_OFFLINE = 0;

    /**
     * 图片类型 1-封面 2-详情图
     */
    public static final int PICTURE_TYPE_COVER = 1;
    public static final int PICTURE_TYPE_INFO = 2;

    /**
     * 微信登录接口
     */
    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 小程序appId
     */
    public static final String WX_APPID = "wx28a53540e99155e8";

    /**
     * 小程序 appSecret
     */
    public static final String WX_SECRET = "fdefc1ca8e09b98fb7fb7b3a96ed98ef";

    /**
     * 授权类型，此处只需填写 authorization_code
     */
    public static final String GRANT_TYPE = "authorization_code";

    /**
     * 订单状态-待支付
     */
    public static final int UNPAID = 1;

    /**
     * 订单状态-待使用
     */
    public static final int UNUSED = 2;

    /**
     * 订单状态-已完成
     */
    public static final int FINISHED = 3;

    /**
     * 订单状态-已取消
     */
    public static final int CANCELED = 4;

    /**
     * 佣金锁定状态 0 锁定 1 解锁
     */
    public static final int COMMISSION_LOCK_STATUS_YES = 0;
    public static final int COMMISSION_LOCK_STATUS_NO = 1;

    /**
     * 佣金百分比-字典表key
     */
    public static final String COMMISSION_DICT_KEY = "commission_percent";

    /**
     * 佣金解锁时间
     */
    public static final String COMMISSION_DICT_LOCK_TIME_KEY = "commission_lock_time";

    /**
     * 钱包流水定义 1-收入 2-支出 3-其他
     */
    public static final int WALLET_WATER_INCOME = 1;
    public static final int WALLET_WATER_EXPEND = 2;
    public static final int WALLET_WATER_OTHER = 3;

    /**
     *  订单自动核销时间
     */
    public static final String AUTO_CHECK_TIME_KEY = "auto_check_time";

    /**
     * 自动取消未支付订单
     */
    public static final String AUTO_CANCEL_ORDER_TIME = "auto_cancel_order_time";
}
