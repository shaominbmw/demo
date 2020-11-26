package com.demo.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName TimeUtils
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-13 14:02
 **/
public class TimeUtils {

    /**
     * 获取某年第几周yyyy-x格式
     *
     * @param date
     * @return
     */
    public static String getWeekOfYear(String date) {
        Date dateParse = dateParse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateParse);
        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        return year + "-" + week;
    }

    /**
     * 获取年月yyyy-mm格式
     *
     * @param date
     * @return
     */
    public static String getMonthOfYear(String date) {
        Date dateParse = dateParse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateParse);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return year + "-" + month;
    }

    /**
     * 获取月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(String date) {
        Date dateParse = dateParse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateParse);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取周一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(String date) {
        Date dateParse = dateParse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateParse);
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        return calendar.getTime();
    }

    /**
     * 获取年第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfYear(String date) {
        Date dateParse = dateParse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateParse);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    /**
     * 获取前几天或后几天时间
     *
     * @param date
     * @param x
     * @return
     */
    public static Date getBeforeOrAfterDay(String date, int x) {
        Date dateParse = dateParse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateParse);
        calendar.add(Calendar.DAY_OF_YEAR, x);
        return calendar.getTime();
    }

    /**
     * 时间转换 年月日时分秒-格式
     *
     * @param date
     * @return
     */
    public static String dateFormat_hms(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 时间转换 年月日-格式
     *
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 获取指定年月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    /**
     * 获取指定年月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    public static Date dateParse(String date) {
        Date parse = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

            parse = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;

    }

    /**
     * 获取
     *
     * @param date
     * @return
     */
    public static Map<String, ArrayList<String>> getWeekScopeOfMonth(String date) {
        Map<String, ArrayList<String>> map = null;
        try {
            Date parse = dateParse(date);

            Calendar c = Calendar.getInstance();

            c.setTime(parse);

            c.set(Calendar.DAY_OF_MONTH, 1);

            int max = c.getActualMaximum(Calendar.DATE);

            int x = 0;

            int week = 1;

            map = new HashMap<>();

            ArrayList<String> list = new ArrayList<>();

            for (int i = 1; i <= max; i++) {

                list.add(TimeUtils.dateFormat(c.getTime()));

                c.add(Calendar.DAY_OF_YEAR, 1);

                map.put(String.valueOf(week), list);

                x++;

                if (x == 7) {

                    list = new ArrayList<>();

                    week++;

                    x = 0;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) throws IOException {
        Map<String, ArrayList<String>> map = getWeekScopeOfMonth("2019-2");
        System.out.println("map = " + map);
    }
}
