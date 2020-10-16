package com.study.web.dto;

import com.study.web.entity.Order;
import lombok.Data;

import javax.persistence.Transient;

/**
 * @author zsc
 * @date 2020/10/15 0015 21:20
 */
@Data
public class BackGroundOrderInfoDto extends Order {

    /**
     * 分销员名称
     */
    private String nickName;

    /**
     * 课程名称
     */
    @Transient
    private String courseName;

    /**
     * 课程购买数量
     */
    @Transient
    private Integer courseNumber;

    /**
     * 分享人昵称
     */
    @Transient
    private String shareName;

    @Transient
    private String coursePicturePath;
}
