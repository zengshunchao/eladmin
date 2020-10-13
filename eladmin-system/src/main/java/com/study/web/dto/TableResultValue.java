package com.study.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: zyf
 * @date: 2020/10/13
 */
@SuppressWarnings("unused")
@ApiModel(description = "返回结果")
public class TableResultValue<T> {

    /**状态码*/
    @ApiModelProperty("状态码")
    private Integer code;
    /**说明*/
    @ApiModelProperty("说明")
    private String msg;
    /**总条数*/
    @ApiModelProperty("总条数")
    private Integer total;
    /**数据*/
    @ApiModelProperty("数据")
    private T data;

    private Long userId;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
