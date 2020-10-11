package com.study.web.dto;

import lombok.Data;

/**
 * @author zyf
 * @date 2020/10/11
 */
@Data
public class PageInfo {
    //当前页
    private int pageNo;
    //每页的数量
    private int pageSize;
    //分页开始记录
    private int startNum;

    public void setStartNum() {
        this.startNum = this.pageNo > 0 ? (this.pageNo - 1) * this.pageSize : 0;
    }
}
