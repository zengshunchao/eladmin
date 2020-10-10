package com.study.web.dto;

import com.study.web.entity.Picture;
import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

/**
 * 订单关联课程详细信息
 */
@Data
public class OrderCourseRelDto {

    /**
     * 课程表主键
     */
    private Long id;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 课程价格
     */
    private Double courseMoney;
    /**
     * 佣金
     */
    private Double courseCommission;
    /**
     * 机构名称
     */
    private String branchName;
    /**
     * 机构地址
     */
    private String branchAddress;

    /**
     * 活动区域
     */
    private String actionArea;

    /**
     * 封面图
     */
    @Transient
    private List<Picture> coverPicture;

    /**
     * 详情图
     */
    @Transient
    private List<Picture> courseInfoPicture;

    /**
     * 课程数量
     */
    private Integer courseNumber;

    /**
     * 分享人昵称
     */
    @Transient
    private String shareName;
}
