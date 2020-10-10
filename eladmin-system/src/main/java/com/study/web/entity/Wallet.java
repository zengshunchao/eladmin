package com.study.web.entity;

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
public class Wallet implements Serializable {
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

}