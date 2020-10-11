package com.study.web.dto;

import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;

@Data
public class WxUserDto {

    private Long id;

    /**
     *  微信标识
     */
    private String openId;
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

    private String code;

    /**
     *  真实姓名
     */
    private String realName;

    /**
     *  联系电话
     */
    private String phone;

    private Date createTime;

    @Transient
    private String parentName;

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
