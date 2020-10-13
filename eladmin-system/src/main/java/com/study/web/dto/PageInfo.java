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
    //结束条数
    private int endNum;
    //数据总页数
    private int totalPage;

    public void setPage() {
        int pageNo = this.pageNo;
        int pageSize = this.getPageSize() == 0 ? 10 : this.getPageSize();
        this.setPage(pageNo,pageSize);
    }

    public void setPage(int pageNo,int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.startNum = pageNo > 0 ? (pageNo - 1) * pageSize : 0;
    }
}
