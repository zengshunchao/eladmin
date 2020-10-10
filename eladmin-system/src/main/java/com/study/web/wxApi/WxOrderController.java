package com.study.web.wxApi;


import com.alibaba.fastjson.JSONObject;
import com.study.web.entity.Share;
import com.study.web.util.ResponseCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  订单管理
 */
@Slf4j
@RestController
@RequestMapping("/wxApi/order")
public class WxOrderController {


    @ApiOperation("微信-获取订单列表")
    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
    public Object updateShareId(HttpServletRequest request, HttpServletResponse response, Share share) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", ResponseCode.SUCCESS.getCode());
            jsonObject.put("msg", ResponseCode.SUCCESS.getMsg());
        } catch (Exception e) {
            jsonObject.put("code", ResponseCode.FAIL.getCode());
            jsonObject.put("msg", ResponseCode.FAIL.getMsg());
            log.error("updateShareId fail {}", e);
            return jsonObject;
        }
        return jsonObject;
    }
}
