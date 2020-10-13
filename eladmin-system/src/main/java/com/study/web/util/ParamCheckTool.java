package com.study.web.util;


import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.Map;


public class ParamCheckTool {

    /**
     * 校验参数是否为空
     *
     * @param checkParam
     * @return
     */
    public static JSONObject checkParam(Map<String, String> checkParam) {
        for (Map.Entry<String, String> e : checkParam.entrySet()) {
            if (StringUtils.isEmpty(e.getValue())) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", ResponseCode.BADREQUESTPARAM.getCode());
                jsonObject.put("msg", ResponseCode.BADREQUESTPARAM.getMsg() + e.getKey());
                return jsonObject;
            }
        }
        return null;
    }

}
