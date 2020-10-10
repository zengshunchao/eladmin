package com.study.web.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CourseQueryDto {

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 上架时间
     */
    private String onlineTime;
    /**
     * 下架时间
     */
    private String offlineTime;

    /**
     * 状态
     */
    private Integer lineType;
}
