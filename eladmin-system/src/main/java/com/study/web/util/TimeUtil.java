package com.study.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式转换工具
 *
 * @author zsc
 * @date 2020/10/14 0014 10:03
 */
public class TimeUtil {


    /**
     * 字符串转换成date
     *
     * @param timeString
     * @param format
     * @return
     */
    public static Date stringToDate(String timeString, String format) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            date = simpleDateFormat.parse(timeString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

}
