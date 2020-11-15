package com.study.web.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zengsc
 * @since 2020-11-11 20:08:35
 */

/**
 * (Apply)实体类
 *
 * @author makejava
 * @since 2020-11-11 20:08:35
 */
public class Apply implements Serializable {
    private static final long serialVersionUID = -23799682183495519L;
    /**
     * 主键
     */
    private Long id;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWalletWaterId() {
        return walletWaterId;
    }

    public void setWalletWaterId(Long walletWaterId) {
        this.walletWaterId = walletWaterId;
    }

    public Long getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(Long wxUserId) {
        this.wxUserId = wxUserId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

}