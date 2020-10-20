package com.study.web.wxApi;


import com.study.config.WxConfig;
import com.study.utils.OrderCodeUtil;
import com.study.web.dto.OrderCourseRelDto;
import com.study.web.dto.OrderDto;
import com.study.web.dto.OrderInfoDto;
import com.study.web.dto.ResultValue;
import com.study.web.entity.Order;
import com.study.web.service.WxOrderService;
import com.study.web.util.Constants;
import com.study.web.util.ResponseCode;
import com.study.web.util.TimeUtil;
import com.study.web.util.XmlUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单管理
 */
@Slf4j
@RestController
@RequestMapping("/wxApi/order")
public class WxOrderController extends JsonResultController {

    @Autowired
    private WxOrderService wxOrderService;

    @ApiOperation("微信-统一下单")
    @RequestMapping(value = "/unifiedOrder", method = RequestMethod.POST)
    public ResultValue unifiedOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody OrderInfoDto order) {
        try {
            Map<String, String> resultMap = wxOrderService.insertOrder(request, order);
            // 返回为空则下单失败
            if (resultMap == null) {
                return successResult(ResponseCode.UNIFIEDORDER.getCode(), ResponseCode.UNIFIEDORDER.getMsg());
            }
            return jsonResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), resultMap);
        } catch (Exception e) {
            log.error("unifiedOrder fail {}", e);
            if ("订单金额有误".equals(e.getMessage())) {
                return errorResult(ResponseCode.FAIL.getCode(), e.getMessage());
            }
            return errorResult(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }

    @ApiOperation("微信-获取订单列表")
    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
    public ResultValue getOrderList(HttpServletRequest request, HttpServletResponse response, @RequestBody Order order) {
        try {
            List<OrderDto> orderList = wxOrderService.queryOrdersByStatus(order);
            return jsonResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), orderList);
        } catch (Exception e) {
            log.error("getOrderList fail {}", e);
            return errorResult(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }


    @ApiOperation("微信-获取推广订单列表")
    @RequestMapping(value = "/getShareOrderList", method = RequestMethod.POST)
    public ResultValue getShareOrderList(HttpServletRequest request, HttpServletResponse response, @RequestBody OrderDto orderDto) {
        try {
            setPageInfo(orderDto);
            List<OrderDto> orderList = wxOrderService.getShareOrderList(orderDto);
            return jsonResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), orderList);
        } catch (Exception e) {
            log.error("getShareOrderList fail {}", e);
            return errorResult(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }


    @ApiOperation("微信-获取订单详情")
    @RequestMapping(value = "/getOrderInfo", method = RequestMethod.POST)
    public ResultValue getOrderInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody Order order) {
        try {
            OrderDto orderDto = wxOrderService.queryOrderByOrderId(order);
            return jsonResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), orderDto);
        } catch (Exception e) {
            log.error("getOrderInfo fail {}", e);
            return errorResult(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }

    /**
     * 支付回调
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/wxNotify", method = RequestMethod.POST)
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            //sb为微信返回的xml
            String notifyXml = sb.toString();
            String resXml = "";

            Map<String, String> map = XmlUtil.getMapFromXML(notifyXml);
            log.info("支付回调数据:\n" + map);
            String returnCode = (String) map.get("return_code");
            if ("SUCCESS".equalsIgnoreCase(returnCode)) {
                String resultCode = map.get("result_code");
                if ("SUCCESS".equalsIgnoreCase(resultCode)) {
                    // 回调进行签名验证
                    Map<String, String> validParams = XmlUtil.paramFilter(map);
                    String linkString = XmlUtil.createLinkString(validParams);
                    String sign = (String) map.get("sign");
                    if (XmlUtil.verify(linkString, sign, WxConfig.KEY, "utf-8")) {
                        // 商户订单号
                        String out_trade_no = map.get("out_trade_no");
                        Order order = wxOrderService.queryOrderByOutTradeNo(out_trade_no);
                        if (order.getStatus() == Constants.UNPAID) {
                            // 验证回调金额与本地订单金额
                            String total_fee = map.get("total_fee");
                            BigDecimal totalMoney = new BigDecimal(total_fee);
                            // 金额相等则修改订单状态
                            if (order.getMoney().multiply(BigDecimal.valueOf(100)).compareTo(totalMoney) == 0) {
                                // 获取支付完成时间
                                String time_end = map.get("time_end");
                                Date payTime = TimeUtil.stringToDate(time_end, "yyyyMMddHHmmss");
                                Order updateOrder = new Order();
                                updateOrder.setId(order.getId());
                                // 状态修改为待使用
                                updateOrder.setStatus(Constants.UNUSED);
                                updateOrder.setPayTime(payTime);
                                updateOrder.setCheckCode(OrderCodeUtil.getRandomStringNum(12));
                                wxOrderService.updateOrderAndSaveCommission(updateOrder);
                            }
                        }
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                    } else {
                        resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                                + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    }
                } else {
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                }
            } else {
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("wxNotify fail {}", e);
        }
    }

    @ApiOperation("微信-支付成功主动回调修改订单状态")
    @RequestMapping(value = "/updateOrderStatus", method = RequestMethod.POST)
    public ResultValue updateOrderStatus(HttpServletRequest request, HttpServletResponse response, @RequestBody Order order) {
        try {
            if (StringUtils.isEmpty(order.getOutTradeNo())) {
                return errorResult(ResponseCode.BADREQUESTPARAM.getCode(), ResponseCode.BADREQUESTPARAM.getMsg());
            }
            Order orderDto = wxOrderService.queryOrderByOutTradeNo(order.getOutTradeNo());
            if (orderDto == null) {
                return errorResult(ResponseCode.NODATA.getCode(), ResponseCode.NODATA.getMsg());
            }
            // 如果订单状态为待支付才修改状态
            if (orderDto.getStatus() == Constants.UNPAID) {
                Date payTime = new Date();
                Order updateOrder = new Order();
                updateOrder.setId(orderDto.getId());
                // 状态修改为待使用
                updateOrder.setStatus(Constants.UNUSED);
                updateOrder.setPayTime(payTime);
                updateOrder.setCheckCode(OrderCodeUtil.getRandomStringNum(12));
                wxOrderService.updateOrderAndSaveCommission(updateOrder);
            }
            return successResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error("updateOrderStatus fail {}", e);
            return errorResult(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }

    @ApiOperation("微信-根据用户和课程查询订单数量")
    @RequestMapping(value = "/countOrderByCourseAndUser", method = RequestMethod.GET)
    public ResultValue countOrderByCourseAndUser(HttpServletRequest request, HttpServletResponse response, Long wxUserId,Long courseId) {
        try {
            if(null == wxUserId || null == courseId){
                return errorResult(ResponseCode.BADREQUESTPARAM.getCode(), ResponseCode.BADREQUESTPARAM.getMsg());
            }
            Integer count = wxOrderService.countOrderByCourseAndUser(wxUserId,courseId);
            return jsonResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), count);
        } catch (Exception e) {
            log.error("getOrderInfo fail {}", e);
            return errorResult(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }
}

