package com.study.web.entity;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(Integer courseNumber) {
        this.courseNumber = courseNumber;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

}