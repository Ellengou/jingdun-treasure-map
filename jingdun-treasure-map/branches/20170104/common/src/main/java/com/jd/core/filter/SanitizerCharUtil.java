package com.jd.core.filter;

import org.apache.commons.lang3.StringUtils;


/**
 * @author ellen
 *         清理特殊字符串
 */
public class SanitizerCharUtil {

    private static char[] sanitizerCharArr = new char[]{(char) 9, (char) 12288};

    private static char safetyChar = ' ';

    public static String sanitizerChar(String date) {
        for (char c : sanitizerCharArr) {
            date = StringUtils.replaceChars(date, c, safetyChar);
        }
        return date;
    }

}
