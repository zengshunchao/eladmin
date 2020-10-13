package com.study.web.wxApi;

import com.study.web.dto.PageInfo;
import com.study.web.dto.ResultValue;
import com.study.web.dto.TableResultValue;

/**
 * @author zyf
 * @date 2020/10/13
 */
public class JsonResultController {
    /**
     * layui表格统一JSON返回
     * @param code
     * @param msg
     * @param total
     * @param data
     * @param <T>
     * @return
     */
    protected <T> TableResultValue<T> tableJsonResult(Integer code, String msg, Integer total, T data) {
        TableResultValue<T> tableResultValue = new TableResultValue<>();
        tableResultValue.setCode(code);
        tableResultValue.setMsg(msg);
        tableResultValue.setTotal(total);
        tableResultValue.setData(data);
        return tableResultValue;
    }

    /**
     * vue方式提交给前台
     * @param code
     * @param msg
     * @param total
     * @param data
     * @param userId
     * @param <T>
     * @return
     */
    protected <T> TableResultValue<T> tableJsonResult(Integer code, String msg, Integer total, T data,Long userId) {
        TableResultValue<T> tableResultValue = new TableResultValue<>();
        tableResultValue.setCode(code);
        tableResultValue.setMsg(msg);
        tableResultValue.setTotal(total);
        tableResultValue.setData(data);
        tableResultValue.setUserId(userId);
        return tableResultValue;
    }

    /**
     * 统一错误状态返回
     * @param code
     * @return ResultValue
     */
    protected <T> TableResultValue<T> errorTableResult(Integer code,String msg) {
        TableResultValue<T> tableResultValue = new TableResultValue<>();
        tableResultValue.setCode(code);
        tableResultValue.setMsg(msg);
        return tableResultValue;
    }

    /**
     * 统一JSON返回
     * @param code
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    protected <T> ResultValue<T> jsonResult(Integer code, String msg, T data) {
        ResultValue<T> resultValue = new ResultValue<>();
        resultValue.setCode(code);
        resultValue.setMsg(msg);
        if (data != null) {
            resultValue.setData(data);
        }
        return resultValue;
    }
    /**
     * 统一状态返回
     * @param code
     * @return ResultValue
     */
    protected <T> ResultValue<T> result(Integer code) {
        ResultValue<T> resultValue = new ResultValue<>();
        resultValue.setCode(code);
        return resultValue;
    }
    /**
     * 统一错误状态返回
     * @param code
     * @return ResultValue
     */
    protected <T> ResultValue<T> errorResult(Integer code,String msg) {
        ResultValue<T> resultValue = new ResultValue<>();
        resultValue.setCode(code);
        resultValue.setMsg(msg);
        return resultValue;
    }
    /**
     * 统一正常状态返回
     * @param code
     * @return ResultValue
     */
    protected <T> ResultValue<T> successResult(Integer code,String msg) {
        ResultValue<T> resultValue = new ResultValue<>();
        resultValue.setCode(code);
        resultValue.setMsg(msg);
        return resultValue;
    }

    protected void setPageInfo(PageInfo model){
        int pageNo = model.getPageNo();
        int pageSize = model.getPageSize();
        model.setPage(pageNo,pageSize);
    }
}
