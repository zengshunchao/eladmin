package com.study.web.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {

    public static String getJsonResult(HttpServletResponse response,Object respObj) {
        response.setContentType("application/json;charset=utf-8");
        String respJsonStr = JSONObject.toJSONString(respObj);
        return respJsonStr;
    }
}
