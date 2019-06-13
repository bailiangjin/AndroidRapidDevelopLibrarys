package com.bailiangjin.utilslibrary.utils.date;

import java.util.Calendar;
import java.util.Date;

public class DateGapUtils {


    /**
     * 判断时间间隔是否超过一天
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isOverOneDay(long startTime, long endTime) {
        long gap = startTime - endTime;
        if (gap < 0L){
            gap = -gap;
        }
        long oneDayLength = 24 * 60 * 60 * 1000;
        if (gap / oneDayLength >= 1) {
            return true;
        }
        return false;
    }

    /**
     * 获得两个时间的间隔天数
     *
     * @param beginDate
     * @param endDate
     * @return int 间隔天数
     */
    public static int getIntervalDays(Date beginDate, Date endDate) {
        if (beginDate == null || endDate == null)
            return 0;
        long between = (endDate.getTime() - beginDate.getTime()) / 1000;// 除以1000是为了转换成秒
        return (int) between / (24 * 60 * 60);
    }

    /**
     * 判断两个时间戳间距是否大于一分钟
     *
     * @param thisTimeStamp
     * @param lastTimeStamp
     * @return
     */
    public static boolean isOverOneMinute(long thisTimeStamp, long lastTimeStamp) {
        long l = thisTimeStamp - lastTimeStamp;
        if (l < 0L)
            l = -l;
        long oneMinuteLength = 60 * 1000;
        return l < oneMinuteLength;// 一分钟
    }

    /**
     * 判断两个时间戳是否为同一分钟
     *
     * @param thisTimeStamp
     * @param lastTimeStamp
     * @return
     */
    public static boolean isSameMinute(long thisTimeStamp, long lastTimeStamp) {
        TimeInfo timeInfo = getAnyMinuteStartAndEndTime(lastTimeStamp);
        return (thisTimeStamp >= timeInfo.getStartTime()) && (thisTimeStamp <= timeInfo.getEndTime());
    }

    /**
     * 两个时间戳是否为在同一小时
     *
     * @param thisTimeStamp
     * @param lastTimeStamp
     * @return
     */
    public static boolean isSameHour(long thisTimeStamp, long lastTimeStamp) {
        TimeInfo timeInfo = getAnyHourStartAndEndTime(lastTimeStamp);
        return (thisTimeStamp >= timeInfo.getStartTime()) && (thisTimeStamp <= timeInfo.getEndTime());
    }

    /**
     * 是否为当前这一小时
     * @param timeMillis
     * @return
     */
    public static boolean isThisHour(long timeMillis) {
        TimeInfo localTimeInfo = getHourStartAndEndTimeByGap(0);
        return (timeMillis >= localTimeInfo.getStartTime()) && (timeMillis <= localTimeInfo.getEndTime());
    }


    /**
     * 判断是否为今天
     *
     * @param timeMillis
     * @return
     */
    public static boolean isToday(long timeMillis) {
        TimeInfo localTimeInfo = getDayStartAndEndTimeByGap(0);
        return (timeMillis >= localTimeInfo.getStartTime()) && (timeMillis <= localTimeInfo.getEndTime());
    }

    /**
     * 判断是否为昨天
     *
     * @param timeMillis
     * @return
     */
    public static boolean isYesterday(long timeMillis) {
        TimeInfo localTimeInfo = getDayStartAndEndTimeByGap(-1);
        return (timeMillis >= localTimeInfo.getStartTime()) && (timeMillis <= localTimeInfo.getEndTime());
    }

    /**
     * 判断是否为明天
     *
     * @param timeMillis
     * @return
     */
    public static boolean isTomorrow(long timeMillis) {
        TimeInfo localTimeInfo = getDayStartAndEndTimeByGap(1);
        return (timeMillis >= localTimeInfo.getStartTime()) && (timeMillis <= localTimeInfo.getEndTime());
    }

    /**
     * 判断是否为后天
     *
     * @param timeMillis
     * @return
     */
    public static boolean isTheDayAfterTomorrow(long timeMillis) {
        TimeInfo localTimeInfo = getDayStartAndEndTimeByGap(2);
        return (timeMillis >= localTimeInfo.getStartTime()) && (timeMillis <= localTimeInfo.getEndTime());
    }

    /**
     * 判断是否为本月
     *
     * @param timeMillis
     * @return
     */
    public static boolean isThisMonth(long timeMillis) {
        return isMonthByGap(timeMillis, 0);
    }

    /**
     * 是否为上月
     *
     * @param timeMillis
     * @return
     */
    public static boolean isLastMonth(long timeMillis) {

        int monthGap = -1;
        return isMonthByGap(timeMillis, monthGap);
    }


    /**
     * 判断是否为今年
     *
     * @param timeMillis
     * @return
     */
    public static boolean isThisYear(long timeMillis) {
        return isYearByGap(timeMillis, 0);
    }

    private static boolean isMonthByGap(long timeMillis, int monthGap) {
        TimeInfo localTimeInfo = getMonthStartAndEndTimeByGap(monthGap);
        return (timeMillis >= localTimeInfo.getStartTime()) && (timeMillis <= localTimeInfo.getEndTime());
    }

    /**
     * 是否为 与今年相差yearGap的某一年
     *
     * @param timeMillis
     * @return
     */
    public static boolean isYearByGap(long timeMillis, int yearGap) {
        TimeInfo localTimeInfo = getYearStartAndEndTime(yearGap);
        return (timeMillis >= localTimeInfo.getStartTime()) && (timeMillis <= localTimeInfo.getEndTime());
    }


    public static TimeInfo getAnyHourStartAndEndTime(long timeMillis) {
        Calendar localCalendarStart = Calendar.getInstance();
        localCalendarStart.setTime(new Date(timeMillis));
        SetDatePointUtils.set2HourStart(localCalendarStart);
        long startTime = localCalendarStart.getTime().getTime();


        Calendar localCalendarEnd = Calendar.getInstance();
        localCalendarEnd.setTime(new Date(timeMillis));
        SetDatePointUtils.set2HourEnd(localCalendarEnd);
        long endTime = localCalendarEnd.getTime().getTime();

        return new TimeInfo(startTime, endTime);
    }


    /**
     * 获取某一分钟起始时间
     *
     * @return
     */

    /**
     * @param timeMillis
     * @return
     */
    public static TimeInfo getAnyMinuteStartAndEndTime(long timeMillis) {
        Calendar localCalendarStart = Calendar.getInstance();
        localCalendarStart.setTime(new Date(timeMillis));
        SetDatePointUtils.set2MinuteStart(localCalendarStart);
        long startTime = localCalendarStart.getTime().getTime();


        Calendar localCalendarEnd = Calendar.getInstance();
        localCalendarEnd.setTime(new Date(timeMillis));
        SetDatePointUtils.set2MinuteEnd(localCalendarEnd);
        long endTime = localCalendarEnd.getTime().getTime();

        return new TimeInfo(startTime, endTime);
    }

    /**
     * 获取与当前小时 相差 hourTimeGap 小时的那一小时的起始时间
     *
     * @param hourTimeGap
     * @return
     */
    public static TimeInfo getHourStartAndEndTimeByGap(int hourTimeGap) {
        Calendar localCalendarStart = Calendar.getInstance();
        localCalendarStart.add(Calendar.HOUR_OF_DAY, hourTimeGap);
        SetDatePointUtils.set2HourStart(localCalendarStart);
        long startTime = localCalendarStart.getTime().getTime();


        Calendar localCalendarEnd = Calendar.getInstance();
        localCalendarEnd.add(Calendar.HOUR_OF_DAY, hourTimeGap);
        SetDatePointUtils.set2HourEnd(localCalendarEnd);
        long endTime = localCalendarEnd.getTime().getTime();

        return new TimeInfo(startTime, endTime);
    }

    /**
     * 获取与今天相差  dayTimeGap天的 那一天的起始时间
     *
     * @param dayTimeGap
     * @return
     */
    public static TimeInfo getDayStartAndEndTimeByGap(int dayTimeGap) {
        Calendar localCalendarStart = Calendar.getInstance();
        localCalendarStart.add(Calendar.DAY_OF_YEAR, dayTimeGap);// 时间偏差
        SetDatePointUtils.set2DayStart(localCalendarStart);
        long startTime = localCalendarStart.getTime().getTime();


        Calendar localCalendarEnd = Calendar.getInstance();
        localCalendarEnd.add(Calendar.DAY_OF_YEAR, dayTimeGap);// 时间向前两天
        SetDatePointUtils.set2DayEnd(localCalendarEnd);
        long endTime = localCalendarEnd.getTime().getTime();

        return new TimeInfo(startTime, endTime);
    }

    /**
     * 获取相对于本月的某个月起始时间
     *
     * @param monthGap 与当前月月差
     * @return
     */
    public static TimeInfo getMonthStartAndEndTimeByGap(int monthGap) {
        // 开始时间
        Calendar localCalendarStart = Calendar.getInstance();
        localCalendarStart.add(Calendar.MONTH, monthGap);// 向前一月
        SetDatePointUtils.set2MonthStart(localCalendarStart);
        Date startDate = localCalendarStart.getTime();
        long startTime = startDate.getTime();

        // 结束时间
        Calendar localCalendarEnd = Calendar.getInstance();
        localCalendarEnd.add(Calendar.MONTH, monthGap);// 向前一月
        SetDatePointUtils.set2MonthEnd(localCalendarEnd);
        Date endDate = localCalendarEnd.getTime();
        long endTime = endDate.getTime();


        return new TimeInfo(startTime, endTime);
    }


    /**
     * 与今年相差 yearGap年的某一年的起始时间
     *
     * @param yearGap
     * @return
     */
    public static TimeInfo getYearStartAndEndTime(int yearGap) {
        Calendar localCalendarStart = Calendar.getInstance();
        localCalendarStart.add(Calendar.YEAR, yearGap);
        SetDatePointUtils.set2YearStart(localCalendarStart);
        Date startDate = localCalendarStart.getTime();
        long startTime = startDate.getTime();

        Calendar localCalendarEnd = Calendar.getInstance();
        localCalendarEnd.add(Calendar.YEAR, yearGap);

        SetDatePointUtils.set2YearEnd(localCalendarEnd);
        Date endDate = localCalendarEnd.getTime();
        long endTime = endDate.getTime();
        return new TimeInfo(startTime, endTime);
    }


    /**
     * 时间信息工具类
     *
     * @author blj
     */
    static class TimeInfo {
        private long startTime;
        private long endTime;

        public TimeInfo(long startTime, long endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public long getStartTime() {
            return this.startTime;
        }

        public void setStartTime(long timeMillis) {
            this.startTime = timeMillis;
        }

        public long getEndTime() {
            return this.endTime;
        }

        public void setEndTime(long timeMillis) {
            this.endTime = timeMillis;
        }
    }

}
