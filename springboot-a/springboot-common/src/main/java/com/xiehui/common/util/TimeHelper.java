package com.xiehui.common.util;

import org.apache.commons.lang3.StringUtils;

import com.xiehui.common.core.exception.CustomException;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 时间工具类
 *
 * @author xiehui
 */
public class TimeHelper {

    /** 线程相关 */
    /**
     * 月份格式 (yyyyMM)
     */
    private final static ThreadLocal<DateFormat> MONTH_FORMAT = new ThreadLocal<DateFormat>();
    /**
     * 月份格式 (yyyy-MM)
     */
    private final static ThreadLocal<DateFormat> $MONTH_FORMAT = new ThreadLocal<DateFormat>();
    /**
     * 日期格式(yyyy-MM-dd)
     */
    private static final ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<DateFormat>();
    /**
     * 日期格式(yyyy年-MM月-dd日)
     */
    private static final ThreadLocal<DateFormat> CDATE_FORMAT = new ThreadLocal<DateFormat>();
    /**
     * 时间格式 (HH:mm:ss)
     */
    private final static ThreadLocal<DateFormat> TIME_FORMAT = new ThreadLocal<DateFormat>();
    /**
     * 时间戳格式 (yyyy-MM-dd HH:mm:ss)
     */
    private final static ThreadLocal<DateFormat> TIMESTAMP_FORMAT = new ThreadLocal<DateFormat>();

    /** 常量相关 */
    /**
     * 开始月份
     */
    private final static String BEGIN_MONTH = "201605";
    /**
     * 开始日期
     */
    private final static String BEGIN_DATE = "2016-05-01";
    /**
     * 最大月份数量
     */
    private final static int MAX_MONTH_COUNT = 6;
    /**
     * 最大日期数量
     */
    private final static int MAX_DAY_COUNT = 30;

    /**
     * 构造函数
     */
    private TimeHelper() {}

    /**
     * 获取日期数量
     *
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return 日期数量
     */
    public static long getDateCount(Date beginDate, Date endDate) {
        // 判断空值
        if (beginDate == null || endDate == null) {
            return 0;
        }

        // 计算天数
        long beginTime = beginDate.getTime();
        long endTime = endDate.getTime();
        return (endTime - beginTime) / 86400000 + 1;
    }

    /**
     * 获取时间的秒数
     *
     * @param time 时间(HH:mm:ss)
     * @return 秒
     */
    public static Integer getSecond(String time) {
        // 检查空值
        if (time == null) {
            return null;
        }

        String[] timeArray = time.split(":");
        int hour = Integer.parseInt(timeArray[0]);
        int min = Integer.parseInt(timeArray[1]);
        int second = Integer.parseInt(timeArray[2]);

        // 返回
        return hour * 3600 + min * 60 + second;
    }

    /**
     * 判断是否是周末
     *
     * @param date 时间
     * @return 是否周末
     */
    public static boolean isWeek(Date date) {
        // 判断非空
        if (Objects.isNull(date)) {
            date = new Date();
        }

        // 判断周末
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int num = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (num == 0) {
            num = 7;
        }
        if (num == 6 || num == 7) {
            return true;
        }
        return false;
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
     * 计算指定日期的最后一天
     *
     * @param date 指定日期
     * @return 最后一天
     */
    public static Date getLastDayOfMonth(Date date) {
        // 初始化
        Calendar cale = Calendar.getInstance();

        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);

        // 返回
        return cale.getTime();
    }

    public static Date strToDateTime(String str) throws CustomException {
        Date date = null;
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            throw new CustomException("时间格式错误", e);
        }
        return date;
    }

    public static Date strToDateYMD(String str) throws CustomException {
        Date date = null;
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            throw new CustomException("时间格式错误", e);
        }

        return date;
    }

    public static Date strToDate(String str) throws CustomException {
        Date date = null;
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            throw new CustomException("时间格式错误", e);
        }
        return date;
    }

    public static String dateToString(Date date) throws CustomException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        String formatStr = formatter.format(date);
        return formatStr;
    }

    /**
     * 判断并转换为时间戳,若为空则获取当前时间的time
     *
     * @param currentTime 给定time
     * @return <code>java.sql.Timestamp</code>
     * @throws CustomException 嘻牛异常
     */
    public static Timestamp escapeLastHourTime(String currentTime) throws CustomException {
        Timestamp currentTimeStamp = null;
        try {
            currentTimeStamp =
                StringUtils.isEmpty(currentTime) ? TimeHelper.getTimestamp() : TimeHelper.getTimestamp(currentTime);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), e);
        }
        return currentTimeStamp;
    }

    /**
     * 获取月份格式 (yyyyMM)
     *
     * @return 月份格式
     */
    private static DateFormat getMonthFormat() {
        DateFormat dateFormat = MONTH_FORMAT.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyyMM");
            MONTH_FORMAT.set(dateFormat);
        }
        return dateFormat;
    }

    /**
     * 获取月份格式 (yyyy-MM)
     *
     * @return 月份格式
     */
    private static DateFormat get$MonthFormat() {
        DateFormat dateFormat = $MONTH_FORMAT.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM");
            $MONTH_FORMAT.set(dateFormat);
        }
        return dateFormat;
    }

    /**
     * 获取日期格式(yyyy-MM-dd)
     *
     * @return 日期格式
     */
    private static DateFormat getDateFormat() {
        DateFormat dateFormat = DATE_FORMAT.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DATE_FORMAT.set(dateFormat);
        }
        return dateFormat;
    }

    /**
     * 获取日期格式(yyyy年-MM月-dd日)
     *
     * @return 日期格式
     */
    private static DateFormat getCDateFormat() {
        DateFormat dateFormat = CDATE_FORMAT.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            CDATE_FORMAT.set(dateFormat);
        }
        return dateFormat;
    }

    /**
     * 获取时间格式 (HH:mm:ss)
     *
     * @return 时间格式
     */
    private static DateFormat getTimeFormat() {
        DateFormat dateFormat = TIME_FORMAT.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("HH:mm:ss");
            TIME_FORMAT.set(dateFormat);
        }
        return dateFormat;
    }

    /**
     * 获取时间戳格式 (yyyy-MM-dd HH:mm:ss)
     *
     * @return 时间戳格式
     */
    private static DateFormat getTimestampFormat() {
        DateFormat dateFormat = TIMESTAMP_FORMAT.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            TIMESTAMP_FORMAT.set(dateFormat);
        }
        return dateFormat;
    }

    /**
     * 获取时间
     *
     * @param time 指定时间
     * @return 时间字符串
     */
    public static String getTime(Time time) {
        // 检查空值
        if (time == null) {
            return null;
        }

        // 转化时间
        return getTimeFormat().format(time);
    }

    /**
     * 获取时间
     *
     * @return 时间
     */
    public static Time getTime() {
        return new Time(new Date().getTime());
    }

    /**
     * 获取时间
     *
     * @param time 时间字符串
     * @return 时间
     * @throws Exception 嘻牛异常
     */
    public static Time getTime(String time) throws Exception {
        // 检查空值
        if (time == null) {
            return null;
        }

        // 转化时间
        try {
            return new Time(getTimeFormat().parse(time).getTime());
        } catch (ParseException e) {
            throw new Exception("时间(" + time + ")格式异常");
        }
    }

    /**
     * 获取时间戳
     *
     * @param timestamp 指定时间戳
     * @return 时间戳字符串
     */
    public static String getTimestamp(Timestamp timestamp) {
        // 检查空值
        if (timestamp == null) {
            return null;
        }

        // 转化时间
        return getTimestampFormat().format(new Date(timestamp.getTime()));
    }

    /**
     * 获取时间戳
     *
     * @return 时间戳
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * 获取时间戳
     *
     * @param timestamp 时间戳字符串
     * @return 时间戳
     * @throws CustomException 嘻牛异常
     */
    public static Timestamp getTimestamp(String timestamp) throws CustomException {
        // 检查空值
        if (timestamp == null) {
            return null;
        }

        // 转化时间
        try {
            return new Timestamp(getTimestampFormat().parse(timestamp).getTime());
        } catch (ParseException e) {
            throw new CustomException("时间戳(" + timestamp + ")格式异常");
        }
    }

    /**
     * 获取上十分钟开始时间
     *
     * @param date 指定日期
     * @return 开始时间戳
     */
    public static Timestamp getBeginTimeOfLastTenMinutes(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.MINUTE, (minute / 10) * 10);
        calendar.add(Calendar.MINUTE, -10);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取上十分钟结束时间
     *
     * @param date 指定日期
     * @return 结束时间戳
     */
    public static Timestamp getEndTimeOfLastTenMinutes(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.MINUTE, (minute / 10) * 10);
        calendar.add(Calendar.MINUTE, -1);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取本十分钟开始时间
     *
     * @param date 指定日期
     * @return 开始时间戳
     */
    public static Timestamp getBeginTimeOfTenMinutes(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.MINUTE, (minute / 10) * 10);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取时间戳
     *
     * @param date
     * @param minute
     * @return
     */
    public static Timestamp getTimestamp(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.MINUTE, minute);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取日期开始时间
     *
     * @param date 指定日期
     * @return 开始时间戳
     */
    public static Timestamp getBeginTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取日期结束时间
     *
     * @param date 指定日期
     * @return 结束时间戳
     */
    public static Timestamp getEndTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取小时开始时间
     *
     * @param date 指定日期
     * @return 开始时间戳
     */
    public static Timestamp getBeginTimeOfHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取上一小时开始时间
     *
     * @param date 指定日期
     * @return 开始时间戳
     */
    public static Timestamp getBeginTimeOfLastHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取上两个小时的开始时间
     *
     * @param date 指定日期
     * @return 开始时间戳
     */
    public static Timestamp getBeginTimeOfLastTwoHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.HOUR_OF_DAY, -2);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取上一小时结束时间
     *
     * @param date 指定日期
     * @return 结束时间戳
     */
    public static Timestamp getEndTimeOfLastHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取上连个小时结束时间
     *
     * @param date 指定日期
     * @return 结束时间戳
     */
    public static Timestamp getEndTimeOfLastTwoHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.HOUR_OF_DAY, -2);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取上月开始日期
     *
     * @return 开始日期
     */
    public static java.sql.Date getBeginDateOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取指定月份开始日期
     *
     * @return 开始日期
     */
    public static java.sql.Date getBeginDateOfYearMonth(int year, short month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取上月结束日期
     *
     * @return 结束日期
     */
    public static java.sql.Date getEndDateOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取指定月份结束日期
     *
     * @return 结束日期
     */
    public static java.sql.Date getEndDateOfYearMonth(int year, short month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取上周开始日期
     *
     * @return 开始日期(周一)
     */
    public static java.sql.Date getBeginDateOfLastWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        calendar.add(Calendar.DATE, -6 - dayOfWeek);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取上周结束日期
     *
     * @return 结束日期(周日)
     */
    public static java.sql.Date getEndDateOfLastWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        calendar.add(Calendar.DATE, -dayOfWeek);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取本周开始日期
     *
     * @param date 指定日期
     * @return 开始日期(周一)
     */
    public static java.sql.Date getBeginDateOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int dayDateOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayDateOfWeek == 0) {
            dayDateOfWeek = 7;
        }
        calendar.add(Calendar.DATE, 1 - dayDateOfWeek);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取本周结束日期
     *
     * @param date 指定日期
     * @return 结束日期(周日)
     */
    public static java.sql.Date getEndDateOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int dayDateOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayDateOfWeek == 0) {
            dayDateOfWeek = 7;
        }
        calendar.add(Calendar.DATE, 7 - dayDateOfWeek);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取上年本周开始日期
     *
     * @param date 指定日期
     * @return 开始日期(周一)
     */
    public static java.sql.Date getBeginDateOfLastYearWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.YEAR, -1);
        int dayDateOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayDateOfWeek == 0) {
            dayDateOfWeek = 7;
        }
        calendar.add(Calendar.DATE, 1 - dayDateOfWeek);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取日期
     *
     * @param date 指定日期
     * @return 日期字符串
     */
    public static String getDate(Date date) {
        // 检查空值
        if (date == null) {
            return null;
        }

        // 转化日期
        return getDateFormat().format(date);
    }

    /**
     * 获取日期
     *
     * @param date 指定日期
     * @return 日期字符串
     */
    public static String getCdate(Date date) {
        // 检查空值
        if (date == null) {
            return null;
        }

        // 转化日期
        return getCDateFormat().format(date);
    }

    /**
     * 获取日期
     *
     * @return 日期
     */
    public static java.sql.Date getDate() {
        return new java.sql.Date(new Date().getTime());
    }

    /**
     * 获取日期
     *
     * @param timestamp 时间戳
     * @return 日期
     */
    public static java.sql.Date getDate(Timestamp timestamp) {
        if (timestamp == null) {
            return new java.sql.Date(new Date().getTime());
        }
        return new java.sql.Date(timestamp.getTime());
    }

    /**
     * 获取日期
     *
     * @param date 日期字符串
     * @return 日期
     * @throws CustomException 嘻牛异常
     */
    public static java.sql.Date getDate(String date) throws CustomException {
        // 检查空值
        if (date == null) {
            return null;
        }

        // 转化日期
        try {
            return new java.sql.Date(getDateFormat().parse(date).getTime());
        } catch (ParseException e) {
            throw new CustomException("日期(" + date + ")格式异常");
        }
    }

    /**
     * 获取昨日日期
     *
     * @return 昨日日期
     */
    public static java.sql.Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取昨日日期
     *
     * @param date 指定日期
     * @return 昨日日期
     */
    public static java.sql.Date getYesterday(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.DATE, -1);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取指定年后日期
     *
     * @param date 指定日期
     * @return 指定年数后日期
     */
    public static java.sql.Date getYearDate(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.YEAR, year);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取指定月后日期
     *
     * @param date 指定日期
     * @return 指定月数后日期
     */
    public static java.sql.Date getMonthDate(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.MONTH, month);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取上周日期
     *
     * @param date 指定日期
     * @return 上周日期
     */
    public static java.sql.Date getLastweekday(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.DATE, -7);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取年份
     *
     * @param date 指定日期
     * @return 年份
     */
    public static short getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        return (short)(calendar.get(Calendar.YEAR));
    }

    /**
     * 获取上年
     *
     * @return 上年
     */
    public static short getLastYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        return (short)(calendar.get(Calendar.YEAR));
    }

    /**
     * 获取月份
     *
     * @param date 指定日期
     * @return 月份(从1开始)
     */
    public static short getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        return (short)(calendar.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取天
     *
     * @param date 指定日期
     * @return 天(从1开始)
     */
    public static short getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        return (short)(calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 获取天的小时
     *
     * @param date 指定时间
     * @return 小时(从0开始)
     */
    public static Integer getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取年月
     *
     * @param date 指定日期
     * @return 年月
     */
    public static String getYearMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        return getMonthFormat().format(date);
    }

    /**
     * 获取年月
     *
     * @param date 指定日期
     * @return 年月
     */
    public static String get$YearMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        return get$MonthFormat().format(date);
    }

    /**
     * 获取最近月份列表(逆序)
     *
     * @return 月份列表(逆序)
     */
    public static List<String> getLatestMonthList() {
        // 初始化
        List<String> monthList = new ArrayList<String>();
        DateFormat monthFormat = getMonthFormat();

        // 获取月份
        String beginMonth = BEGIN_MONTH;
        Date endDate = new Date();
        String endMonth = monthFormat.format(endDate);

        // 添加月份
        if (BEGIN_MONTH.compareTo(endMonth) <= 0) {
            monthList.add(endMonth);
            if (!beginMonth.equals(endMonth)) {
                // 初始日历
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(endDate);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                // 依次添加
                String currMonth = endMonth;
                while (!currMonth.equals(beginMonth)) {
                    // 递减月份
                    calendar.add(Calendar.MONTH, -1);
                    currMonth = monthFormat.format(calendar.getTime());

                    // 添加月份
                    if (BEGIN_MONTH.compareTo(currMonth) <= 0) {
                        monthList.add(currMonth);
                    }

                    // 最多半年
                    if (monthList.size() >= MAX_MONTH_COUNT) {
                        break;
                    }
                }
            }
        }

        // 返回列表
        return monthList;
    }

    /**
     * 获取之前月份列表(逆序)
     *
     * @param date 指定时间
     * @return 月份列表(逆序)
     */
    public static List<String> getBeforeMonthList(Date date) {
        // 初始化
        List<String> monthList = new ArrayList<String>();
        DateFormat monthFormat = getMonthFormat();

        // 检查空值
        if (date == null) {
            date = new Date();
        }

        // 获取月份
        String beginMonth = BEGIN_MONTH;
        String endMonth = monthFormat.format(date);

        // 添加月份
        if (BEGIN_MONTH.compareTo(endMonth) <= 0) {
            monthList.add(endMonth);
            if (!beginMonth.equals(endMonth)) {
                // 初始日历
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                // 依次添加
                String currMonth = endMonth;
                while (!currMonth.equals(beginMonth)) {
                    // 递减月份
                    calendar.add(Calendar.MONTH, -1);
                    currMonth = monthFormat.format(calendar.getTime());

                    // 添加月份
                    if (BEGIN_MONTH.compareTo(currMonth) <= 0) {
                        monthList.add(currMonth);
                    }

                    // 最多半年
                    if (monthList.size() >= MAX_MONTH_COUNT) {
                        break;
                    }
                }
            }
        }

        // 返回列表
        return monthList;
    }

    /**
     * 获取时期月份列表(逆序)
     *
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return 月份列表(逆序)
     */
    public static List<String> getPeriodMonthList(Date beginDate, Date endDate) {
        // 初始化
        List<String> monthList = new ArrayList<String>();
        DateFormat monthFormat = getMonthFormat();

        // 检查日期
        if (endDate == null) {
            endDate = new Date();
        }
        if (beginDate == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            calendar.add(Calendar.MONTH, -MAX_MONTH_COUNT);
            beginDate = calendar.getTime();
        }

        // 交换大小
        if (beginDate.compareTo(endDate) > 0) {
            Date tempDate = beginDate;
            beginDate = endDate;
            endDate = tempDate;
        }

        // 获取月份
        String beginMonth = monthFormat.format(beginDate);
        String endMonth = monthFormat.format(endDate);

        // 添加月份
        if (BEGIN_MONTH.compareTo(endMonth) <= 0) {
            monthList.add(endMonth);
            if (!beginMonth.equals(endMonth)) {
                // 初始日历
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(endDate);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                // 依次添加
                String currMonth = endMonth;
                while (!currMonth.equals(beginMonth)) {
                    // 递减月份
                    calendar.add(Calendar.MONTH, -1);
                    currMonth = monthFormat.format(calendar.getTime());

                    // 添加月份
                    if (BEGIN_MONTH.compareTo(currMonth) <= 0) {
                        monthList.add(currMonth);
                    }
                }
            }
        }

        // 返回列表
        return monthList;
    }

    /**
     * 获取未来月份列表
     *
     * @return 月份列表
     */
    public static List<String> getFutureMonthList(int day) {
        // 初始化
        List<String> monthList = new ArrayList<String>();
        DateFormat monthFormat = getMonthFormat();

        // 获取当前月份
        Calendar calendar = Calendar.getInstance();
        String todayMonth = monthFormat.format(calendar.getTime());
        monthList.add(todayMonth);

        // 获取未来月份
        calendar.add(Calendar.DAY_OF_MONTH, day);
        String tomorrowMonth = monthFormat.format(calendar.getTime());
        if (tomorrowMonth != null && !tomorrowMonth.equals(todayMonth)) {
            monthList.add(tomorrowMonth);
        }

        // 返回列表
        return monthList;
    }

    /**
     * 获取时期日期列表(逆序)
     *
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param isSimple 是否简单
     * @return 日期列表(逆序)
     */
    public static List<String> getPeriodDateList(Date beginDate, Date endDate, boolean isSimple) {
        // 初始化
        List<String> dateList = new ArrayList<String>();
        DateFormat dateFormat = getDateFormat();

        // 检查日期
        if (endDate == null) {
            endDate = new Date();
        }
        if (beginDate == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            calendar.add(Calendar.DAY_OF_MONTH, -MAX_DAY_COUNT);
            beginDate = calendar.getTime();
        }

        // 交换大小
        if (beginDate.compareTo(endDate) > 0) {
            Date tempDate = beginDate;
            beginDate = endDate;
            endDate = tempDate;
        }

        // 获取日期
        String $beginDate = dateFormat.format(beginDate);
        String $endDate = dateFormat.format(endDate);

        // 添加日期
        if (BEGIN_DATE.compareTo($endDate) <= 0) {
            dateList.add(isSimple ? $endDate.replace("-", "") : $endDate);
            if (!$beginDate.equals($endDate)) {
                // 初始日历
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(endDate);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                // 依次添加
                String currDate = $endDate;
                while (!currDate.equals($beginDate)) {
                    // 递减日期
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    currDate = dateFormat.format(calendar.getTime());

                    // 添加日期
                    if (BEGIN_DATE.compareTo(currDate) <= 0) {
                        dateList.add(isSimple ? currDate.replace("-", "") : currDate);
                    }
                }
            }
        }

        // 返回列表
        return dateList;
    }

    /**
     * 获取星期一列表
     *
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return 星期一列表
     */
    public static List<String> getMondayList(Date beginDate, Date endDate) {
        // 初始化
        List<String> dateList = new ArrayList<String>();
        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        DateFormat dateFormat = getDateFormat();

        // 设置日期
        // 设置日期: 开始日期
        if (beginDate != null) {
            beginCalendar.setTime(beginDate);
        }
        // 设置日期: 结束日期
        if (endDate != null) {
            endCalendar.setTime(endDate);
        }

        // 设置周一
        // 设置周一: 开始日期
        int dayOfWeek = beginCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        beginCalendar.add(Calendar.DATE, 1 - dayOfWeek);
        // 设置周一: 结束日期
        dayOfWeek = endCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        endCalendar.add(Calendar.DATE, 1 - dayOfWeek);

        // 交换日期
        if (beginCalendar.after(endCalendar)) {
            Calendar tmpCalendar = beginCalendar;
            beginCalendar = endCalendar;
            endCalendar = tmpCalendar;
        }

        // 依次遍历
        for (; beginCalendar.compareTo(endCalendar) <= 0; beginCalendar.add(Calendar.DAY_OF_YEAR, 7)) {
            dateList.add(dateFormat.format(beginCalendar.getTime()));
        }

        // 返回数据
        return dateList;
    }

    /**
     * 获取月份列表
     *
     * @param beginMonth 开始月份
     * @param endMonth 结束月份
     * @return 月份列表
     * @throws Exception 嘻牛异常
     */
    public static List<String> getMonthList(String beginMonth, String endMonth) throws Exception {
        // 初始化
        List<String> monthList = new ArrayList<String>();
        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        DateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

        // 设置日期
        // 设置日期: 开始月份
        if (beginMonth != null) {
            try {
                beginCalendar.setTime(monthFormat.parse(beginMonth));
            } catch (ParseException e) {
                throw new Exception("解析月份异常");
            }
        }
        // 设置日期: 结束月份
        if (endMonth != null) {
            try {
                endCalendar.setTime(monthFormat.parse(endMonth));
            } catch (ParseException e) {
                throw new Exception("解析月份异常");
            }
        }

        // 交换日期
        if (beginCalendar.after(endCalendar)) {
            Calendar tmpCalendar = beginCalendar;
            beginCalendar = endCalendar;
            endCalendar = tmpCalendar;
        }

        // 依次遍历
        for (; beginCalendar.compareTo(endCalendar) <= 0; beginCalendar.add(Calendar.MONTH, 1)) {
            monthList.add(monthFormat.format(beginCalendar.getTime()));
        }

        // 返回数据
        return monthList;
    }

    /**
     * 转换时间格式
     *
     * @param str 时间(yyyyMMdd)
     * @return 事假(yyyy - MM - dd)
     * @throws Exception 爱驴异常
     */
    public static String formatDate(String str) throws Exception {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String sfstr = "";
        try {
            sfstr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            throw new Exception("解析时间异常");
        }
        return sfstr;
    }

    /**
     * 获取指定天数后日期
     *
     * @param date 指定日期
     * @param day 指定天数
     * @return 指定天数后日期
     */
    public static java.sql.Date getDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.DATE, day);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取指定天数后日期
     *
     * @param date 指定日期
     * @return 指定天数后日期
     */
    public static java.sql.Date getDateByMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.MONTH, month);
        return new java.sql.Date(calendar.getTimeInMillis());
    }

    /**
     * 获取简单日期列表
     *
     * @param days 提前天数
     * @return 日期列表
     */
    public static List<String> getSimpleDateList(int days) {
        // 检查提前天数
        if (days <= 0) {
            return null;
        }

        // 设置初始日期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 依次添加日期
        List<String> dateList = new ArrayList<String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        for (int i = 0; i < days; i++) {
            calendar.add(Calendar.DATE, 1);
            dateList.add(format.format(calendar.getTime()));
        }

        // 返回日期列表
        return dateList;
    }

    /**
     * 取得季度月
     *
     * @param date
     * @return 月份数组
     */
    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, TimeHelper.getYear(date));

        int nSeason = getSeason(date);
        if (nSeason == 1) {
            // 第一季度
            c.set(Calendar.MONTH, Calendar.JANUARY);
            c.set(Calendar.DAY_OF_MONTH, 1);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            c.set(Calendar.DATE, 31);
            season[2] = c.getTime();
        } else if (nSeason == 2) {
            // 第二季度
            c.set(Calendar.MONTH, Calendar.APRIL);
            c.set(Calendar.DAY_OF_MONTH, 1);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            c.set(Calendar.DATE, 30);
            season[2] = c.getTime();
        } else if (nSeason == 3) {
            // 第三季度
            c.set(Calendar.MONTH, Calendar.JULY);
            c.set(Calendar.DAY_OF_MONTH, 1);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            c.set(Calendar.DATE, 30);
            season[2] = c.getTime();
        } else if (nSeason == 4) {
            // 第四季度
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            c.set(Calendar.DAY_OF_MONTH, 1);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            c.set(Calendar.DATE, 31);
            season[2] = c.getTime();
        }
        return season;
    }

    /**
     * 获取当前时间是第几季度
     *
     * @param date
     * @return 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     */
    public static int getSeason(Date date) {

        // 第几季度
        int season = 0;

        // 获取时间对象
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    /**
     * 获取月份第一天
     *
     * @param date 日期
     * @return 月份第一天日期
     */
    public static Date getFirstDayOfDate(Date date) {
        Date firstDayOfMonth = null;
        Calendar calendar = Calendar.getInstance();
        if (date == null) {
            calendar.setTime(TimeHelper.getDate());
        } else {
            calendar.setTime(date);
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        firstDayOfMonth = calendar.getTime();
        return firstDayOfMonth;
    }

    /**
     * 获取月份最后一天
     *
     * @param date 日期
     * @return 月份最后一天
     */
    public static Date getLastDayOfDate(Date date) {
        Date lastDayOfMonth = null;
        Calendar calendar = Calendar.getInstance();
        if (date == null) {
            calendar.setTime(getDate());
        } else {
            calendar.setTime(date);
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        lastDayOfMonth = calendar.getTime();
        return lastDayOfMonth;
    }

    /**
     * 是否有效日期
     *
     * @param date 指定日期
     * @return 是否有效
     */
    public static boolean isValidDate(Date date) {
        // 检查空值
        if (date == null) {
            return false;
        }

        // 获取字符串
        String $date = getDate(date);
        if ($date == null) {
            return false;
        }

        // 小于开始日期
        if ($date.compareTo(BEGIN_DATE) < 0) {
            return false;
        }

        // 返回日期有效
        return true;
    }
}
