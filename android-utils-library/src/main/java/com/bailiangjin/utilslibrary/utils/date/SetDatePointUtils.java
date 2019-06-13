package com.bailiangjin.utilslibrary.utils.date;

import java.util.Calendar;

/**
 * Created by bailiangjin on 2017/5/26.
 */
public class SetDatePointUtils {

    //------------------------年月日时分初始值设置


    public final static void set2YearStart(Calendar calendar) {
        calendar.set(Calendar.MONTH, 0);
        set2MonthStart(calendar);
    }

    public final static void set2YearEnd(Calendar calendar) {
        calendar.set(Calendar.MONTH, 12);
        set2MonthEnd(calendar);
    }

    public final static void set2MonthStart(Calendar calendar) {
        calendar.set(Calendar.DATE, 1);
        set2DayStart(calendar);
    }

    public final static void set2MonthEnd(Calendar calendar) {
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        set2DayEnd(calendar);
    }

    /**
     * 设置回到一天的初始时刻
     *
     * @param calendar
     */
    public final static void set2DayStart(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        set2HourStart(calendar);
    }

    /**
     * 设置回到一天的结束时刻
     *
     * @param calendar
     */
    public final static void set2DayEnd(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        set2HourEnd(calendar);
    }

    public final static void set2HourStart(Calendar calendar) {
        calendar.set(Calendar.MINUTE, 0);
        set2MinuteStart(calendar);
    }

    public final static void set2HourEnd(Calendar calendar) {
        calendar.set(Calendar.MINUTE, 59);
        set2MinuteEnd(calendar);
    }

    public final static void set2MinuteStart(Calendar calendar) {
        calendar.set(Calendar.SECOND, 0);
        set2SecondStart(calendar);
    }

    public final static void set2MinuteEnd(Calendar calendar) {
        calendar.set(Calendar.SECOND, 59);
        set2SecondEnd(calendar);
    }

    public final static void set2SecondStart(Calendar calendar) {
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public final static void set2SecondEnd(Calendar calendar) {
        calendar.set(Calendar.MILLISECOND, 999);
    }

    //-----------------年月日时分秒首末值设置结束 ----------------------

}
