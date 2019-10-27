package com.sss.common.util;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wyy-sss
 * @date 2019-09-18 09:58
 **/
public class MyDateTimeUtils {
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 返回当前的日期
     *
     * @return
     */
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    /**
     * 返回当前日期时间
     *
     * @return
     */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 返回当前日期时间戳
     *
     * @return
     */
    public static long getCurrentLocalDateTimeStamp() {
        Instant now = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8));
        return now.toEpochMilli();
    }

    /**
     * 返回当前日期字符串 yyyyMMdd
     *
     * @return
     */
    public static String getCurrentDateStr() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    /**
     * 返回当前日期时间字符串 yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentDateTimeStr() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }


    /**
     *  自定义日期字符串和格式化格式
     * @param dateStr
     * @param pattern
     * @return java.time.LocalDate
    **/
    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }


    /**
     *  自定义日期时间字符串和格式化格式
     * @param dateTimeStr
     * @param pattern
     * @return java.time.LocalDate
     **/
    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 日期相隔天数
     *
     * @param startDateInclusive
     * @param endDateExclusive
     * @return
     */
    public static int periodDays(LocalDate startDateInclusive, LocalDate endDateExclusive) {
        return Period.between(startDateInclusive, endDateExclusive).getDays();
    }

    /**
     * 日期相隔小时
     *
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationHours(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toHours();
    }

    /**
     * 日期相隔分钟
     *
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationMinutes(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMinutes();
    }

    /**
     * 日期相隔毫秒数
     *
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationMillis(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMillis();
    }

    /**
     * 是否当天
     *
     * @param date
     * @return
     */
    public static boolean isToday(LocalDate date) {
        return getCurrentLocalDate().equals(date);
    }

    /**
     * 获取本月的第一天
     *
     * @return
     */
    public static String getFirstDayOfThisMonth() {
        return getCurrentLocalDate().with(TemporalAdjusters.firstDayOfMonth()).format(DATE_FORMATTER);
    }

    /**
     * 获取本月的最后一天
     *
     * @return
     */
    public static String getLastDayOfThisMonth() {
        return getCurrentLocalDate().with(TemporalAdjusters.lastDayOfMonth()).format(DATE_FORMATTER);
    }

    /**
     * 获取2017-01的第一个周一
     *
     * @return
     */
    public static String getFirstMonday() {
        return LocalDate.parse("2017-01-01").with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY))
                .format(DATE_FORMATTER);
    }

    /**
     * 获取当前日期的后两周
     *
     * @return
     */
    public static String getCurDateAfterWeek(Long week) {
        return getCurrentLocalDate().plus(week, ChronoUnit.WEEKS).format(DATE_FORMATTER);
    }

    /**
     * 获取当前日期的几个月后的日期
     *
     * @return
     */
    public static String getCurDateAfterMonth(Long month) {
        return getCurrentLocalDate().plus(month, ChronoUnit.MONTHS).format(DATE_FORMATTER);
    }

    /**
     * 获取当前日期的5年后的日期
     *
     * @return
     */
    public static String getCurDateAfterYear(Long year) {
        return getCurrentLocalDate().plus(year, ChronoUnit.YEARS).format(DATE_FORMATTER);
    }

    /**
     * 获取当前日期的20年后的日期
     *
     * @return
     */
    public static String getCurDateAfterTwentyYear() {
        return getCurrentLocalDate().plus(2, ChronoUnit.DECADES).format(DATE_FORMATTER);
    }

    /**
     * 字符串转时间
     *
     * @return
     */
    public static LocalDate stringToDate(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(time, formatter);
    }


    /**
     *  判断传入时间戳是否小于当前时间戳
     * @param maxDateTimes
     * @return boolean
     * @author WYY-SSS
    **/
    public static boolean comparisonNow(long maxDateTimes)
    {
        Date now = new Date();
        if (maxDateTimes < now.getTime()) {
            return true;
        }
        return false;
    }


    /**
     * 根据开始和结束的时间得到中间的天数
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return java.util.List<java.lang.String>
     * @author WYY-SSS
    **/
    public static List<String> getRangeTimeStr(String beginTime, String endTime) throws ParseException {
        List<String> list = new ArrayList<>();
        DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startDay = Calendar.getInstance();
        Calendar endDay = Calendar.getInstance();
        startDay.setTime(FORMATTER.parse(beginTime));
        endDay.setTime(FORMATTER.parse(endTime));
        if (!beginTime.equals(endTime)) {
            //现在打印中的日期
            Calendar currentPrintDay = startDay;
            while (true){
                // 日期加一
                currentPrintDay.add(Calendar.DATE, 1);
                // 日期加一后判断是否达到终了日，达到则终止打印
                if (currentPrintDay.compareTo(endDay) == 0) {
                    break;
                }
                String date = FORMATTER.format(currentPrintDay.getTime());
                list.add(date);
            }
            list.add(endTime);
        }
        return list;
    }


    /**
     *  判断字符串是否是规定日期字符串
     * @param str  字符串
     * @return boolean
     * @author WYY-SSS
    **/
    public  static boolean isDateStr(String str) {
        String format="yyyy-MM-dd";
        DateTimeFormatter ldt = DateTimeFormatter.ofPattern(format.replace("y","u"))
                .withResolverStyle(ResolverStyle.STRICT);

        boolean dateFlag = true;
        try {
            LocalDate.parse(str, ldt);
        } catch (DateTimeParseException | NullPointerException e) {
            dateFlag = false;
        }

        // 成功：true ;失败:false
        System.out.println("日期是否满足要求" + dateFlag);
        return dateFlag;
    }


   /**
    * 将时间戳转转换成特定时间格式的字符串
    * @param time  时间戳
    * @param pattern 日期格式
    * @return java.lang.String
    * @author WYY-SSS
   **/
    public static String longToStr(long time,String pattern) {
        Assert.notNull(time, "time is null");
        DateTimeFormatter ftf=null;
        if (StringUtils.isEmpty(pattern)) {
            ftf =DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }else {
            ftf = DateTimeFormatter.ofPattern(pattern);
        }
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));

    }


   /**
    * 将时间字符串转换成时间戳
    * @param strTime
    * @return long
    * @author WYY-SSS
   **/
    public  static long strToLong(String strTime){
        Assert.notNull(strTime, "time is null");
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse(strTime, ftf);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

    }

    /**
     * 获取指定日期字符串
     * @param year
     * @param month
     * @param day
     * @return java.time.LocalDate
     * @author WYY-SSS
    **/
    public static String getSpecifiedDate(int year,int month,int day){
        LocalDate localDate = LocalDateTime.of(year, month, day, 0, 0).toLocalDate();
        String format = localDate.format(DATE_FORMATTER);
        return format;
    }

    /**
     * 根据年周字符串时间得到 周开始天 和 周结束天的时间字符串
     * @param dateString
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @author WYY-SSS
     **/
    public static Map<String, String> weekStrConverToDataStr(String dateString){
        Map<String, String> map = new HashMap<>(2);
        int year = Integer.valueOf(dateString.substring(0,dateString.indexOf("-")));
        int week = Integer.valueOf(dateString.substring(dateString.indexOf("-")+1));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, 0, 1);
        //算出第一周还剩几天 +1是因为1号是1天
        int dayOfWeek = 7- calendar.get(Calendar.DAY_OF_WEEK) + 1;
        //周数减去第一周再减去要得到的周
        week = week -2;
        calendar.add(Calendar.DAY_OF_YEAR, week*7+dayOfWeek+1);
        String startTime = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, 6);
        String endTime = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        return map;
    }
}
