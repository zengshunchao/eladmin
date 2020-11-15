package com.study.web.dto;

import com.study.web.entity.WxUser;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zsc
 * @date 2020/11/11 0011 20:35
 */
@Data
public class ApplyDto extends WxUser {

    private Long applyId;

    /**
     * 微信钱包id
     */
    private Long walletWaterId;
    /**
     * 微信用户id
     */
    private Long wxUserId;
    /**
     * 账号名
     */
    private String accountName;
    /**
     * 账号编号
     */
    private String accountNumber;
    /**
     * 审批状态 0-审批中 1-审批通过 2-审批不通过
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 审批时间
     */
    private Date checkTime;

    /**
     *  余额
     */
    private BigDecimal balance;

    /**
     *  提现金额
     */
    private BigDecimal money;
}
