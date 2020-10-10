package com.study.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zengsc
 * @since 2020-09-07 23:41:27
 */

/**
 * (CoursePictureRel)实体类
 *
 * @author makejava
 * @since 2020-09-07 23:41:27
 */
@Data
public class CoursePictureRel implements Serializable {
    private static final long serialVersionUID = 765028797088703250L;
    /**
     * 课程表和附件表中间关联表id
     */
    private Long id;
    /**
     * 课程表id
     */
    private Long courseId;
    /**
     * 附件表id
     */
    private Long pictureId;


}