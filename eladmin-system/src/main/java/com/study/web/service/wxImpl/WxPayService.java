package com.study.web.service.wxImpl;

import com.study.config.WxConfig;
import com.study.web.dao.WxUserDao;
import com.study.web.entity.Order;
import com.study.web.entity.WxUser;
import com.study.web.util.IpAddressUtil;
import com.study.web.wxPaySdk.PayHandleConfig;
import com.study.web.wxPaySdk.WXPay;
import com.study.web.wxPaySdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zsc
 * @date 2020/10/12 0012 22:26
 */
@Slf4j
@Service
public class WxPayService {

    @Autowired
    private WxUserDao wxUserDao;

    /**
     * 调用统一下单接口
     *
     * @param order   订单信息
     * @param request request
     * @return
     */
    public Map<String, String> unifiedOrder(HttpServletRequest request, Order order) {
        Map<String, String> map = null;
        // 获取openid
        WxUser user = wxUserDao.queryById(order.getWxUserId());
        if (user == null || StringUtils.isEmpty(user.getOpenId())) {
            return null;
        }
        // 微信统一下单接口参数
        Map<String, String> reqParams = new HashMap<>();
        //微信分配的小程序ID
        reqParams.put("appid", WxConfig.APPID);
        //微信支付分配的商户号
        reqParams.put("mch_id", WxConfig.MCHID);
        //随机字符串
        reqParams.put("nonce_str", WXPayUtil.generateNonceStr());
        //签名类型
        reqParams.put("sign_type", "MD5");
        //充值订单 商品描述
        reqParams.put("body", "课程购买订单-微信小程序");

        //商户订单号
        reqParams.put("out_trade_no", order.getOutTradeNo());
        //订单总金额，单位为分
        reqParams.put("total_fee", order.getMoney().multiply(BigDecimal.valueOf(100)).intValue() + "");
        String ipAddress = IpAddressUtil.getIpAddress(request);
        //终端IP
        reqParams.put("spbill_create_ip", ipAddress);
        //通知地址
        reqParams.put("notify_url", WxConfig.NOTIFY_URL);
        //交易类型
        reqParams.put("trade_type", WxConfig.TRADETYPE);
        //用户标识
        reqParams.put("openid", user.getOpenId());
        //签名
        String unifiedSign = "";
        try {
            unifiedSign = WXPayUtil.generateSignature(reqParams, WxConfig.KEY);
        } catch (Exception e) {
            log.error("统一下单签名失败", e);
            return null;
        }
        reqParams.put("sign", unifiedSign);
        PayHandleConfig config = null;
        WXPay wxPay = null;
        try {
            config = new PayHandleConfig();
            wxPay = new WXPay(config);
        } catch (Exception e) {
            log.error("创建统一下单接口实例失败", e);
            return null;
        }

        try {
            // 请求统一下单接口
            Map<String, String> resultMap = wxPay.unifiedOrder(reqParams);
            log.info("统一下单接口返回: " + resultMap);
            String return_code = (String) resultMap.get("return_code");
            String result_code = (String) resultMap.get("result_code");
            Long timeStamp = System.currentTimeMillis() / 1000;
            if ("SUCCESS".equals(return_code) && return_code.equals(result_code)) {
                map = new HashMap<>();
                // 获取随机字符串
                String nonceStr = WXPayUtil.generateNonceStr();
                map.put("nonceStr", nonceStr);
                String prepayid = resultMap.get("prepay_id");
                map.put("package", "prepay_id=" + prepayid);
                map.put("signType", "MD5");
                //这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                map.put("timeStamp", timeStamp + "");
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                map.put("appId", WxConfig.APPID);
                String sign = WXPayUtil.generateSignature(map, WxConfig.KEY);
                map.put("paySign", sign);
                log.info("生成的签名paySign : " + sign);
                return map;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("请求统一下单接口失败 {}", e);
            return null;
        }
    }

}
