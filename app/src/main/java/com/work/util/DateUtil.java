package com.work.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 获取今天在全年的周数量
     */
    public static String GetYear_Week_Num() {
        Date d = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(d);
        int num = calendar.get(Calendar.WEEK_OF_YEAR);
        d.setMonth(12);
        d.setDate(31);
        calendar.setTime(d);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        if (week != 53) {
            week = 52;
        }
        return num + "/" + week;
    }

    /**
     * 获取日期在全年的周数量
     */
    public static String GetYear_Week_Num(Date temp) {
        Date d = (Date) temp.clone();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(d);
        int num = calendar.get(Calendar.WEEK_OF_YEAR);
        d.setMonth(12);
        d.setDate(31);
        calendar.setTime(d);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        if (week != 53) {
            week = 52;
        }
        return num + "/" + week;
    }

    /**
     * 0 不是, 1 是, 2出错
     */
    public static int isSameDay(Date d1, Date d2) {
        if (d1 != null && d2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(d1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(d2);
            return isSameDay(cal1, cal2);
        } else {
            return 2;
        }
    }

    /**
     * 0 不是, 1 是, 2出错
     */
    public static int isSameDay(Calendar d1, Calendar d2) {
        if (d1 != null && d2 != null) {
            if (d1.get(0) == d2.get(0) && d1.get(1) == d2.get(1) && d1.get(6) == d2.get(6)) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 2;
        }
    }

    /**
     * 获取昨天
     */
    public static Date GetLastDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);   //设置当前日期
        c.add(Calendar.DATE, -1); //日期减1天
        return c.getTime();
    }

    /**
     * 获取明天
     */
    public static Date GetNextDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);   //设置当前日期
        c.add(Calendar.DATE, 1); //日期加1天
        return c.getTime();
    }

    /**
     * 时间字符串转时间 yyyy-MM-dd HH:mm:ss
     */
    public static Date getDateFromString(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间转字符串 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringFromDate(Date d) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(d);
    }

    /**
     * 获取今天
     *
     * @return String
     */
    public static String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 获取昨天
     *
     * @return String
     */
    public static String getYestoday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }

    /**
     * 获取本月开始日期
     *
     * @return String
     **/
    public static String getMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 00：00：00";
    }

    /**
     * 获取本月最后一天
     *
     * @return String
     **/
    public static String getMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59:59";
    }

    /**
     * 获取本周的第一天
     *
     * @return String
     **/
    public static String getWeekStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 00:00:00";
    }

    /**
     * 获取本周的最后一天
     **/
    public static String getWeekEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_WEEK, 1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59:59";
    }

    /**
     * 获取本年的第一天
     *
     * @return String
     **/
    public static String getYearStart() {
        return new SimpleDateFormat("yyyy").format(new Date()) + "-01-01";
    }

    /**
     * 获取本年的最后一天
     *
     * @return String
     **/
    public static String getYearEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date currYearLast = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(currYearLast) + " 23:59:59";
    }

}
