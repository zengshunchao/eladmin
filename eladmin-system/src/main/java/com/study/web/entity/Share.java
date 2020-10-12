package com.study.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zengsc
 * @since 2020-10-09 16:35:20
 */

/**
 * 分享关系表(Share)实体类
 *
 * @author makejava
 * @since 2020-10-09 16:35:20
 */
@Data
public class Share implements Serializable {
    private static final long serialVersionUID = -67621330137489654L;
    /**
     * 表主键
     */
    private Long id;
    /**
     * 用户id
     */
    private Long wxUserId;
    /**
     * 分享人id
     */
    private Long shareId;

}