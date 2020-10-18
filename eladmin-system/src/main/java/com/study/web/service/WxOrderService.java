package com.study.web.service;

import com.study.web.dto.OrderDto;
import com.study.web.dto.OrderInfoDto;
import com.study.web.entity.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 订单表(Order)表服务接口
 *
 * @author zengsc
 * @since 2020-10-09 16:26:20
 */
public interface WxOrderService {

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Order> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param orderInfoDto
     * @param request
     * @return
     * @throws Exception
     */
    Map<String, String> insertOrder(HttpServletRequest request, OrderInfoDto orderInfoDto) throws Exception;

    /**
     * 修改数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    void update(Order order);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据微信当前用户及订单状态查询所有订单
     *
     * @param order
     * @return
     */
    List<OrderDto> queryOrdersByStatus(Order order);

    /**
     * 订单id查询订单详情
     *
     * @param order
     * @return
     */
    OrderDto queryOrderByOrderId(Order order);

    /**
     * 根据商户订单号查询订单信息
     *
     * @param outTradeNo
     * @return
     */
    Order queryOrderByOutTradeNo(String outTradeNo);

    /**
     * 获取推广订单列表
     *
     * @param orderDto
     * @return
     */
    List<OrderDto> getShareOrderList(OrderDto orderDto);

}