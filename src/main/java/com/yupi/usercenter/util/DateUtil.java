package com.yupi.usercenter.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author HP
 */
public class DateUtil {

    /**
     * TIME 默认格式: yyyy-MM-dd HH:mm:ss 年月日 时分秒
     */
    public static final String C_TIME_PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /**
     * TIME 默认格式: yyyy-MM-dd HH:mm 年月日 时分
     */
    public static final String C_TIME_PATTERN_1 = "yyyy-MM-dd HH:mm";

    /**
     * TIME 默认格式: yyyy-MM-dd HH 年月日 时
     */
    public static final String C_TIME_PATTERN_2 = "yyyy-MM-dd HH";

    /**
     * DATE 默认格式: yyyy-MM-dd 年月日
     */
    public static final String C_DATE_PATTERN_DEFAULT = "yyyy-MM-dd";

    /**
     * DATE 默认格式: yyyy-MM 年月日
     */
    public static final String C_DATE_PATTERN_1 = "yyyy-MM";

    /**
     * DATE 默认格式: yyyy 年月日
     */
    public static final String C_DATE_PATTERN_2 = "yyyy";

    /**
     * 日期格式: yyyyMMddHHmmss
     */
    public static final String C_DATE_PATTERN_PARSE_1 = "yyyyMMddHHmmss";

    /**
     * 日期格式: yyyyMMdd
     */
    public static final String C_DATE_PATTERN_PARSE_2 = "yyyyMMdd";

    /**
     * 日期格式: HHmmss
     */
    public static final String C_DATE_PATTERN_PARSE_3 = "HHmmss";

    /**
     * 将日期格式化为字符串
     *
     * @param date    日期
     * @param pattern 格式化模式，如 "yyyy-MM-dd HH:mm:ss"
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return DateFormatUtils.format(date, C_TIME_PATTERN_DEFAULT);
        }
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * yyyy-MM-dd HH:mm:ss 字符串形式
     *
     * @return
     */
    public static String getDefaultTime() {
        return DateFormatUtils.format(new Date(), C_TIME_PATTERN_DEFAULT);
    }

    /**
     * yyyy-MM-dd 字符串形式
     *
     * @return
     */
    public static String getDefaultDate() {
        return DateFormatUtils.format(new Date(), C_DATE_PATTERN_DEFAULT);
    }

    /**
     * 日期格式: yyyyMMddHHmmss 字符串形式
     *
     * @return
     */
    public static String getTimeString() {
        return formatDate(new Date(), C_DATE_PATTERN_PARSE_1);
    }

    /**
     * 日期格式: yyyyMMdd 字符串形式
     *
     * @return
     */
    public static String getTimeString02() {
        return formatDate(new Date(), C_DATE_PATTERN_PARSE_2);
    }

    /**
     * 日期格式: HHmmss 字符串形式
     *
     * @return
     */
    public static String getTimeString03() {
        return formatDate(new Date(), C_DATE_PATTERN_PARSE_3);
    }

    /**
     * 将字符串解析为日期
     *
     * @param str     字符串
     * @param pattern 格式化模式，如 "yyyy-MM-dd HH:mm:ss"
     * @return 解析后的日期
     * @throws ParseException 解析异常
     */
    public static Date parseDate(String str, String pattern) throws ParseException {
        if (StringUtils.isBlank(pattern)) {
            return DateUtils.parseDate(str, C_TIME_PATTERN_DEFAULT);
        }
        return DateUtils.parseDate(str, pattern);
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 获取昨天的日期
     *
     * @return 昨天的日期
     */
    public static Date yesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 获取明天的日期
     *
     * @return 明天的日期
     */
    public static Date tomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    /**
     * 获取本周第一天的日期（周一）
     *
     * @return 本周第一天的日期
     */
    public static Date firstDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 获取本周最后一天的日期（周日）
     *
     * @return 本周最后一天的日期
     */
    public static Date lastDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return cal.getTime();
    }

    /**
     * 获取本月第一天的日期
     *
     * @return 本月第一天的日期
     */
    public static Date firstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取本月最后一天的日期
     *
     * @return 本月最后一天的日期
     */
    public static Date lastDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 相差的天数
     */
    public static int diffDays(Date date1, Date date2) {
        long diff = Math.abs(date1.getTime() - date2.getTime());
        return (int) (diff / (24 * 60 * 60 * 1000));
    }
}