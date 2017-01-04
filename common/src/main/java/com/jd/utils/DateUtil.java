package com.jd.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @version V1.0
 * @Title: DateUtil.java
 * @Description: 日期辅助类
 */
public class DateUtil {

    /**
     * 返回当前时间 格式：yyyy-MM-dd hh:mm:ss
     *
     * @return String
     */
    public static String fromDateH() {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format1.format(new Date());
    }

    public static String fromDateH2() {
        DateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
        return format1.format(new Date());
    }

    public static String fromCurrentDate() {
        DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return format1.format(new Date());
    }

    public static String getCurrentMillisecond() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

    /**
     * 返回当前时间 格式：yyyy-MM-dd
     *
     * @return String
     */
    public static String fromDateY() {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(new Date());
    }

    /**
     * 返回当前时间 格式：yyyyMMdd
     *
     * @return String
     */
    public static String fromDateY2() {
        DateFormat format1 = new SimpleDateFormat("yyyyMMdd");
        return format1.format(new Date());
    }

    /**
     * @param date
     * @return String 返回类型
     * @Title: getDateString
     * @Description: 使用"yyyy-MM-dd HH:mm:ss"格式化日期
     */
    public static String getDateStringH(Date date) {
        return getDateString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getDateStringH2(Date date) {
        return getDateString(date, "yyyy/MM/dd HH:mm:ss");
    }

    /**
     * @param date
     * @return String 返回类型
     * @Title: getDateString
     * @Description: 使用"yyyy-MM-dd HH:mm:ss"格式化日期
     */
    public static String getDateStringY(Date date) {
        return getDateString(date, "yyyy-MM-dd");
    }

    /**
     * @param date   日期
     * @param format 模式
     * @return String 返回类型
     * @Title: getDateString
     * @Description: 格式化日期
     */
    public static String getDateString(Date date, String format) {
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            String dateString = formatter.format(date);
            return dateString;
        }
        return null;
    }

    /**
     * 功能描述：String 转 timestamp
     *
     * @param time
     * @return
     * @author 胡佳[hujia@xinnet.com]
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     * @since 2013-11-28
     */
    public static Timestamp getTimestamp(String time) {
        return Timestamp.valueOf(time);
    }

    /**
     * 功能描述：date 转 timestamp
     *
     * @param date
     * @return
     * @author 胡佳[hujia@xinnet.com]
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     * @since 2013-11-28
     */
    public static Timestamp getTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     *
     * @return 以yyyyMMddHHmmss为格式的当前系统时间
     */
    public static String getOrderNum() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

        Random random = new Random();
        String rand = String.valueOf(random.nextInt(100000));

        for (int j = 0; j < 5 - rand.length(); j++) {
            rand += "0";
        }

        return df.format(date) + rand;
    }

    /**
     * @return 以yyyyMMddHHmmss为格式的当前系统时间
     */
    public static String getYyyyMMddHHmmss() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(date);
    }

    /**
     * 格式化字符串
     *
     * @param timestamp
     * @return
     */
    public static String getFormatDate(Timestamp timestamp) {
        Date date = new Date(timestamp.getTime());
        String pattern = "yyyy-MM-dd";
        return getFormatDate(pattern, date);
    }

    public static String getFormatDateTime(Timestamp timestamp) {
        Date date = new Date(timestamp.getTime());
        String pattern = "yyyy-MM-dd HH:mm:ss";
        return getFormatDate(pattern, date);
    }

    public static String getFormatDate(String pattern, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static String getFormatDate(String date) {
        AssertUtil.notEmpty(date, "日期date");
        return date.substring(0, 10);
    }

    /**
     * 根据指定格式得到当前日期的字符串
     *
     * @param pattern String
     * @return String
     */
    public static String getCurrentDate(String pattern) {
        if (pattern == null || pattern.trim().equals("")) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        pattern = pattern.trim();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 以默认格式得到当前日期的字符串
     *
     * @return String
     */
    public static String getCurrentDate() {
        return getCurrentDate("yyyy-MM-dd");
    }

    /**
     * 滚动日期
     *
     * @param timestamp     被滚动的日期
     * @param calendarField 日历字段
     * @param rollAmount    滚动量
     * @return Timestamp
     */
    public static Timestamp getRollTime(Timestamp timestamp, int calendarField, int rollAmount) {
        AssertUtil.notNull(timestamp, "日期");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(timestamp.getTime()));
        cal.add(calendarField, rollAmount);
        return new Timestamp(cal.getTimeInMillis());
    }

    /**
     * 年滚动
     *
     * @param timestamp  Timestamp
     * @param rollAmount 滚动量
     * @return Timestamp
     */
    public static Timestamp getRollYear(Timestamp timestamp, int rollAmount) {
        return getRollTime(timestamp, Calendar.YEAR, rollAmount);
    }

    /**
     * 月滚动
     *
     * @param timestamp  Timestamp
     * @param rollAmount 滚动量
     * @return Timestamp
     */
    public static Timestamp getRollMonth(Timestamp timestamp, int rollAmount) {
        return getRollTime(timestamp, Calendar.MONTH, rollAmount);
    }

    /**
     * 天滚动
     *
     * @param timestamp  Timestamp
     * @param rollAmount 滚动量
     * @return Timestamp
     */
    public static Timestamp getRollDay(Timestamp timestamp, int rollAmount) {
        return getRollTime(timestamp, Calendar.DAY_OF_WEEK, rollAmount);
    }

    /**
     * 两个日期相差的天数
     *
     * @param bigTime    大日期
     * @param littleTime 小日期
     * @return int 相差的天数
     */
    public static int getIntervalDays(Timestamp bigTime, Timestamp littleTime) {
        AssertUtil.notNull(bigTime, "日期");
        AssertUtil.notNull(littleTime, "日期");

        String t1str = DateUtil.getTimestampStr(bigTime);
        String t2str = DateUtil.getTimestampStr(littleTime);
        Timestamp t1 = DateUtil.getTimestampFormat(t1str);
        Timestamp t2 = DateUtil.getTimestampFormat(t2str);

        // Calendar bigCal = Calendar.getInstance();
        // bigCal.setTimeInMillis(bigTime.getTime());
        // bigCal.set(Calendar.HOUR, 0);
        // bigCal.set(Calendar.MINUTE, 0);
        // bigCal.set(Calendar.SECOND, 0);
        // bigTime.setTime(bigCal.getTimeInMillis());
        //
        // Calendar littleCal = Calendar.getInstance();
        // littleCal.setTimeInMillis(littleTime.getTime());
        // littleCal.set(Calendar.HOUR, 0);
        // littleCal.set(Calendar.MINUTE, 0);
        // littleCal.set(Calendar.SECOND, 0);
        // littleTime.setTime(littleCal.getTimeInMillis());
        //
        // System.out.println("bigTime=" + getTimestampStr(bigTime));
        // System.out.println("littleTime=" + getTimestampStr(littleTime));

        long intervalTime = t1.getTime() - t2.getTime();
        int intervalDay = (int) (intervalTime / 1000 / 60 / 60 / 24);

        return intervalDay;
    }

    /**
     * 两个日期相差的月数(退费的情况)
     *
     * @param bigTime    大日期
     * @param littleTime 小日期
     * @return int 相差的月数
     */
    public static int getIntervalRefundMonths(Timestamp bigTime, Timestamp littleTime) {
        return getIntervalChargeMonths(bigTime, littleTime);
    }

    /**
     * 两个日期相差的月数(收费的情况)
     *
     * @param bigTime    大日期
     * @param littleTime 小日期
     * @return int 相差的月数
     */
    public static int getIntervalChargeMonths(Date bigTime, Date littleTime) {
        AssertUtil.notNull(bigTime, "日期");
        AssertUtil.notNull(littleTime, "日期");

        Calendar bigCalendar = Calendar.getInstance();
        bigCalendar.setTime(new Date(bigTime.getTime()));

        Calendar littleCalendar = Calendar.getInstance();
        littleCalendar.setTime(new Date(littleTime.getTime()));

        int intervalYear = getIntervalYears(bigTime, littleTime);
        int intervalMonth = bigCalendar.get(Calendar.MONTH) - littleCalendar.get(Calendar.MONTH);

        int n = intervalYear * 12 + intervalMonth;
        n = n < 0 ? 0 : n;

        return n;
    }

    /**
     * 两个日期相差的年数
     *
     * @param bigTime    Timestamp
     * @param littleTime Timestamp
     * @return int
     */
    public static int getIntervalYears(Date bigTime, Date littleTime) {
        AssertUtil.notNull(bigTime, "日期");
        AssertUtil.notNull(littleTime, "日期");

        Calendar bigCalendar = Calendar.getInstance();
        bigCalendar.setTime(new Date(bigTime.getTime()));

        Calendar littleCalendar = Calendar.getInstance();
        littleCalendar.setTime(new Date(littleTime.getTime()));

        return bigCalendar.get(Calendar.YEAR) - littleCalendar.get(Calendar.YEAR);
    }

    /**
     * 得到日期字符串
     *
     * @param timestamp Timestamp
     * @return String
     */
    public static String getTimestampStr(Timestamp timestamp) {
        AssertUtil.notNull(timestamp, "日期");
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date(timestamp.getTime()));
    }

    /**
     * 日期比较,t1>t2则返回true
     *
     * @param t1
     * @param t2
     * @return t1>t2则返回true
     */
    public static boolean compareDate(Timestamp t1, Timestamp t2) {
        String d1 = DateUtil.getFormatDate(t1);
        String d2 = DateUtil.getFormatDate(t2);
        return d1.compareTo(d2) > 0;
    }

    /**
     * 日期时间比较,t1>t2则返回true
     *
     * @param t1
     * @param t2
     * @return t1>t2则返回true
     */
    public static boolean compareDateTime(Timestamp t1, Timestamp t2) {
        String d1 = DateUtil.getFormatDateTime(t1);
        String d2 = DateUtil.getFormatDateTime(t2);

        return d1.compareTo(d2) > 0;
    }

    /**
     * 日期时间比较,d1>d2则返回true
     *
     * @param d1
     * @param d2
     * @return d1>d2则返回true
     */
    public static boolean compareDate(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);

        return c1.compareTo(c2) > 0;
    }

    public static Timestamp getTimestampFormat(String dateStr) {
        String year = dateStr.substring(0, 4);
        String month = dateStr.substring(5, 7);
        if (month.startsWith("0")) {
            month = month.substring(1);
        }

        String day = dateStr.substring(8, 10);
        if (day.startsWith("0")) {
            day = day.substring(1);
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return new Timestamp(cal.getTimeInMillis());
    }

    /**
     * 日期比较,t1==t2则返回true
     *
     * @param t1
     * @param t2
     * @return
     */
    public static boolean equalDate(Timestamp t1, Timestamp t2) {
        String d1 = DateUtil.getFormatDate(t1);
        String d2 = DateUtil.getFormatDate(t2);

        return d1.equals(d2);
    }

    /**
     * 得到日期字符串 1900-01-01 00:00:00
     *
     * @param timestamp Timestamp
     * @param pattern
     * @return String
     */
    public static String getFormatedTimestampStr(Timestamp timestamp, String pattern) {
        AssertUtil.notNull(timestamp, "日期不能为空");
        if (pattern == null || "".equals(pattern)) {
            pattern = "yyyy-MM-dd kk:mm";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date(timestamp.getTime()));
    }

    /**
     * @param year  年
     * @param month 月 @ return Timestamp 增加流量 返回某一个月份最后一天;
     * @author gonghailiang add by gonghailiang 2010-6-5 增加流量 返回某一个月份最后一天
     */
    public static Timestamp getMonthLastDay(String year, String month) {
        Calendar cal = Calendar.getInstance();
        // 年
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        // 月，因为Calendar里的月是从0开始，所以要-1
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        // 日，设为一号
        cal.set(Calendar.DATE, 1);
        // 月份加一，得到下个月的一号
        cal.add(Calendar.MONTH, 1);
        // 下一个月减一为本月最后一天
        cal.add(Calendar.DATE, -1);
        // System.out.println(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));//);

        return Timestamp.valueOf(year + "-" + month + "-" + String.valueOf(cal.get(Calendar.DAY_OF_MONTH))
                + " 23:59:59");
    }

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parseDate(String str) {
        try {
            return df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Date parseDateyyyyMMdd(String str) {
        try {
            return df2.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String formatDateTime(Date d) {
        return d == null ? null : df.format(d);
    }

    public static String formatDateTime(Date d, SimpleDateFormat format) {
        return format.format(d);
    }

    public static String formatDate(Date d) {
        return df2.format(d);
    }

    /**
     * 增加某一日期后的几天，days参数可为负数
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDay(Date date, int days) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, days);
        return c.getTime();
    }

    /**
     * 获取日期的月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH);
    }

    /**
     * 获取日期的天
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DATE);
    }

    /**
     * 获取日期的年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * add by liyingpeng 2013-12-31
     *
     * @param t1 开始时间
     * @param t2 结束时间 @ return String 返回结束时间和开始时间的相差秒数
     */
    public static String getIntervalSecond(Date t1, Date t2) {
        long d = (t2.getTime() - t1.getTime());
        return d + "";
    }

    /**
     * add by liyingpeng 2013-12-31
     *
     * @param t1 开始时间
     * @param t2 结束时间 @ return String 返回结束时间和开始时间的相差秒数
     */
    public static long getIntervalTime(Date t1, Date t2) {
        long d = t2.getTime() - t1.getTime();
        return d;
    }

    /**
     * 获取下一天 默认 格式 yyyy-MM-dd 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getNextDayYYYYMMdd(Date date) {
        date = rollDay(date, 1);
        return getDateYYYYMMdd(dateStr2(date));
    }

    public static Date getNextDayYYYYMMddByDay(Date date, int day) {
        date = rollDay(date, day);
        return getDateYYYYMMdd(dateStr2(date));
    }

    public static Date getNextDayYYYYMMddByMonth(Date date, int mon) {
        date = rollMon(date, mon);
        return getDateYYYYMMdd(dateStr2(date));
    }

    public static Date getNextDayYYYYMMddHHMMSSByMonth(Date date, int mon) {
        date = rollMon(date, mon);
        return getDateYYYYMMddHHMMSS(dateStr3(date));
    }

    public static Date rollDay(Date d, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public static String dateStryyyyMMdd(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        return str;
    }

    public static String dateStr2(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    public static String dateStr3(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    public static Date getDateYYYYMMdd(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;

    }

    /**
     * 字符串yyyy/MM/dd HH:mm:ss格式时间转date
     *
     * @param str
     * @return
     */
    public static Date getDateYMD(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;

    }

    public static Date rollMon(Date d, int mon) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MONTH, mon);
        return cal.getTime();
    }

    public static Date getDateYYYYMMddHHMMSS(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;
    }

    /**
     * add by luozf
     *
     * @param one 开始时间
     * @param two 结束时间 @ return String 返回结束时间和开始时间的 天 /小时/分/秒
     */
    public static String getDistanceTime(Date one, Date two) throws ParseException {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;

        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }

    /**
     * add by lidong
     *
     * @param date
     * @return 当前星期一
     */
    public static String getMonday(Date date) {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本周第一天
        return getDateStringY(cal.getTime());
    }

    /**
     * add by lidong
     *
     * @param date
     * @return 当月第一天
     */
    public static String getEarlierMonth(Date date) {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return getDateStringY(cal.getTime());
    }

    /**
     * add by lidong
     *
     * @param date
     * @return 当年第一天
     */
    public static String getYearOne(Date date) {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.set(Calendar.DAY_OF_YEAR, 1);// //本年第一天
        return getDateStringY(cal.getTime());
    }

    /**
     * 返回格式为(yyyy-MM-dd HH:mm:ss)，秒为0的时间
     *
     * @param date
     * @return
     */
    public static Date getZeroSecondDate(Date date) {
        String str = getDateStringH(date);
        str = str.substring(0, str.length() - 3) + ":00";
        return parseDate(str);
    }

    /**
     * 返回格式为(yyyy-MM-dd HH:mm:ss)，秒为0的时间
     *
     * @param str
     * @return
     */
    public static Date getZeroSecondDate(String str) {
        return getZeroSecondDate(parseDate(str));
    }

    /**
     * 测试方法
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        System.out.println(getMonthLastDay(new Date()));
        System.out.println(getMonthLastDay(parseDate("2015-01-26 00:12:56")));
        System.out.println(getMonthLastDay(parseDate("2015-02-05 03:54:48")));
        System.out.println(getMonthLastDay(parseDate("2015-04-15 12:19:36")));
        System.out.println(getMonthLastDay(parseDate("2015-07-15 19:44:32")));
        Date date = parseDate("2015-01-26 00:12:56");
        System.out.println(DateUtil.getMonthLastDay(date).equals(date));
        date = parseDate("2015-02-28 15:29:33");
        System.out.println(DateUtil.getMonthLastDay(date).equals(date));
        System.out.println(getTitel(new Date(), "【双十一】"));
        // System.out.println(getIntervalChargeMonths(parseDate("2015-09-30 00:12:56"),
        // new Date()));
        // System.out.println(getEarlierMonth(new Date()));
        // Calendar cal = Calendar.getInstance();// 获取当前日期
        // cal.add(Calendar.MONTH, -1);
        // System.out.println(getDateStringY(cal.getTime()));
        // Calendar cal2 = Calendar.getInstance();// 获取当前日期
        // cal2.set(Calendar.DAY_OF_MONTH, 1);
        // System.out.println(getDateStringY(cal2.getTime()));
        // Timestamp t1 = new Timestamp(System.currentTimeMillis());
        // //
        // String dateStr = "2012-01-28 15:24:24";
        // Timestamp t2 = DateUtil.getTimestamp(dateStr);
        // //
        // // System.out.println(DateUtil.getIntervalDays(t1, t2));
        //
        // Timestamp currentTime = new
        // Timestamp(Calendar.getInstance().getTimeInMillis());
        // System.out.println(DateUtil.getTimestampStr(currentTime));
        // System.out.println(DateUtil.getTimestampStr(t1));
        //
        // System.out.println(Calendar.getInstance().getTimeInMillis());
        // System.out.println(System.currentTimeMillis());
        // System.out.println(getIntervalSecond(new Date(),
        // DateUtil.parseDate("2015-05-11 17:43:00")));
        // System.out.println("===========" + 2 * 60 * 60 * 1000);
        // System.out.println("===========" + 7211 / 60 / 60);
    }

    public static String fromNowStartTime() {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(new Date()) + " 00:00:00";
    }

    public static String fromNowEndTime() {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(new Date()) + " 23:59:59";
    }

    /**
     * 获取传入时间天的截止时间点
     *
     * @param d
     * @return
     */
    public static Date formEndTime(Date d) {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return getDateYYYYMMddHHMMSS(format1.format(d) + " 23:59:59");

    }

    /**
     * 获取传入时间天的开始时间点
     *
     * @param d
     * @return
     */
    public static Date formStartTime(Date d) {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return getDateYYYYMMddHHMMSS(format1.format(d) + " 00:00:00");

    }

    /**
     * 获取传入时间月份最后一天
     *
     * @param date
     * @return
     */
    public static Date getMonthLastDay(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }

    /**
     * 获取传入时间月份的第一天
     *
     * @param date
     * @return
     */
    public static Date getMonthFirstDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return c.getTime();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取本月最后一天
     *
     * @param sDate
     * @return
     */
    public static int getLastDayOfMonth(Date sDate) {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(sDate);
        int lastDay = cDay.getActualMaximum(Calendar.DAY_OF_MONTH);
        return lastDay;
    }

    /**
     * @param date
     * @param titel
     * @return 返回双十一标志
     */
    public static String getTitel(Date date, String titel) {
        if (date != null && titel != null) {
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            if ("2015-11-11".equals(format1.format(date)) && titel.indexOf("【双十一】") < 0) {
                return "【双十一】" + titel;
            }
        }
        return titel;
    }

    /**
     * 日期月份的改变
     *
     * @param d
     * @param i
     * @return
     */
    public static String DateMonthChange(Date d, int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(d);
        rightNow.add(Calendar.MONTH, i);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }

    /**
     * 日期加一天
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static String DateDayChange(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = sdf.parse(date);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.DAY_OF_YEAR, 1);// 日期加1天
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }
}
