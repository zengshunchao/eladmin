package com.study.web.wxApi;


import com.alibaba.fastjson.JSONObject;
import com.study.web.dto.OrderDto;
import com.study.web.dto.OrderInfoDto;
import com.study.web.entity.Order;
import com.study.web.entity.Share;
import com.study.web.service.WxOrderService;
import com.study.web.util.ResponseCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单管理
 */
@Slf4j
@RestController
@RequestMapping("/wxApi/order")
public class WxOrderController {

    @Autowired
    private WxOrderService wxOrderService;

    @ApiOperation("微信-添加订单")
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public Object createOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody OrderInfoDto order) {
        JSONObject jsonObject = new JSONObject();
        try {
            wxOrderService.insertOrder(order);
            jsonObject.put("code", ResponseCode.SUCCESS.getCode());
            jsonObject.put("msg", ResponseCode.SUCCESS.getMsg());
        } catch (Exception e) {
            jsonObject.put("code", ResponseCode.FAIL.getCode());
            jsonObject.put("msg", ResponseCode.FAIL.getMsg());
            if ("订单金额有误".equals(e.getMessage())) {
                jsonObject.put("msg", e.getMessage());
            }
            log.error("createOrder fail {}", e);
            return jsonObject;
        }
        return jsonObject;
    }

    @ApiOperation("微信-获取订单列表")
    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
    public Object getOrderList(HttpServletRequest request, HttpServletResponse response, @RequestBody Order order) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<OrderDto> orderList = wxOrderService.queryOrdersByStatus(order);
            jsonObject.put("code", ResponseCode.SUCCESS.getCode());
            jsonObject.put("msg", ResponseCode.SUCCESS.getMsg());
            jsonObject.put("data", orderList);
        } catch (Exception e) {
            jsonObject.put("code", ResponseCode.FAIL.getCode());
            jsonObject.put("msg", ResponseCode.FAIL.getMsg());
            log.error("getOrderList fail {}", e);
            return jsonObject;
        }
        return jsonObject;
    }

    @ApiOperation("微信-获取订单详情")
    @RequestMapping(value = "/getOrderInfo", method = RequestMethod.POST)
    public Object getOrderInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody Order order) {
        JSONObject jsonObject = new JSONObject();
        try {
            OrderDto orderDto = wxOrderService.queryOrderByOrderId(order);
            jsonObject.put("code", ResponseCode.SUCCESS.getCode());
            jsonObject.put("msg", ResponseCode.SUCCESS.getMsg());
            jsonObject.put("data", orderDto);
        } catch (Exception e) {
            jsonObject.put("code", ResponseCode.FAIL.getCode());
            jsonObject.put("msg", ResponseCode.FAIL.getMsg());
            log.error("getOrderInfo fail {}", e);
            return jsonObject;
        }
        return jsonObject;
    }
}
