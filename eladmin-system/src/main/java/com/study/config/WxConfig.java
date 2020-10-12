package com.study.config;

/**
 * @author zsc
 * @date 2020/10/12 0012 22:11
 */
public final class WxConfig {

    /**
     * 小程序appid
     */
    public final static String APPID = "wxe86f60xxxxxxx";
    /**
     * 商户ID
     */
    public final static String MCHID = "15365xxxxx";
    /**
     * 跟微信支付约定的密钥
     */
    public final static String KEY = "";
    /**
     * 回调地址
     */
    public final static String NOTIFY_URL = "/admin/wxnotify";
    /**
     * 统一下单地址
     */
    public final static String PAYURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 支付方式
     */
    public final static String TRADETYPE = "JSAPI";
}
