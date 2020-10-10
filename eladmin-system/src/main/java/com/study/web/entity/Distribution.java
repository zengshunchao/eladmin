package com.study.web.entity;

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
public class Distribution implements Serializable {
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
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(Long wxUserId) {
        this.wxUserId = wxUserId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}