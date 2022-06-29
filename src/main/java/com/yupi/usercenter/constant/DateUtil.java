package com.yupi.usercenter.constant;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @author HP
 * @create 2022/6/1 15:30
 */
public class DateUtil {
    public static String formatDate(Date date, String pattern) {
        String formatDate = null;
        if (StringUtils.isNotBlank(pattern)) {
            formatDate = DateFormatUtils.format(date, pattern);
        }else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    public static String getDays() {
        return formatDate(new Date(), "yyyyMMdd");
    }
}
