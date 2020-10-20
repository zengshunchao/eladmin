package com.study.web.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 时间格式转换工具
 *
 * @author zsc
 * @date 2020/10/14 0014 10:03
 */
public class TimeUtil {

    public final static String DAY = "day";
    public final static String HOUR = "hour";
    public final static String MINUTE = "minute";
    public final static String SECOND = "second";

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

    /**
     * 字符串转换成date
     *
     * @param timeStamp
     * @param format
     * @return
     */
    public static Date timeStampToDate(Long timeStamp, String format) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            String dateString = simpleDateFormat.format(timeStamp);
            date = simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 把日期转换为字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

    /**
     * 将时间转换成定时表达式
     *
     * @param date
     * @return
     */
    public static String transCorn(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return formatDate(date, dateFormat);
    }

    /**
     * 取某个时间点几天后的某个时间点
     *
     * @param d     原日期
     * @param count 天数
     * @return 目标日期
     */
    public static Date afterTime(Date d, String type, int count) {
        LocalDateTime localDateTime = dateToLocalDateTime(d);
        if (type.equals(DAY)) {
            localDateTime = localDateTime.plusDays(count);
        } else if (type.equals(HOUR)) {
            localDateTime = localDateTime.plusHours(count);
        } else if (type.equals(MINUTE)) {
            localDateTime = localDateTime.plusMinutes(count);
        } else if (type.equals(SECOND)) {
            localDateTime = localDateTime.plusSeconds(count);

        }
        return localDateTimeToDate(localDateTime);
    }

    /**
     * 取某个时间点后的某个时间点
     *
     * @param dateStr   原日期
     * @param inFormat  日期格式
     * @param outFormat 日期输出格式
     * @param count     天数
     * @return 目标日期
     * @throws Exception
     */
    public static String afterTime(String dateStr, String type, String inFormat, String outFormat, int count) throws Exception {
        Date d = stringToDate(dateStr, inFormat);
        return formatDate(afterTime(d, type, count), outFormat);
    }

    /**
     * 取某个时间点后的某个时间点
     *
     * @param date
     * @param type
     * @param outFormat
     * @param count
     * @return
     * @throws Exception
     */
    public static String afterTime(Date date, String type, String outFormat, int count) throws Exception {
        return formatDate(afterTime(date, type, count), outFormat);
    }


    /**
     * localDateTimeToDate
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * dateToLocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
