package com.study.web.dto;

/**
 * @author: zyf
 * @date: 2020/10/13
 */
public class ResultValue<T> {
    private Integer code;
    private String msg;
    private T data;

    public ResultValue(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultValue(Integer code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
    public ResultValue(){

    }
    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
