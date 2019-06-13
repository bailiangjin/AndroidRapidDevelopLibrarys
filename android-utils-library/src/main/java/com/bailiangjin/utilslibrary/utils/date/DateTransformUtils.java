package com.bailiangjin.utilslibrary.utils.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by bailiangjin on 2017/5/26.
 */
public abstract class DateTransformUtils {

    public static final String CN_TIME_FORMAT_HH_mm = "HH:mm";
    public static final String CN_TIME_FORMAT_HH_mm_ss = "HH:mm:ss";
    public static final String CN_TIME_FORMAT_M_d_HH_mm = "M月d日 HH:mm";
    public static final String CN_TIME_FORMAT_M_d = "M月d日";
    public static final String CN_TIME_FORMAT_yyyy_M_d = "yyyy年M月d日";
    public static final String CN_TIME_FORMAT_yyyy_M_d_simple = "yyyy-M-d";
    public static final String CN_TIME_FORMAT_yyyy_M_d_HH_mm = "yyyy年M月d日 HH:mm";
    public static final String CN_TIME_FORMAT_yyyy_M_d_HH_mm_ss = "yyyy年M月d日 HH:mm:ss";

    public static final String EN_TIME_FORMAT_yyyy_M_d_HH_mm_ss = "yyyy-M-d HH:mm:ss";

    /**
     * 获取当前时间戳 long
     * @return
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间 Date
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 通过 时间戳 得到当前时间
     *
     * @param timeMillis 时间戳
     * @return
     */
    public static Date getDateByTimeMillis(long timeMillis) {
        Timestamp timestamp = new Timestamp(timeMillis);
        return timestamp;
    }

    /**
     * 通过 时间戳字符串 得到当前时间
     *
     * @param timeMillisStr 时间戳字符串
     * @return
     */
    public static Date getDateByTimeMillis(String timeMillisStr) {
        Date date = new Date(Long.parseLong(timeMillisStr));
        return date;
    }

    /**
     * 解析时间字符串
     *
     * @param dateString
     * @param format
     * @return
     */
    public static Date getDate(String dateString, String format) {
        return getDate(dateString, format, Locale.CHINESE, TimeZone.getDefault());
    }

    /**
     * 格式化日期 中国大陆
     *
     * @param date
     * @param format
     * @return
     */
    public static String getDateString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
        return sdf.format(date);
    }

    /**
     * 解析时间字符串
     *
     * @param dateString
     * @param format
     * @param locale
     * @param timeZone
     * @return
     */
    public static Date getDate(String dateString, String format, Locale locale, TimeZone timeZone) {
        SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        sdf.applyPattern(format);
        sdf.setTimeZone(timeZone);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (Exception e) {
            date = null;
        }
        return date;
    }


    /**
     * 将服务器时间转换成当地时间
     *
     * @param date           服务器时区
     * @param serverTimeZone 服务器时区
     * @return
     */
    public static Date getLocalDate(Date date, String serverTimeZone) {
        Date resultDate = null;
        if (date != null) {
            // 本地时区
            Calendar localCalender = Calendar.getInstance();
            TimeZone localTimeZone = localCalender.getTimeZone();
            int timeOffset = TimeZone.getTimeZone(serverTimeZone).getRawOffset() - localTimeZone.getRawOffset();
            resultDate = new Date(date.getTime() - timeOffset);
        }
        return resultDate;
    }

    /**
     * 根据服务器时间和服务器时区 获取当地时间 的时间戳
     * @param timeMillis
     * @param serverTimeZone
     * @return
     */
    public static long getLocalTimeMillis(long timeMillis, String serverTimeZone) {
        Date date = new Date(timeMillis);
        if (date != null) {
            return getLocalDate(date, serverTimeZone).getTime();
        }
        return 0;
    }

    /**
     * 获取更改时区后的时间
     *
     * @param date        时间
     * @param oldTimeZone 旧时区
     * @param newTimeZone 新时区
     * @return 时间
     */
    public static Date changeTimeZone(Date date, TimeZone oldTimeZone, TimeZone newTimeZone) {
        Date newDate = null;
        if (date != null) {
            int timeOffset = oldTimeZone.getRawOffset() - newTimeZone.getRawOffset();
            newDate = new Date(date.getTime() - timeOffset);
        }
        return newDate;
    }



}
