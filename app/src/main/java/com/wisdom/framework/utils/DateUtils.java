package com.wisdom.framework.utils;

import android.app.Activity;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 字符日期装换类
 *
 * @author xyu
 */
public class DateUtils {

    public static final String DATE_FORMAT_Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_Y_M_D_h_M_S = "yyyy-MM-dd hh:mm:ss";
    public static final String DATE_FORMAT_Y_M_D_H_M = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_Y_M_D_h_M = "yyyy-MM-dd hh:mm";
    public static final String DATE_FORMAT_Y_M_D = "yyyy-MM-dd";
    public static final String DATE_FORMAT_Y_M = "yyyy-MM";
    public static final String DATE_FORMAT_M_D = "MM-dd";
    public static final String DATE_FORMAT_Y_M_D_CH = "yyyy年MM月dd日";
    public static final String H_M_S = "HH:mm:ss";
    public static final String h_M_S = "hh:mm:ss";
    private static SimpleDateFormat sdf;
    private static Date dateNew;
    private static Date startday;
    private static Date today;
    private static String dateStrNew;
    private static String todayStr;

    private static int oneSec = 1000;
    private static int oneMin = oneSec * 60;
    private static int oneHour = oneMin * 60;
    private static int aDay = oneHour * 24;

    /**
     * 日期装换成字符
     *
     * @param dateFormat
     * @param date
     * @return string
     * @tip dtf=dateTransformString
     */
    public static String dts(String dateFormat, Date date) {
        sdf = new SimpleDateFormat(dateFormat,Locale.CHINA);
        if (date != null) {
            dateStrNew = sdf.format(date);
        } else {
            return null;
        }
        return dateStrNew;
    }

    /**
     * 日期装换成字符
     *
     * @param date
     * @return string
     * @tip dtf=dateTransformString
     */
    public static String dts(Date date) {
        return dts(DATE_FORMAT_Y_M_D, date);
    }

    /**
     * 字符装换成日期
     *
     * @param dateFormat
     * @param dateStr
     * @return
     * @tip std=stringTransformDate
     */
    public static Date std(String dateFormat, String dateStr) {
        sdf = new SimpleDateFormat(dateFormat,Locale.CHINA);

        if ((dateStr != null) && (!dateStr.equals(""))) {

            try {
                dateNew = sdf.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            return null;
        }

        return dateNew;
    }

    /**
     * 字符装换成日期
     *
     * @param dateStr
     * @return
     * @tip std=stringTransformDate
     */
    public static Date std(String dateStr) {
        return std(DATE_FORMAT_Y_M_D, dateStr);
    }

    /**
     * 今天和所选日期的比较
     *
     * @param activity
     * @param stringInt    开始时间比今天时间小的错误提示字符
     * @param dateFormat
     * @param startDateStr
     * @return
     */
    public static boolean compareTodayToStartDay(Activity activity,
                                                 int stringInt, String dateFormat, String startDateStr) {
        sdf = new SimpleDateFormat(dateFormat,Locale.CHINA);
        todayStr = sdf.format(new Date());

        startday = std("yyyy-MM-dd", startDateStr);
        try {
            today = sdf.parse(todayStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ((today.before(startday)) || (today.equals(startday))) {
            return true;
        }
        Toast.makeText(activity, activity.getResources().getString(stringInt),
                Toast.LENGTH_SHORT).show();

        return false;
    }

    /**
     * 两个日期的比较
     *
     * @param activity
     * @param StringStartInt 开始时间为空时错误的字符
     * @param StringEndInt   结束时间比开始时间早的错误提示
     * @param startDay       较前的时间
     * @param endDay         较后的时间
     * @return
     */
    public static boolean compareStaryDayToEndDay(Activity activity,
                                                  int StringStartInt, int StringEndInt, Date startDay, Date endDay) {
        if (startDay == null) {
            Toast.makeText(activity,
                    activity.getResources().getString(StringStartInt),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if ((endDay.after(startDay)) || (endDay.equals(startDay))) {
            return true;
        }

        Toast.makeText(activity,
                activity.getResources().getString(StringEndInt),
                Toast.LENGTH_SHORT).show();

        return false;
    }

    /**
     * 两个日期的比较
     *
     * @param activity
     * @param StringStartInt 开始时间为空时错误的字符
     * @param StringEndInt   结束时间比开始时间早的错误提示
     * @param startDay       较前的时间
     * @param endDay         较后的时间
     * @return
     */
    public static boolean compareStaryDayToEndDayAtNow(Activity activity,
                                                       int StringStartInt, int StringEndInt, Date startDay, Date endDay) {
        if (startDay == null) {
            return false;
        }

        if ((startDay.after(endDay))) {
            return true;
        }


        return false;
    }

    /**
     * 获取具有格式的今天的时间字符
     *
     * @return
     */
    public static String getTodayDateStr(String dateFormat) {
        sdf = new SimpleDateFormat(dateFormat,Locale.CHINA);
        return sdf.format(new Date());
    }

    /**
     * 获取两个日期之间的天数
     *
     * @return
     */
    public static int getNumOfDate(Date dateOne, Date dateTwo) {
        sdf = new SimpleDateFormat(DATE_FORMAT_Y_M_D,Locale.CHINA);

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        long beforeDayStr = 0;
        long afterDayStr = 0;
        if (dateOne.after(dateTwo)) {
            calendar.setTime(dateTwo);
            beforeDayStr = calendar.getTimeInMillis();
            calendar.setTime(dateOne);
            afterDayStr = calendar.getTimeInMillis();
        } else {
            calendar.setTime(dateOne);
            beforeDayStr = calendar.getTimeInMillis();
            calendar.setTime(dateTwo);
            afterDayStr = calendar.getTimeInMillis();
        }

        return (int) ((afterDayStr - beforeDayStr) / aDay);
    }

    public static String computeTheTimeDifference(Date startDay) {
        today = new Date();
        long nowDate = today.getTime();
        long startDate = startDay.getTime();
        // String date1 = dateTransformString("yyyy-MM-dd HH:mm:ss", new
        // Date());
        // String date = dateTransformString("yyyy-MM-dd HH:mm:ss", startDay);
        long ss = (nowDate - startDate) / (1000); // 共计秒数
        int MM = (int) ss / 60; // 共计分钟数
        int hh = (int) ss / 3600; // 共计小时数
        int dd = (int) hh / 24; // 共计天数
        if (dd <= 1 && dd > 0) {
            return String.valueOf(dd) + "天前";
        } else if (hh > 0 && hh < 24) {
            return String.valueOf(hh) + "小时前";
        } else if (MM > 0 && MM < 60) {
            return String.valueOf(MM) + "分钟前";
        } else if (ss > 0 && ss < 60) {
            return String.valueOf(ss) + "秒前";
        } else {
            return dts("MM-dd", startDay);
        }
    }

    public static String getCostTimeStr(int minutes){
        StringBuilder costTimeStr = new StringBuilder();

        if (minutes < 60) {
            costTimeStr.append(minutes);
            costTimeStr.append("分钟");
        } else {
            int hour = minutes / 60;
            costTimeStr.append(hour);
            costTimeStr.append("小时");
            int remainMinutes = minutes % 60;
            if (remainMinutes > 0) {
                costTimeStr.append(remainMinutes);
                costTimeStr.append("分钟");
            }
        }
        return costTimeStr.toString();
    }

    public static String getDayOfWeek(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int firstDayOfWeek = instance.get(Calendar.DAY_OF_WEEK);
        String dayOfWeek = "";
        switch (firstDayOfWeek) {
            case Calendar.SUNDAY:
                dayOfWeek = "周日";
                break;
            case Calendar.MONDAY:
                dayOfWeek = "周一";
                break;
            case Calendar.TUESDAY:
                dayOfWeek = "周二";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = "周三";
                break;
            case Calendar.THURSDAY:
                dayOfWeek = "周四";
                break;
            case Calendar.FRIDAY:
                dayOfWeek = "周五";
                break;
            case Calendar.SATURDAY:
            default:
                dayOfWeek = "周六";
                break;
        }
        return dayOfWeek;
    }

    public static  String getMonthOfYearStr(Date date){
        if (date.getYear() != new Date().getYear())
            return DateUtils.dts("yyyy年MM月", date);
        return (date.getMonth()+1) + "月";
    }

    public static String getStringDate(Long l){
        Date date = new Date(l);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_Y_M_D_h_M_S,Locale.CHINA);
        return sdf.format(date);
    }

    public static String getStringDateM(Long l){
        Date date = new Date(l);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_Y_M_D_h_M,Locale.CHINA);
        return sdf.format(date);
    }
    public static String getmMonthandDay(Date date){
        return DateUtils.dts("MM-dd", date);
    }


    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
