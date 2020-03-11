package com.example.common.util;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ShiQing_Chen  2020/3/11  17:55
 **/
public class DateUtils {

    private DateUtils() {
        //default
    }

    public static String getDateString(Date date){
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getDateStringSimple(Date date){
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    /**
     * 获取当前日期最小时间
     * @param curDate
     * @return
     */
    public static Date getMinDateTime(Date curDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String curStr = sdf.format(curDate);
        DateTime now = new DateTime(curStr);
        Date beginDate = now.millisOfDay().withMinimumValue().toDate();
        return beginDate;
    }

    /**
     * 获取当前日期最大时间
     * @param curDate
     * @return
     */
    public static Date getMaxDateTime(Date curDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String curStr = sdf.format(curDate);
        DateTime now = new DateTime(curStr);
        Date endDate = now.millisOfDay().withMaximumValue().toDate();
        return endDate;
    }

    /**
     * 增加或减少日期
     * @param date
     * @return
     */
    public static String addOrSubDay(Date date, Integer n){
        Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, n); //当前时间减去一天，即一天前的时间
        Date time = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

    /**
     * 增加或减少小时
     * @param date
     * @param n
     * @return
     */
    public static String addOrSubHour(Date date, Integer n){
        Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, n); //当前时间减去一小时，即小时前的时间
        Date time = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

    /**
     * 增加或减少小时
     * @param date
     * @param n
     * @return
     */
    public static Date addOrSubHourDate(Date date, Integer n){
        Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, n); //当前时间减去一小时，即小时前的时间
        return calendar.getTime();
    }

    /**
     * 计算时差
     * @param max
     * @param min
     * @return
     */
    public static String timeDiff(Date max, Date min){
        if (max == null || min == null){
            return "";
        }
        long s = max.getTime() - min.getTime();
        long hours = s /(3600*1000);
        long leave2 = s%(3600*1000); //计算小时数后剩余的毫秒数
        long minutes = leave2/(60*1000);//计算相差分钟数
        long leave3 = leave2%(60*1000); //计算分钟数后剩余的毫秒数
        long seconds = leave3/1000;
        String result = "";
        if (hours != 0L){
            result +=  hours + "时";
        }
        if (minutes != 0L){
            result +=  minutes + "分";
        }
        if (seconds != 0L){
            result +=  seconds + "秒";
        }

        if (hours == 0L && minutes == 0L && seconds == 0L) {
            result = "0秒";
        }
        return result;
    }

    /**
     * 计算时差(精确到分)秒级的，如果超过30秒，计为1分，没超过，计为0分
     * @param max
     * @param min
     * @return
     */
    public static String timeDiffMinute(Date max, Date min){
        if (max == null || min == null){
            return "";
        }
        long s = max.getTime() - min.getTime();
        long minutes = s/(60*1000);//计算相差分钟数
        long leaveSec = s%(60*1000); //计算分钟数后剩余的毫秒数
        long seconds = leaveSec/1000;
        String result = "";
        if ( seconds >= 30L){
            result = (minutes+1) + "分";
        } else  {
            result = minutes + "分";
        }
        return result;
    }

    public static String transTimeLongToStr(Long secNum) {
        long hours = secNum / 3600;
        long leave2 = secNum % (3600); //计算小时数后剩余的毫秒数
        long minutes = leave2 / (60);//计算相差分钟数
        long seconds = leave2 % (60); //计算分钟数后剩余的毫秒数
        String result = "";
        if (hours != 0L){
            result +=  hours + "时";
        }
        if (minutes != 0L){
            result +=  minutes + "分";
        }
        if (seconds != 0L){
            result +=  seconds + "秒";
        }

        if (hours == 0L && minutes == 0L && seconds == 0L) {
            result = "0秒";
        }
        return result;
    }
    /**
     * 计算两个日期的时间间隔
     * 返回 X年X月X天X小时X分钟
     * @param fromDate
     * @param toDate
     */
    public static String getIntervalStr(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null){
            return "";
        }
        //An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        Instant fromInstant = fromDate.toInstant();
        //An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        Instant toInstant = toDate.toInstant();
        //A time-zone ID, such as {@code Europe/Paris}.(时区)
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime fromDateTime = fromInstant.atZone(zoneId).toLocalDateTime();
        LocalDateTime toDateTime = toInstant.atZone(zoneId).toLocalDateTime();
        LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);
        long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears(years);
        long months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths(months);
        long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(days);
        long hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours(hours);
        long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes(minutes);
        long seconds = tempDateTime.until(toDateTime, ChronoUnit.SECONDS);
        if (years != 0L) {
            return years + "年" + months + "个月" + days + "天" + hours + "小时" + minutes + "分钟";
        } else if (months != 0L) {
            return months + "个月" + days + "天" + hours + "小时" + minutes + "分钟";
        } else if (days != 0L) {
            return days + "天" + hours + "小时" + minutes + "分钟";
        } else if (hours != 0L) {
            return hours + "小时" + minutes + "分钟";
        } else if (minutes != 0L) {
            return minutes + "分钟" + seconds + "秒";
        } else if (seconds != 0L) {
            return seconds + "秒";
        } else {
            return "";
        }
    }

    public static Date StringToDate(String dateStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
        try {
            Date date = simpleDateFormat.parse(dateStr);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

}
