package com.jd.core.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * The type Date utils.
 */
public class DateUtils {

    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 中文简写 如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd日";
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_INT_MINITE = "yyyyMMddHHmmss";
    /**
     * 日期
     */
    public static String FORMAT_INT_DATE = "yyyyMMdd";

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    public static Date getCurrent(){
        Calendar c = Calendar.getInstance() ;
        return c.getTime() ;
    }

    /**
     * 根据预设格式返回当前日期
     *
     * @return
     */
    public static String getNow() {
        return format(new Date());
    }

    /**
     * 根据用户格式返回当前日期
     *
     * @param format
     * @return
     */
    public static String getNow(String format) {
        return format(new Date(), format);
    }

    /**
     * 使用预设格式格式化日期
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date
     *            日期
     * @param pattern
     *            日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate
     *            日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date
     *            日期
     * @param n
     *            要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date
     *            日期
     * @param n
     *            要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     *
     * @param date
     *            日期
     * @return
     */
    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date
     *            日期字符串
     * @return
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date
     *            日期字符串
     * @param format
     *            日期格式
     * @return
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }
    /**
     * 获取某天开始时间
     * @param date
     * @return
     */
    public static Date getDayBegin(Date date){
        Calendar calendar = Calendar.getInstance() ;
        calendar.setTime(date) ;
        calendar.set(Calendar.HOUR_OF_DAY, 0) ;
        calendar.set(Calendar.MINUTE, 0) ;
        calendar.set(Calendar.SECOND, 0) ;
        return calendar.getTime() ;
    }
    /**
     * 获取某天结束时间
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date){
        Calendar calendar = Calendar.getInstance() ;
        date = getDayBegin(date) ;
        calendar.setTime(date) ;
        calendar.add(Calendar.DATE, 1) ;
        calendar.add(Calendar.SECOND, -1) ;
        return calendar.getTime() ;
    }

    /**
     * 取得指定时间间隔后的系统时间
     * 示例：
     * getDifferentTime( 1, 2, 3)
     * 输出1小时2分3秒后的系统时间
     * getDifferentTime( -24, 0, 0)
     * 输出1天前的系统日期
     *
     * @param hour
     *            小时
     * @param minute
     *            分钟
     * @param second
     *            秒
     * @return String
     */
    public static Date getDifferentTime(int day, int hour , int minute , int second ) {
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        calendar.add(Calendar.HOUR, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 补位
     * 如果num位数小于figure自定的位数，则在左边补0；如果大于，则不变
     * 例如：
     * figure = 4 时，num = 12，返回0012；num = 1100时，返回1100； num = 11001时，返回11001
     *
     * @param num
     * @param figure
     * @return
     */
    public static String fillGap(int num ,int figure){
        NumberFormat formatter   =   NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(figure);
        formatter.setGroupingUsed(false);
        return formatter.format(num);
    }

    /**
     * 获取两个时间的分钟差
     *
     * Get minutes between.
     *
     * @param beginDate the begin date
     * @param endDate the end date
     * @return the int
     */
    public static int getMinutesBetween(Date beginDate,Date endDate){
        if(null == beginDate || null == endDate){
            return 0;
        }
        return Minutes.minutesBetween(new DateTime(beginDate), new DateTime(endDate)).getMinutes();
    }

    /**
     * 获取两个时间的秒数差
     * <p>
     * Get seconds between.
     *
     * @param beginDate
     * @param endDate
     * @return the int
     */
    public static int getSecondsBetween(Date beginDate, Date endDate) {
        if (null == beginDate || null == endDate) {
            return 0;
        }

        return Seconds.secondsBetween(new DateTime(beginDate), new DateTime(endDate)).getSeconds();
    }

    /**
     * 获取两个时间的毫秒差
     * <p>
     * Get seconds between.
     *
     * @param beginDate
     * @param endDate
     * @return the int
     */
    public static long getMillSecondsBetween(DateTime beginDate, DateTime endDate) {
        if (null == beginDate || null == endDate) {
            return 0;
        }

        return beginDate.getMillis() - endDate.getMillis();
    }

    /**
     * 比较2个日期是否是同一天
     */
    public static boolean isSameDay(Date sourceDate, Date targetDate) {
        return Days.daysBetween(new DateTime(sourceDate), new DateTime(targetDate)).getDays() == 0;
    }

    /**
     * 判断指定日期是否是当天
     */
    public static boolean isCurrentDay(Date date) {
        return Days.daysBetween(DateTime.now(), new DateTime(date)).getDays() == 0;
    }

    /**
     *
     * @param date 日期
     * @return
     */
    public static long getMillis(Date date){
        return new DateTime(date).getMillis();
    }
}
