package com.study.web.wxPaySdk;

import com.study.config.WxConfig;

import java.io.InputStream;

/**
 * @author zsc
 * @date 2020/10/12 0012 23:01
 */
public class PayHandleConfig extends WXPayConfig {

    @Override
    String getAppID() {
        return WxConfig.APPID;
    }

    @Override
    String getMchID() {
        return WxConfig.MCHID;
    }

    @Override
    String getKey() {
        return WxConfig.KEY;
    }

    @Override
    InputStream getCertStream() {
        return null;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {
            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo("api.mch.weixin.qq.com", false);
            }
        };
    }
}
