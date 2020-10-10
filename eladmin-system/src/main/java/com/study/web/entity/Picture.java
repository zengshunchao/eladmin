package com.study.web.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zengsc
 * @since 2020-09-03 15:54:09
 */

@Data
public class Picture implements Serializable {
    private static final long serialVersionUID = 140061199164802415L;
    /**
     * 通用附件表id
     */
    private Long id;
    /**
     * 附件名称
     */
    private String pictureName;
    /**
     * 附件存储路径
     */
    private String pictureUrl;

    /**
     *  图片类型 1-封面图 2-详情图
     */
    private Integer pictureType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;
}