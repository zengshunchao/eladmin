package com.study.web.util;

import lombok.Data;

public enum ResponseCode {

    SUCCESS(10000, "成功"),
    FAIL(10001, "系统错误"),
    BADREQUESTPARAM(10002, "缺少必要参数"),
    NODATA(10003, "未查询到数据"),
    UNIFIEDORDER(10004, "统一下单失败");
    int code;
    String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
