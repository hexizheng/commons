/**
 *
 */
package com.jscn.commons.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期/时间工具类
 *
 * 提供有关日期/时间的常用静态操作方法
 */
public class DateTimeUtils {

    public static long dateSecond = 24 * 60 * 60 * 1000;

    /**
     * 验证日期字符串是否为[yyyy-MM-dd]格式
     *
     * @param dateStr 日期字符串
     * @return 日期字符串是[yyyy-MM-dd]格式返回<code>true</code>；否则返回<code>false</code>
     */
    public static boolean isPageDateStr(String dateStr) {
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 验证日期字符串是否为[yyyyMMdd]格式
     *
     * @param dateStr 日期字符串
     * @return 日期字符串是[yyyyMMdd]格式返回<code>true</code>；否则返回<code>false</code>
     */
    public static boolean isDBDateStr(String dateStr) {
        try {
            new SimpleDateFormat("yyyyMMdd").parse(dateStr);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    /**
     * 取得当前年字符串; 格式:yyyy
     *
     * @return 格式[yyyy]的当前年字符串
     */
    public static String getCurrentYear() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }

    /**
     * Date -> String(yyyyMMdd)
     *
     * @param date <code>Date</code> 对象
     * @return [yyyyMMdd]格式的日期字符串
     */
    public static String formatDbDate(Date date) {
        if (date == null) {
            return "";
        }

        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

    /**
     * String[yyyy-MM-dd HH:mm:ss]-> String[yyyyMMdd]
     *
     * @param fullTime [yyyy-MM-dd HH:mm:ss]格式日期字符串
     * @return [yyyyMMdd]格式日期字符串
     */
    public static String convertDbDateByFullTime(String fullTime) {
        Date fullDate = parseFullDateTime(fullTime);
        return formatDbDate(fullDate);
    }

    /**
     * String(yyyyMMdd) -> Date
     *
     * @param strDate [yyyyMMdd]格式日期字符串
     * @return 解析成功返回<code>Date</code> 对象，否则抛出<code>RuntimeException</code>异常
     */
    public static Date parseDbDate(String strDate) {
        if (strDate == null) {
            return null;
        }

        try {
            return new SimpleDateFormat("yyyyMMdd").parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException("将字符串" + strDate + "解析为"
                    + new SimpleDateFormat("yyyyMMdd").toPattern() + "格式的日期时发生异常:", e);
        }
    }

    /**
     * String(yyyy-MM-dd) -> Date
     *
     * @param strDate [yyyy-MM-dd]格式日期字符串
     * @return 解析成功返回<code>Date</code> 对象，否则抛出<code>RuntimeException</code>异常
     */
    public static Date parsePageDate(String strDate) {
        if (strDate == null) {
            return null;
        }

        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException("将字符串" + strDate + "解析为"
                    + new SimpleDateFormat("yyyyMMdd").toPattern() + "格式的日期时发生异常:", e);
        }
    }

    /**
     * String(yyyyMMddHHmmss) -> Date
     *
     * @param dateTime 时间字符串(yyyyMMddHHmmss)
     * @return 解析成功返回<code>Date</code> 对象，否则抛出<code>RuntimeException</code>异常
     */
    public static Date parseBackendDateTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }

        try {
            return new SimpleDateFormat("yyyyMMddHHmmss").parse(dateTime);
        } catch (ParseException e) {
            throw new RuntimeException("将字符串" + dateTime + "解析为"
                    + new SimpleDateFormat("yyyyMMddHHmmss").toPattern() + "格式的日期时发生异常:", e);
        }
    }

    /**
     * String(yyyy-MM-dd HH:mm:ss) -> Date
     *
     * @param dateTime 时间字符串(yyyy-MM-dd HH:mm:ss)
     * @return 解析成功返回<code>Date</code> 对象，否则抛出<code>RuntimeException</code>异常
     */
    public static Date parseFullDateTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }

        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
        } catch (ParseException e) {
            throw new RuntimeException("将字符串" + dateTime + "解析为"
                    + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").toPattern() + "格式的日期时发生异常:", e);
        }
    }

    /**
     * String(yyyy-MM-dd HH:mm:ss,S) -> Date
     *
     * @param dateTime 时间字符串(yyyy-MM-dd HH:mm:ss,S)
     * @return 解析成功返回<code>Date</code> 对象，否则抛出<code>RuntimeException</code>异常
     */
    public static Date parseExacDateTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }

        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,S").parse(dateTime);
        } catch (ParseException e) {
            throw new RuntimeException("将字符串" + dateTime + "解析为"
                    + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").toPattern() + "格式的日期时发生异常:", e);
        }
    }

    /**
     * Date -> String(yyyy-MM-dd)
     *
     * @param date <code>Date</code> 对象
     * @return [yyyy-MM-dd]格式日期字符串
     */
    public static String formatPageDate(Date date) {
        if (date == null) {
            return "";
        }

        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     * Date -> String(yyyy-MM-dd HH:mm:ss)
     *
     * @param date <code>Date</code> 对象
     * @return [yyyy-MM-dd HH:mm:ss]格式日期字符串
     */
    public static String formatFullDate(Date date) {
        if (date == null) {
            return "";
        }

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * Date -> String(yyyyMMddHHmmss)
     *
     * @param date <code>Date</code> 对象
     * @return [yyyyMMddHHmmss]格式日期字符串
     */
    public static String formatFull2Date(Date date) {
        if (date == null) {
            return "";
        }

        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }

    /**
     * String(yyyy-MM-dd)-> String(yyyyMMdd)
     *
     * @param pageDate [yyyy-MM-dd]格式日期字符串
     * @return [yyyyMMdd]格式日期字符串
     */
    public static String convertDate4Page2DB(String pageDate) {
        if (pageDate == null) {
            return "";
        }
        if (pageDate.length() != 10) {
            return pageDate;
        }
        return pageDate.replaceAll("-", "");
    }

    /**
     * String(yyyyMMdd)->String(yyyy-MM-dd)
     *
     * @param dbDate [yyyyMMdd]格式日期字符串
     * @return [yyyy-MM-dd]格式日期字符串
     */
    public static String convertDate4DB2Page(String dbDate) {
        if (dbDate == null) {
            return "";
        }
        if (dbDate.length() != 8) {
            return dbDate;
        } else {
            return dbDate.substring(0, 4) + "-" + dbDate.substring(4, 6) + "-"
                    + dbDate.substring(6, 8);
        }
    }

    /**
     * String(HHmmss) -> String(HH:mm:ss)
     *
     * @param dbDate [HHmmss]格式时间字符串
     * @return [HH:mm:ss]格式时间字符串
     */
    public static String convertTime4DB2Page(String dbDate) {
        if (dbDate == null) {
            return "";
        }
        if (dbDate.length() != 6) {
            return dbDate;
        } else {
            return dbDate.substring(0, 2) + ":" + dbDate.substring(2, 4) + ":"
                    + dbDate.substring(4, 6);
        }
    }

    /**
     * String(HH:mm:ss) -> String(HHmmss)
     *
     * @param pageTime [HH:mm:ss]格式时间字符串
     * @return [HHmmss]格式时间字符串
     */
    public static String convertTime4Page2DB(String pageTime) {
        if (pageTime == null) {
            return "";
        }
        if (pageTime.length() != 8) {
            return pageTime;
        }
        return pageTime.replaceAll(":", "");
    }

    /**
     * String(HHmmss) -> String(HH:mm:ss)
     *
     * @param dbTime [HHmmss]格式时间字符串
     * @return [HH:mm:ss]格式时间字符串
     * @throws ParseException ParseException
     */
    public static String dbTimeToPageTime(String dbTime) throws ParseException {
        if (dbTime == null) {
            return "";
        }
        return new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(dbTime));
    }

    /**
     * 把日期，时间转化为格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期，格式：yyyyMMdd
     * @param time 时间，格式：HHmmss
     * @return 格式[yyyy-MM-dd HH:mm:ss]的日期+时间
     */
    public static String getDateTime(String date, String time) {
        StringBuffer sb = new StringBuffer();
        sb.append(convertDate4DB2Page(date));
        sb.append(" ");

        try {
            sb.append(dbTimeToPageTime(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * 把当前时间转化为格式[yyyy-MM-dd HH:mm:ss]的字符串
     *
     * @return 格式[yyyy-MM-dd HH:mm:ss]的字符串
     */
    public static String getDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 把当前时间转化为格式[yyyyMMdd]的字符串
     *
     * @return 格式[yyyyMMdd]的字符串
     */
    public static String getCurrentFullDate() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    /**
     * 把当前时间转化为格式[yyyy-MM-dd HH:mm:ss,S]的字符串
     *
     * @return 格式[yyyy-MM-dd HH:mm:ss,S]的字符串
     */
    public static String getCurrentExacDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,S").format(new Date());
    }

    /**
     * 把当前时间转化为格式[yyyy-MM-dd]的字符串
     *
     * @return 格式[yyyy-MM-dd]的字符串
     */
    public static String getCurrentPageDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 取得当前时间字符串; 时间格式:HHmmss
     *
     * @return 格式[HHmmss]的字符串
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat("HHmmss").format(new Date());
    }

    /**
     * 取得当前时间字符串; 时间格式(yyyyMMddHHmmss)
     *
     * @return 格式[yyyyMMddHHmmss]的字符串
     */
    public static String getCurrentTimeForBACKEND() {
        Date date = new Date();
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }

    /**
     * 解析时间字符串;
     *
     * @param time 格式[HHmmss]的时间字符串
     * @return 解析成功返回<code>Date</code> 对象，否则抛出<code>RuntimeException</code>异常
     */
    public static Date parseTime(String time) {
        try {
            return new SimpleDateFormat("HHmmss").parse(time);
        } catch (ParseException e) {
            throw new RuntimeException("将字符串" + time + "按照"
                    + new SimpleDateFormat("HHmmss").toPattern() + "格式进行解析时发生异常:", e);
        }
    }

    /**
     * Date -->> yyyy年MM月dd HH:mm:ss
     *
     * @param date <code>Date</code> 对象
     * @return [yyyy年MM月dd HH:mm:ss]格式字符串
     */
    public static String formatLocalDate(Date date) {
        return new SimpleDateFormat("yyyy年MM月dd HH:mm:ss").format(date);
    }

    /**
     * HH:mm:ss ->> HHmmss
     *
     * @param pageTime [HH:mm:ss]格式字符串
     * @return [HHmmss]格式字符串
     */
    public static String pageTimeToDbTime(String pageTime) {
        return pageTime.replaceAll(":", "");
    }

    /**
     * 将日期转换为指定格式
     *
     * @param date    <code>Date</code> 对象
     * @param pattern 日期格式
     * @return 指定格式的字符串
     */
    public static String formateDate2Str(Date date, String pattern) {
        SimpleDateFormat s = new SimpleDateFormat(pattern);
        return s.format(date);
    }

    /**
     * 将日期中的2007-1-1转化为20070101格式
     *
     * @param datestr [yyyy-MM-dd]格式字符串
     * @return 剔除了日期分隔符的日期字符串
     */
    public static String dateStringFormat(String datestr) {
        if (datestr == null || datestr.equals(""))
            return null;
        String[] str1 = datestr.split("-");
        if (str1.length == 3) {
            if (str1[1].length() == 1) {
                str1[1] = "0" + str1[1];
            }
            if (str1[2].length() == 1) {
                str1[2] = "0" + str1[2];
            }
        } else
            return datestr;
        datestr = str1[0] + str1[1] + str1[2];
        return datestr;
    }

    /**
     * 天数偏移
     *
     * @param date   [yyyyMMdd]格式日期
     * @param dayNum 偏移天数
     * @return 加上偏移天数后的[yyyyMMdd]格式日期字符串
     */
    public static String getDateTimeForword(String date, int dayNum) {
        if (date == null) {
            return "";
        }
        Date tempdate;
        if (date.indexOf("-") == -1) {
            try {
                tempdate = new SimpleDateFormat("yyyyMMdd").parse(date);
            } catch (ParseException e) {
                throw new RuntimeException("将字符串" + date + "解析为"
                        + new SimpleDateFormat("yyyy-MM-dd").toPattern() + "格式的日期时发生异常:", e);
            }
        } else {
            try {
                tempdate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (ParseException e) {
                throw new RuntimeException("将字符串" + date + "解析为"
                        + new SimpleDateFormat("yyyy-MM-dd").toPattern() + "格式的日期时发生异常:", e);
            }
        }
        tempdate = getDataTimeForwordDay(tempdate, dayNum);
        return new SimpleDateFormat("yyyyMMdd").format(tempdate);
    }

    /**
     * 天数偏移
     *
     * @param date   指定日期
     * @param dayNum 偏移天数
     * @return 加上偏移天数后的日期
     */
    public static Date getDataTimeForwordDay(Date date, int dayNum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, dayNum);
        return cal.getTime();
    }

    /**
     * 月数偏移
     *
     * @param date     指定日期
     * @param monthNum 偏移月数
     * @return 加上偏移月数后的日期
     */
    public static Date getDataTimeForwordMonth(Date date, int monthNum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, monthNum);
        return cal.getTime();
    }

    /**
     * 年数偏移
     *
     * @param date    指定日期
     * @param yearNum 偏移年数
     * @return 加上偏移年数后的日期
     */
    public static Date getDataTimeForwordYear(Date date, int yearNum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, yearNum);
        return cal.getTime();
    }

    /**
     * 月数偏移 -> [yyyyMMdd]
     *
     * @param date     指定日期
     * @param monthNum 偏移月数
     * @return 加上偏移月数后的[yyyyMMdd]格式的日期
     */
    public static String getDataTimeForwordMonthStr(Date date, int monthNum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, monthNum);

        return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
    }

    /**
     * 小时偏移
     *
     * @param date    指定日期
     * @param timeNum 偏移小时数
     * @return 加上偏移小时数后的日期
     */
    public static Date getDataTimeOffset(Date date, int timeNum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, timeNum);
        return cal.getTime();
    }

    /**
     * 取得指定格式的当前时间字符串
     *
     * @param pattern 格式
     * @return 指定格式的当前时间字符串
     */
    public static String getTime(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * 取得指定时间的偏移时间
     *
     * @param transferTime 原始时间（yyyy-MM-dd HH:ss:mm）
     * @param calendarType 偏移单位（Calendar的常量）
     * @param amount       偏移量
     * @return [yyyy-MM-dd HH:mm:ss]格式时间字符串
     */
    public static String getExcursionTime(
            String transferTime,
            int calendarType, int amount) {
        Date parseFullDateTime = parseFullDateTime(transferTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseFullDateTime);
        calendar.add(calendarType, amount);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    /**
     * 取得指定格式的当前时间偏移一定量后的时间
     *
     * @param calendarType 偏移单位（Calendar的常量）
     * @param amount       偏移量
     * @param pattern      日期格式
     * @return 指定格式的时间字符串
     */
    public static String getExcursionTime(
            int calendarType, int amount,
            String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendarType, amount);
        return new SimpleDateFormat(pattern).format(calendar.getTime());
    }

    /**
     * 取得当前小时和分钟构成的长整型数,例如(12:30 = 1230)
     *
     * @return 当前小时和分钟构成的长整型数, 例如(12:30 = 1230)
     */
    public static Long getCurrentHourMinute() {
        return Long.parseLong(new SimpleDateFormat("HHmm").format(new Date()));
    }

    /**
     * 求两个日期的天数之差
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 两个日期的天数之差
     */
    public static int getOddDateNum(String beginTime, String endTime) {
        Date dateBegin = parseFullDateTime(beginTime);
        Date dateEnd = parseFullDateTime(endTime);
        return (int) ((dateEnd.getTime() - dateBegin.getTime()) / (dateSecond));
    }

    /**
     * 取系统时间零点
     *
     * @return 当前时间所在天的零点时间
     */
    public static Date getCurrentZeroTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 计算格林威治时间（包含时差计算）
     *
     * @return 长整数表示的时间
     */
    public static long unixTime() {
        java.util.Calendar cal1 = new GregorianCalendar(1970, 0, 1, 0, 0, 0);
        java.util.Calendar cal = java.util.Calendar
                .getInstance(java.util.Locale.CHINA);
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        long now = cal1.getTimeInMillis() / 1000;
        long now1 = cal.getTimeInMillis() / 1000;
        return now1 - now;
    }

    /**
     * 当前时区时间
     *
     * @return 当前时区时间
     */
    public static Date getCurrentLocaleTime() {
        java.util.Calendar cal = java.util.Calendar
                .getInstance(java.util.Locale.CHINA);
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTime();
    }

    /**
     * 取当前月份第一天日期 (yyyy-MM-dd)
     *
     * @return Date 当前月份第一天日期
     */
    public static Date getCurrentMothFirstDayByDate() {
        Date nowTime = new Date(System.currentTimeMillis());// 取系统时间
        Date fistDay = null;
        try {
            SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-01");
            String today = sformat.format(nowTime);
            fistDay = parsePageDate(today);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fistDay;
    }

    /**
     * 取当前月份第一天日期 (yyyy-MM-dd)
     *
     * @return [yyyy-MM-dd]格式的当前月份第一天日期字符串
     */
    public static String getCurrentMothFirstDayByString() {
        Date nowTime = new Date(System.currentTimeMillis());// 取系统时间
        String today = null;
        try {
            SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-01");
            today = sformat.format(nowTime);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return today;
    }

    /**
     * 将字符串按照指定的格式转换为日期类型
     *
     * @param date       一定格式字符串表示的日期
     * @param dateFormat 字符串格式
     * @return 解析成功返回<code>Date</code>对象，解析失败则抛出<code>Exception</code>
     * @throws Exception 通用异常
     */
    public static Date parseDateByString(String date, String dateFormat)
            throws Exception {
        try {
            return new SimpleDateFormat(dateFormat).parse(date);
        } catch (ParseException e) {
            throw new Exception("解析字符串【" + date + "】为【" + dateFormat
                    + "】格式的日期对象时发生异常：", e);
        }
    }

    /**
     * 对传入的日期按指定格式转换成字符串
     *
     * @param date   指定日期
     * @param format 日期格式
     * @return 按指定格式对日期进行格式化后的字符串
     * @throws Exception 通用异常
     */
    public static String parseStringByDate(Date date, String format)
            throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            throw new Exception("解析字符串【" + date + "】为【" + format
                    + "】格式的日期对象时发生异常：", e);
        }

    }

    /**
     * 将毫秒数转化为日期格式
     *
     * @param time 毫秒表示的日期
     * @return <code>Date</code>对象
     */
    public static Date parse(Long time) {
        if (time == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.getTime();
    }

    /**
     * 将日期字符串按照指定的格式转换成新的日期字符串
     *
     * @param oldDate   原日期格式的日期字符串
     * @param oldFormat 原日期格式
     * @param newFormat 新日期格式
     * @return 新日期格式的日期字符串
     * @throws Exception 通用异常
     */
    public static String parseNewTimeString(
            String oldDate, String oldFormat,
            String newFormat) throws Exception {
        if (StringUtils.isEmpty(oldDate)) {
            return null;
        }
        Date date = parseDateByString(oldDate, oldFormat);
        return parseStringByDate(date, newFormat);
    }

}
