package com.study.web.dto;

import com.study.web.entity.OrderCourseRel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单详情
 */
@Data
public class OrderInfoDto {

    /**
     * 订单编号
     */
    private String orderNumber;
    /**
     * 购买人id
     */
    private Long wxUserId;
    /**
     * 订单金额
     */
    private BigDecimal money;
    /**
     * 订单状态(1-待支付 2-待使用 3-已完成 4-已取消)
     */
    private Long status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 系统自动核销时间
     */
    private Date autoCheckTime;
    /**
     * 订单完成时间
     */
    private Date finishTime;
    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 订单课程数量
     */
    private List<OrderCourseRel> courseNums;
}
