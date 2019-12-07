/**
 *
 */
package com.xiehui.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 日期工具类
 */
public class DateHelper extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 常用日期 格式 pattern
     */
    private static String[] DEFAULT_DATE_FORMAT_PATTERNS = new String[] {DateFormatPattern.CommonDatePattern.YYYY_MM_DD,
        DateFormatPattern.CommonDateTimePattern.YYYY_MM_DD_HH_MM_SS, DateFormatPattern.CommonDatePattern.YYYYMMDD,
        DateFormatPattern.CommonDateTimePattern.YYYYMMDDHHMMSS};

    /**
     * 转换日期格式字符串 (yyyy-MM-dd)
     *
     * @param obj
     * @return
     */
    public static String dateStr(Object obj) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(obj);
    }

    /**
     * 转换日期格式字符串 (yyyyMMdd)
     *
     * @param obj
     * @return
     */
    public static String dateStrYMD(Object obj) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(obj);
    }

    /**
     * 转换日期格式字符串 (yyyyMMdd)
     *
     * @param obj
     * @return
     */
    public static Integer dateIntegerYMD(Object obj) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return Integer.valueOf(df.format(obj));
    }

    /**
     * 转换日期格式字符串 (yyyyMMdd)
     *
     * @param obj
     * @return
     */
    public static String dateStrYM(Object obj) {
        DateFormat df = new SimpleDateFormat("yyyyMM");
        return df.format(obj);
    }

    /**
     * 转换日期格式字符串 (yyyyMMdd)
     *
     * @param obj
     * @return
     */
    public static String dateStrStartTime(Object obj) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(obj)+ " 00:00:00";
    }

    /**
     * 转换日期格式字符串 (yyyyMMdd)
     *
     * @param obj
     * @return
     */
    public static String dateStrEndTime(Object obj) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(obj)+" 23:59:59";
    }

    /**
     * 转换日期格式字符串 (yyyy年MM月dd日 HH:mm)
     *
     * @param obj
     * @return
     */
    public static String dateStrYMDSF(Object obj) {
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return df.format(obj);
    }

    /**
     * 转换日期格式字符串 (yyyy年MM月dd日 HH:mm)
     *
     * @param obj
     * @return
     */
    public static String dateStrPay(Object obj) {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(obj);
    }

    /**
     * 转换日期格式字符串 (yyyy-MM-dd HH:mm:ss)
     *
     * @param obj
     * @return
     */
    public static String dateTimeStr(Object obj) {
        if (null == obj) {
            return "";
        }

        if ("0000-00-00 00:00:00".equals(obj.toString())) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(obj);
    }

    /**
     * 转换日期格式字符串
     *
     * @param obj
     * @param pattern
     * @return
     */
    public static String dateStr(Object obj, String pattern) {
        if (null == obj) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(obj);
    }

    /**
     * 将字符串转换成java.util.Date (yyyy-MM-dd)
     *
     * @param str (yyyy-MM-dd)
     * @return
     */
    public static Date strToDate(String str) {
        if (RegexHelper.isNotDate(str)) {
            throw new RuntimeException("str is not date");
        }
        Date date = null;
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 将字符串转换成java.util.Date (yyyyMMdd)
     *
     * @param str (yyyyMMdd)
     * @return
     */
    public static Date strToDateYMD(String str) {
        Date date = null;
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 将字符串转换成java.util.Date (yyyyMMddHHmmss)
     *
     * @param str (yyyyMMddHHmmss)
     * @return
     */
    public static Date strToDateYMDHMS(String str) {
        Date date = null;
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date YMDtoDate(String str){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setLenient(false);
        try {
            Date newDate= formatter.parse(str);
            return newDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date YMDtoDateTime(String str){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setLenient(false);
        try {
            Date newDate= formatter.parse(str);
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            return strToDateTime(formatter.format(newDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转换成java.util.Date (yyyy-MM-dd HH:mm:ss)
     *
     * @param str (yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static Date strToDateTime(String str) {
        if (RegexHelper.isNotDate(str)) {
            throw new RuntimeException("str is not datetime");
        }
        Date date = null;
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }



    public static Date getMinTime(Date dt) {
        Date dt1 = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            dt1 = sf.parse(sf.format(dt));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("date formate error ：" + dt + ".   " + e.getMessage());
        }
        return dt1;
    }

    /**
     * 计算两个日期相差的天数
     *
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public static long getDistinceDay(Date beforeDate, Date afterDate) {
        long dayCount = 0;
        dayCount = (afterDate.getTime() - beforeDate.getTime()) / (24 * 60 * 60 * 1000);
        return dayCount;
    }


    /**
     * 当天到第二天12点的秒数
     *
     * @return
     */
    public static int exipireTime() {
        Calendar curDate = Calendar.getInstance();
        Calendar tommorowDate = new GregorianCalendar(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH),
            curDate.get(Calendar.DATE) + 1, 0, 0, 0);
        long timeCap = tommorowDate.getTimeInMillis() - System.currentTimeMillis();
        return (int)timeCap / 1000;
    }

    /**
     * 返回当前时间前指定年数列表
     *
     * @param count
     * @return
     * @author
     */
    public static List<Integer> getBeforeYear(int count) {
        Calendar curDate = Calendar.getInstance();
        int nowYear = curDate.get(Calendar.YEAR);
        List<Integer> yearList = new ArrayList<Integer>();
        for (int i = nowYear; i > (nowYear - count); i--) {
            yearList.add(i);
        }
        return yearList;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static int checkTimeDis(Date begin, Date end, int distance) {
        if (begin == null) {
            // 时间未选择
            return -1;
        }

        if (end == null) {
            end = new Date();
        }

        if (getDistinceDay(begin, end) > distance) {
            return -2;
        }

        return 1;
    }

    public static Date getDayByCount(Date curDatetime, int count) {
        // 设置日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        long longTime = 24 * 60 * 60 * 1000 * count;
        try {
            Date changeDate = formatter.parse(dateStr(curDatetime));
            longTime += changeDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 根据毫秒获得日期
        Date newDate = new Date(longTime);
        // 日期格式转换为字符串
        return newDate;
    }

    // 获得昨天日期
    public static Date getYesterday(Date date) {
        date = null == date ? new Date() : date;
        return addDays(date, -1);
    }

    // 获得本周一的日期
    public static Date getWeekBegin(Date date) {
        date = null == date ? new Date() : date;

        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }
    // 获得今天开始的日期
    public static Date getDayBegin(Date date) {
        date = null == date ? new Date() : date;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return cal.getTime();
    }

    // 获得今天结束的日期
    public static Date getDayEnd(Date date) {
        date = null == date ? new Date() : date;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return cal.getTime();
    }

    // 客服部刘立丹提的 每周第一天是上周四
    public static Date getWeekBeginForLiuLiDan(Date date) {
        int delay = -3;
        if (getWeekNum(date) >= 5) {
            delay = 4;
        }
        return addDays(getWeekBegin(date), delay);
    }

    // 获得本周日的日期
    public static Date getWeekEnd(Date date) {
        date = null == date ? new Date() : date;
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(getWeekBegin(date));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return cal.getTime();
    }

    // 客服部刘立丹提的 每周第一天是本周五
    public static Date getWeekEndForLiuLiDan(Date date) {
        int delay = -3;
        if (getWeekNum(date) >= 5) {
            delay = 4;
        }
        return addDays(getWeekEnd(date), delay);
    }

    // 获得本月第一天日期
    public static Date getMonthBegin(Date date) {
        date = null == date ? new Date() : date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    // 获得本月最后一天日期
    public static Date getMonthEnd(Date date) {
        date = null == date ? new Date() : date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        return cal.getTime();
    }

    public static int getWeekNum(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int num = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (num == 0) {
            num = 7;
        }
        return num;
    }

    /**
     * 返回1时（dt1>dt2）;0是相等；-1时dt1<dt2
     *
     * @param dt1
     * @param dt2
     * @return
     */
    public static int compareDate(Date dt1, Date dt2) {
        if (dt1 == null && dt2 == null)
            return 0;
        if (dt1 == null)
            return -1;
        if (dt2 == null)
            return 1;
        if (dt1.getTime() > dt2.getTime()) {
            return 1;
        } else if (dt1.getTime() < dt2.getTime()) {
            return -1;
        } else {// 相等
            return 0;
        }
    }

    /**
     * 返回当前剩余秒数
     * 
     * @return
     */
    public static long getRemainingSeconds() {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now();
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
        long seconds =
            TimeUnit.NANOSECONDS.toSeconds(Duration.between(LocalDateTime.now(), tomorrowMidnight).toNanos());
        return seconds;
    }

    /**
     * 日期 字符串 类型 转为 Date类型<br>
     * 支持多种常用格式, 但是 注意 不支持 jdk8的日期格式
     * 
     * @param dateStr 字符串类型
     * @return Date类型
     * @throws ParseException 转换异常
     */
    public static Date tryParseDate(final String dateStr, final String... datePatterns) throws ParseException {
        return parseDateStrictly(dateStr,
            ArrayUtils.isEmpty(datePatterns) ? DEFAULT_DATE_FORMAT_PATTERNS : datePatterns);
    }

    public static void main(String[] args) {
        // System.out.println(dateStr(getWeekBegin(new Date())));
        // System.out.println(dateStr(getWeekEnd(new Date())));
        // System.out.println(dateStr(getWeekBeginForLiuLiDan(new Date())));
        // System.out.println(dateStr(getWeekEndForLiuLiDan(new Date())));
        //
        // System.out.println(dateStr(getWeekBeginForLiuLiDan(strToDate("2016-07-31"))));
        // System.out.println(dateStr(getWeekEndForLiuLiDan(strToDate("2016-07-31"))));
        //
        // System.out.println(getWeekNum(strToDate("2016-10-21")));
    }
}
