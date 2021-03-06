package com.study.web.service;

import com.study.web.dto.BackGroundOrderInfoDto;
import com.study.web.dto.BackGroundOrderQueryDto;
import com.study.web.dto.ExportOrderInfoDto;
import com.study.web.entity.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author zsc
 * @date 2020/10/15 0015 22:00
 */
public interface BackGroundOrderService {

    /**
     * 查询订单
     *
     * @param orderQueryDto
     * @param pageable
     * @return
     */
    List<BackGroundOrderInfoDto> queryOrderByLimit(BackGroundOrderQueryDto orderQueryDto, Pageable pageable);

    /**
     * 统计订单
     *
     * @param orderQueryDto
     * @return
     */
    int totalOrder(BackGroundOrderQueryDto orderQueryDto);

    /**
     * 根据订单id查询订单
     *
     * @param id
     * @return
     */
    BackGroundOrderInfoDto queryOrderById(Long id);

    /**
     *  订单核销
     * @param order
     */
    void updateCheckTimeAndStatus(Order order);

    /**
     *  导出订单
     * @return
     */
    List<ExportOrderInfoDto> exportOrderExcel();
}
