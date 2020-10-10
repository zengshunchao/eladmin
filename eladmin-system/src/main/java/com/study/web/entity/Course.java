package com.study.web.entity;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zengsc
 * @since 2020-09-03 15:47:41
 */

@Data
public class Course implements Serializable {
    private static final long serialVersionUID = 814682909859821954L;
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
     *  活动区域
     */
    private String actionArea;
    /**
     * 上架时间
     */
    private Date onlineTime;
    /**
     * 下架时间
     */
    private Date offlineTime;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;

    /**
     *  经度
     */
    private String longitude;

    /**
     *  纬度
     */
    private String latitude;

}