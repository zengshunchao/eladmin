package com.study.web.dto;

import lombok.Data;

/**
 * @author zyf
 * @date 2020/10/11
 */
@Data
public class PageInfo {
    //当前页
    private int pageNo = 0;
    //每页的数量
    private int pageSize = 0;
    //分页开始记录
    private int startNum = 0;

    public void setPage() {
        this.startNum = this.pageNo > 0 ? (this.pageNo - 1) * this.pageSize : 0;
    }
}
