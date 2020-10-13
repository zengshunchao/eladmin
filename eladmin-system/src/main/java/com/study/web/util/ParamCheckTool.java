package com.study.web.util;


import com.alibaba.fastjson.JSONObject;
import com.study.web.dto.ResultValue;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.StringUtils;

import java.util.Map;


public class ParamCheckTool {

    /**
     * 校验参数是否为空
     *
     * @param checkParam
     * @return
     */
    public static ResultValue checkParam(Map<String, Object> checkParam) {
        for (Map.Entry<String, Object> e : checkParam.entrySet()) {
            if (StringUtils.isEmpty(e.getValue())) {
                ResultValue<T> resultValue = new ResultValue<>();
                resultValue.setCode(ResponseCode.BADREQUESTPARAM.getCode());
                resultValue.setMsg(ResponseCode.BADREQUESTPARAM.getMsg() + e.getKey());
                return resultValue;
            }
        }
        return null;
    }

}
