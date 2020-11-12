package com.study.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class WalletWaterDto extends PageInfo {
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
     * 1收入 2支出
     */
    private Integer type;
    /**
     * 流水备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //审批状态
    private Integer status;

    //审批时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date checkTime;
}