package com.study.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zengsc
 * @since 2020-10-09 22:27:59
 */

/**
 * 订单-课程关联表(OrderCourseRel)实体类
 *
 * @author makejava
 * @since 2020-10-09 22:27:59
 */
@Data
public class OrderCourseRel implements Serializable {
    private static final long serialVersionUID = 490320219729654313L;
    /**
     * 表主键
     */
    private Long id;
    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 课程id
     */
    private Long courseId;
    /**
     * 课程数量
     */
    private Integer courseNumber;
    /**
     * 分享人id
     */
    private Long shareId;

}