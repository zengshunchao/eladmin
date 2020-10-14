package com.study.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单详情
 */
@Data
public class OrderDto {

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
    private Integer status;

    /**
     * 订单用户姓名
     */
    private String orderUserName;

    /**
     * 订单联系电话
     */
    private String orderPhone;
    /**
     * 备注
     */
    private String remark;
    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;
    /**
     * 系统自动核销时间
     */
    private Date autoCheckTime;
    /**
     * 订单完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;
    /**
     * 订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 课程列表
     */
    @Transient
    private List<OrderCourseRelDto> courseList;

    @Transient
    private String realName;

    @Transient
    private String phone;
}
