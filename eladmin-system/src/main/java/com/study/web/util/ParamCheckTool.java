package com.study.web.util;


import com.study.web.dto.ResultValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
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

    public static ResultValue checkParam(List<String> checkParam, Object object) {
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (checkParam.contains(field.getName())) {
                    String getter = "get" + upperCase(field.getName());
                    Method method = object.getClass().getMethod(getter, new Class[]{});
                    Object value = method.invoke(object, new Object[]{});
                    if (StringUtils.isEmpty(value)
                            || (value instanceof ArrayList<?> && ((ArrayList) value).size() == 0)) {
                        return new ResultValue<>(ResponseCode.BADREQUESTPARAM.getCode(), ResponseCode.BADREQUESTPARAM.getMsg() + field.getName());
                    }
                }
            }
        } catch (Exception e) {
            log.error("checkParam fail {}", e);
            return new ResultValue<>(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
        return null;
    }

    //字符串首字母大写
    public static String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }


}
