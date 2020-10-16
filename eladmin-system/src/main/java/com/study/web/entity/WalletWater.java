package com.study.web.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zengsc
 * @since 2020-10-09 16:45:14
 */

/**
 * 钱包流水(WalletWater)实体类
 *
 * @author makejava
 * @since 2020-10-09 16:45:14
 */
@Data
public class WalletWater implements Serializable {
    private static final long serialVersionUID = 369324903234239626L;
    /**
     * 表主键
     */
    private Long id;
    /**
     * 用户id
     */
    private Long wxUserId;
    /**
     * 金额
     */
    private BigDecimal money;
    /**
     * 1收入 2支出 3其他
     */
    private Integer type;
    /**
     * 流水备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;

}