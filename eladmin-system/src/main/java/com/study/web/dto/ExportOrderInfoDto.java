package com.study.web.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zsc
 * @date 2020/10/17 0017 22:11
 */
@Data
@ExcelTarget("orderExcel")
public class ExportOrderInfoDto {

    /**
     * 订单编号
     */
    @Excel(name = "订单编号", orderNum = "1")
    private String orderNumber;

    /**
     * 课程名称
     */
    @Excel(name = "课程名称", orderNum = "2")
    private String courseName;

    /**
     * 课程购买数量
     */
    @Excel(name = "购买数量", orderNum = "3")
    private Integer courseNumber;
    /**
     * 订单金额
     */
    @Excel(name = "订单金额", orderNum = "4")
    private BigDecimal money;

    /**
     * 订单用户名
     */
    @Excel(name = "订单用户名", orderNum = "5")
    private String orderUserName;

    /**
     * 订单联系电话
     */
    @Excel(name = "订单联系电话", orderNum = "6")
    private String orderPhone;
    /**
     * 备注
     */
    @Excel(name = "订单备注", orderNum = "7")
    private String remark;
    /**
     * 支付时间
     */
    @Excel(name = "支付时间", exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "8", width = 15D)
    private Date payTime;

    /**
     * 订单状态(1-待支付 2-待使用 3-已完成 4-已取消)
     */
    @Excel(name = "订单状态", orderNum = "9")
    private String status;

    /**
     * 系统自动核销时间
     */
    @Excel(name = "自动核销时间", exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "10", width = 15D)
    private Date autoCheckTime;
    /**
     * 订单完成时间
     */
    @Excel(name = "手工核销时间", exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "11", width = 15D)
    private Date finishTime;
    /**
     * 创建时间
     */
    @Excel(name = "下单时间", exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "12", width = 15D)
    private Date createTime;

    /**
     * 订单核销码
     */
    @Excel(name = "核销码", orderNum = "13")
    private String checkCode;

    /**
     * 分享人昵称
     */
    @Excel(name = "分享人昵称", orderNum = "14")
    private String shareName;

}
