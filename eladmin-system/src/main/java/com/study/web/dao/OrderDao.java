package com.study.web.dao;

import com.study.web.dto.BackGroundOrderInfoDto;
import com.study.web.dto.BackGroundOrderQueryDto;
import com.study.web.dto.OrderDto;
import com.study.web.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单表(Order)表数据库访问层
 *
 * @author zengsc
 * @since 2020-10-09 16:26:20
 */
@Repository
public interface OrderDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderDto queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Order> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param order 实例对象
     * @return 对象列表
     */
    List<Order> queryAll(Order order);

    /**
     * 根据订单状态查询所有订单
     *
     * @param order
     * @return
     */
    List<OrderDto> queryByStatus(Order order);

    /**
     * 新增数据
     *
     * @param order 实例对象
     * @return 影响行数
     */
    int insert(Order order);

    /**
     * 修改数据
     *
     * @param order 实例对象
     * @return 影响行数
     */
    int update(Order order);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据商户订单号查询订单
     *
     * @param outTradeNo
     * @return
     */
    Order queryOrderByOutTradeNo(@Param("outTradeNo") String outTradeNo);

    /**
     * 后台-订单查询
     *
     * @param order
     * @param startNum
     * @param pageSize
     * @return
     */
    List<BackGroundOrderInfoDto> queryOrderLimit(BackGroundOrderQueryDto order, @Param("startNum") int startNum, @Param("pageSize") int pageSize);

    /**
     * 统计订单
     *
     * @param order
     * @return
     */
    int totalOrder(BackGroundOrderQueryDto order);


    /**
     * 根据订单id查询订单详情
     *
     * @param id
     * @return
     */
    BackGroundOrderInfoDto queryOrderInfoById(Long id);

    /**
     * 推广订单
     *
     * @param orderDto
     * @return
     */
    List<OrderDto> getShareOrderList(OrderDto orderDto);
}