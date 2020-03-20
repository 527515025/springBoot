package cn.abel.utils;

import cn.abel.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期util。
 */
public class DateTimeUtils {

    /**
     * 一分钟。
     */
    public static final int ONE_MINUTE = 60 * 1000;

    /**
     * 一小时。
     */
    public static final int ONE_HOUR = 60 * ONE_MINUTE;

    /**
     * 一天。
     */
    public static final int ONE_DAY = 24 * ONE_HOUR;


    public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final int ARG_ERROR_CODE = 400;
    private static final String ARG_ERROR = "输入的参数有误";


    /**
     * 根据给定的格式将时间转为字符串。
     *
     * @param dateTime
     * @param pattern
     * @return
     */
    public static String format(Date dateTime, String pattern) {
        if (dateTime == null || pattern == null) {
            return StringUtils.EMPTY;
        }

        return DateFormatUtils.format(dateTime, pattern);
    }

    /**
     * 将时间转为默认格式的字符串。
     *
     * @param dateTime
     * @return
     */
    public static String format(Date dateTime) {
        return format(dateTime, DEFAULT_DATETIME_PATTERN);
    }

    /**
     * 根据给定的格式获取当前日期的字符串。
     *
     * @param pattern
     * @return
     */
    public static String formatCurrent(String pattern) {
        return format(new Date(), pattern);
    }

    /**
     * 获取当前日期的字符串。
     *
     * @return
     */
    public static String formatCurrent() {
        return format(new Date(), DEFAULT_DATE_PATTERN);
    }

    /**
     * 去除时间中的时分秒毫秒，只保留日期部分。
     *
     * @param dateTime
     * @return
     */
    public static Date removeTime(Date dateTime) {
        if (dateTime == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取明天的日期。
     *
     * @return
     */
    public static Date getTomorrowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 获取昨天的日期。
     *
     * @return
     */
    public static Date getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 根据给定的格式将时间字符串转化成{@link Date}对象。
     *
     * @param dateTimeStr
     * @param pattern
     * @return
     * @throws ServiceException
     */
    public static Date parse(String dateTimeStr, String pattern) throws ServiceException {
        if (dateTimeStr == null || pattern == null) {
            return null;
        }
        try {
            return DateUtils.parseDate(dateTimeStr, pattern);
        } catch (ParseException e) {
            throw new ServiceException(ARG_ERROR_CODE, ARG_ERROR);
        }
    }

    /**
     * 将时间字符串根据默认格式转换成{@link Date}对象。
     *
     * @param dateTimeStr
     * @return
     * @throws ServiceException
     */
    public static Date parse(String dateTimeStr) throws ServiceException {
        return parse(dateTimeStr, DEFAULT_DATETIME_PATTERN);
    }

    /**
     * 获取日期中的月份。
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期中的年份。
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 增加日期。
     *
     * @param date
     * @param days
     * @return
     * @throws ServiceException
     */
    public static Date dateAdd(Date date, int days) throws ServiceException {
        if (date == null) {
            throw new ServiceException(ARG_ERROR_CODE, ARG_ERROR);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 增加月份。
     *
     * @param date
     * @param months
     * @return
     * @throws ServiceException
     */
    public static Date monthAdd(Date date, int months) throws ServiceException {
        if (date == null) {
            throw new ServiceException(ARG_ERROR_CODE, ARG_ERROR);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * 获取两个日期之间的差值。
     *
     * @param one
     * @param two
     * @return
     * @throws ServiceException
     */
    public static int dateDiff(Date one, Date two) throws ServiceException {
        if (one == null || two == null) {
            throw new ServiceException(ARG_ERROR_CODE, ARG_ERROR);
        }
        long diff = Math.abs((one.getTime() - two.getTime()) / (1000 * 3600 * 24));
        return new Long(diff).intValue();
    }


    /**
     * 计算几天前的时间
     *
     * @param date 当前时间
     * @param day  几天前
     * @return
     */
    public static Date getDateBefore(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 获取当前月第一天
     *
     * @return Date
     * @throws ParseException
     */
    public static Date getFirstAndLastOfMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstDay = LocalDate.of(today.getYear(),today.getMonth(),1);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = firstDay.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取当前周第一天
     *
     * @return Date
     * @throws ParseException
     */
    public static Date getWeekFirstDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = cal.getTime();
        return date;
    }
    
}
