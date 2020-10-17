package com.study.web.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zengsc
 * @since 2020-10-09 15:57:39
 */

/**
 * 佣金表(Commission)实体类
 *
 * @author makejava
 * @since 2020-10-09 15:57:39
 */
@Data
public class Commission implements Serializable {
    private static final long serialVersionUID = -68028794021822713L;
    /**
     * 表主键
     */
    private Long id;
    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 佣金金额
     */
    private BigDecimal commissionMoney;
    /**
     * 佣金获得者id
     */
    private Long wxUserId;
    /**
     * 分享人id(佣金来源)
     */
    private Long shareId;
    /**
     * 解锁时间
     */
    private Date lockTime;
    /**
     * 锁定状态0锁定 1解锁
     */
    private Integer lockStatus;

}