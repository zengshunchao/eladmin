package com.study.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zengsc
 * @since 2020-10-09 16:10:38
 */

/**
 * 分销员表(Distribution)实体类
 *
 * @author makejava
 * @since 2020-10-09 16:10:38
 */
@Data
public class DistributionDto extends PageInfo implements Serializable {
    private static final long serialVersionUID = 425981076038169520L;
    /**
     * 表主键
     */
    private Long id;
    /**
     * 用户id
     */
    private Long wxUserId;
    /**
     * 上级id
     */
    private Long parentId;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 头像路径
     */
    private String avatarUrl;
    /**
     *  真实姓名
     */
    private String realName;
}