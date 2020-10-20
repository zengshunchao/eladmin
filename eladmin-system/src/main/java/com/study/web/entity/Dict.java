package com.study.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zengsc
 * @since 2020-10-19 17:19:51
 */

/**
 * (Dict)实体类
 *
 * @author makejava
 * @since 2020-10-19 17:19:51
 */
@Data
public class Dict implements Serializable {
    private static final long serialVersionUID = 227078677083938141L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 字典名称-key
     */
    private String dictName;
    /**
     * 字典值-value
     */
    private String dictValue;

    /**
     *  字典值描述
     */
    private String dictDesc;

}