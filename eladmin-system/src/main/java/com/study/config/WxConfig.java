package com.study.config;

/**
 * @author zsc
 * @date 2020/10/12 0012 22:11
 */
public final class WxConfig {

    /**
     * 小程序appid
     */
    public final static String APPID = "wx28a53540e99155e8";
    /**
     * 商户ID
     */
    public final static String MCHID = "1603403622";
    /**
     * 跟微信支付约定的密钥
     */
    public final static String KEY = "f47a6fd6fc5032b6d1668C7898EC5546";
    /**
     * 回调地址
     */
    public final static String NOTIFY_URL = "https:tomuchlove:8000/wxApi/order/wxNotify";
    /**
     * 统一下单地址
     */
    public final static String PAYURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 支付方式
     */
    public final static String TRADETYPE = "JSAPI";
}
