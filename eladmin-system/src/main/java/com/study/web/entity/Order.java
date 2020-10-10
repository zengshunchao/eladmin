package com.study.web.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zengsc
 * @since 2020-10-09 16:26:20
 */

/**
 * 订单表(Order)实体类
 *
 * @author makejava
 * @since 2020-10-09 16:26:20
 */
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 239972185784539107L;
    /**
     * 表主键
     */
    private Long id;
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
     * 创建时间
     */
    private Date createTime;

}