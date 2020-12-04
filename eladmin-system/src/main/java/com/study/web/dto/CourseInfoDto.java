package com.study.web.dto;

import com.study.web.entity.Picture;
import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
public class CourseInfoDto  extends PageInfo {

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
     *  上下架状态 0-已下架 1-已上架 2-未上架
     */
    @Transient
    private Integer lineType;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;

    /**
     *  封面图
     */
    @Transient
    private List<Picture> coverPicture;

    /**
     *  详情图
     */
    @Transient
    private List<Picture> courseInfoPicture;

    /**
     *  课程状态
     */
    @Transient
    private String lineTypeString;

    /**
     *  经度
     */
    private String longitude;

    /**
     *  纬度
     */
    private String latitude;

    /**
     *  排序编号
     */
    private Integer sortNumber;

    /**
     *  失效时间
     */
    private Date expireTime;
}

