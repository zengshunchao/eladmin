package com.study.web.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author zsc
 * @date 2020/10/22 0022 23:13
 */
@Data
public class BackGroundDistributionInfoDto {

    private String nickName;

    private String phone;

    private String parentNickName;

    private Integer distributionNumber;

    private Integer orderNumber;

    private Integer allMoney;

    private Date createTime;
}
