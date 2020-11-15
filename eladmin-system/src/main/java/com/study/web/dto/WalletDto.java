package com.study.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zengsc
 * @since 2020-10-09 16:38:42
 */

/**
 * 我的钱包(Wallet)实体类
 *
 * @author makejava
 * @since 2020-10-09 16:38:42
 */
@Data
public class WalletDto implements Serializable {
    private static final long serialVersionUID = -15157132388384329L;
    /**
     * 表主键
     */
    private Long id;
    /**
     * 用户id
     */
    private Long wxUserId;
    /**
     * 总额
     */
    private BigDecimal allMoney;
    /**
     * 已取现
     */
    private BigDecimal cashMoney;
    /**
     * 可取现
     */
    private BigDecimal mayCashMoney;
    /**
     * 锁定余额
     */
    private BigDecimal lockMoney;

    //提现金额
    private BigDecimal withdrawal;

    /**
     *  账号名称
     */
    private String accountName;

    /**
     *  账号编号
     */
    private String accountNumber;

    public WalletDto() {
    }

    public WalletDto(Long wxUserId, BigDecimal allMoney, BigDecimal cashMoney, BigDecimal mayCashMoney, BigDecimal lockMoney, BigDecimal withdrawal, String accountName, String accountNumber) {
        this.wxUserId = wxUserId;
        this.allMoney = allMoney;
        this.cashMoney = cashMoney;
        this.mayCashMoney = mayCashMoney;
        this.lockMoney = lockMoney;
        this.withdrawal = withdrawal;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
    }
}