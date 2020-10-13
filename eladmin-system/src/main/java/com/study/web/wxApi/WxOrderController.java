package com.study.web.wxApi;


import com.alibaba.fastjson.JSONObject;
import com.study.web.dto.OrderDto;
import com.study.web.dto.OrderInfoDto;
import com.study.web.dto.ResultValue;
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
public class WxOrderController extends JsonResultController{

    @Autowired
    private WxOrderService wxOrderService;

    @ApiOperation("微信-添加订单")
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public ResultValue createOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody OrderInfoDto order) {
        try {
            wxOrderService.insertOrder(order);
            return successResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error("createOrder fail {}", e);
            if ("订单金额有误".equals(e.getMessage())) {
                return errorResult( ResponseCode.FAIL.getCode(), e.getMessage());
            }
            return errorResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }

    @ApiOperation("微信-获取订单列表")
    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
    public ResultValue getOrderList(HttpServletRequest request, HttpServletResponse response, @RequestBody Order order) {
        try {
            List<OrderDto> orderList = wxOrderService.queryOrdersByStatus(order);
            return jsonResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(),orderList);
        } catch (Exception e) {
            log.error("getOrderList fail {}", e);
            return errorResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }

    @ApiOperation("微信-获取订单详情")
    @RequestMapping(value = "/getOrderInfo", method = RequestMethod.POST)
    public ResultValue getOrderInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody Order order) {
        try {
            OrderDto orderDto = wxOrderService.queryOrderByOrderId(order);
            return jsonResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(),orderDto);
        } catch (Exception e) {
            log.error("getOrderInfo fail {}", e);
            return errorResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }
}
