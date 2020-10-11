package com.study.web.entity;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zengsc
 * @since 2020-09-21 22:48:37
 */

/**
 * (WxUser)实体类
 *
 * @author makejava
 * @since 2020-09-21 22:48:37
 */
@Data
public class WxUser implements Serializable {
    private static final long serialVersionUID = -48373628979560467L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 微信唯一标识
     */
    private String openId;
    /**
     * 登录态
     */
    private String sessionKey;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 语言
     */
    private String wxLanguage;
    /**
     * 城市
     */
    private String city;
    /**
     * 省份
     */
    private String province;
    /**
     * 国家
     */
    private String country;
    /**
     * 头像
     */
    private String avatarUrl;

    /**
     *  真实姓名
     */
    private String realName;

    /**
     *  联系电话
     */
    private String phone;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否分销员
     */
    @Transient
    private Integer distributionFlag;
    /**
     * 上级分销员id
     */
    @Transient
    private Long parentId;

}