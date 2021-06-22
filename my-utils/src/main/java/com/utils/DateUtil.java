package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author luozhihong
 * @title: DateConverUtil
 * @description: 日期工具类
 * @date 2021/6/189:46
 */
public class DateUtil {

    public static SimpleDateFormat dateFormatAsDay = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat dateFormatAsHours = new SimpleDateFormat("HH");

    /**
     * @Description: 获取 年-月-日，例如："1996-08-07"
     * @Author: luozhihong
     * @Date: 2021/6/18
     * @return: java.lang.String
     **/
    public static String converToStringAsDay(Date date) {
        return dateFormatAsDay.format(date);
    }

    /**
     * @Description: 字符串转换为 Date，
     * @Author: luozhihong
     * @Date: 2021/6/22
     * @param param: 参数类型："1996-08-07"
     * @return: java.util.Date
     */
    public static Date converToDateAsDay(String param) throws ParseException {
        return dateFormatAsDay.parse(param);
    }

    /**
     * @Description: 获取 日期的小时
     * @Author: luozhihong
     * @Date: 2021/6/22 
     * @param date: 
     * @return: java.lang.String
     **/
    public static String converToStringAsHours(Date date) {
        return dateFormatAsHours.format(date);
    }

    /**
     * @Description: 获取 5 分钟前的时间
     * @Author: luozhihong
     * @Date: 2021/6/22
     * @param date:
     * @return: java.util.Date
     **/
    public static Date getDateBeforeFiveMinutes(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, -5);
        return cal.getTime();
    }

    /**
     * @Description: 获取当前日期周一的时间
     * @Author: luozhihong
     * @Date: 2021/6/22
     * @param date:
     * @return: java.util.Date
     **/
    public static Date getMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, Calendar.MONDAY - day);
        return cal.getTime();
    }

    /**
     * @Description: 获取当前日期周末的时间
     * @Author: luozhihong
     * @Date: 2021/6/22
     * @param date:
     * @return: java.util.Date
     **/
    public static Date getSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == day) {
            return cal.getTime();
        }
        cal.add(Calendar.DATE, 8 - day);
        return cal.getTime();
    }

    /**
     * @Description: 获取当前日期 月初时间
     * @Author: luozhihong
     * @Date: 2021/6/22
     * @param date:
     * @return: java.util.Date
     */
    public static Date getBeginMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 月初是 1号
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * @Description: 获取当前日期 月末时间
     * @Author: luozhihong
     * @Date: 2021/6/22
     * @param date:
     * @return: java.util.Date
     */
    public static Date getEndMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 当前日期 月数加 1
        cal.add(Calendar.MONTH, 1);
        // 日期 0，就是上个月末
        cal.set(Calendar.DAY_OF_MONTH, 0);
        return cal.getTime();
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormatAsDay = new SimpleDateFormat("yyyy-MM-dd");
        String par = "2020-12-2";
        Date date = dateFormatAsDay.parse(par);
        System.out.println(converToStringAsDay(getBeginMonth(date)));
        System.out.println(converToStringAsDay(getEndMonth(date)));
    }
}
