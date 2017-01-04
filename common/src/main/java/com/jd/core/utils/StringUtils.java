package com.jd.core.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by Jintao on 2015/5/20.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static final String EMPTY = "";

    public static String replaceWithBlank(String sourceString, String replacedSymbol) {
        return sourceString.replace(replacedSymbol, StringUtils.EMPTY);
    }

    /**
     * 校验给定的字符串是否超过给点的最大长度
     *
     * @param s         待校验的字符串
     * @param maxLength 最大长度
     * @return 是否查过最大长度
     */
    public static boolean isRangeLength(String s, int maxLength) {
        return getStringLength(s) > maxLength;
    }

    /**
     * 获取字符串长度（null值返回0，汉字占2个字符）
     *
     * @param s
     * @return
     */
    public static int getStringLength(String s) {
        int valueLength = 0;
        if (s == null) {
            return valueLength;
        }
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符
            String temp = s.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为2
                valueLength += 2;
            } else {
                // 其他字符长度为1
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 全角转半角字符串
     *
     * @param QJstr 传入字符串
     * @return 转换后的字符串
     */
    public static String full2HalfChange(String QJstr) {

        if (QJstr == null) return null;

        StringBuffer outStrBuf = new StringBuffer("");
        String Tstr = "";
        byte[] b = null;

        for (int i = 0; i < QJstr.length(); i++) {
            Tstr = QJstr.substring(i, i + 1);
            try {

                // 全角空格转换成半角空格
                if (Tstr.equals("　")) {
                    outStrBuf.append(" ");
                    continue;
                }

                b = Tstr.getBytes("unicode");

                // 得到 unicode 字节数据
                if (b[2] == -1) {
                    b[3] = (byte) (b[3] + 32);
                    b[2] = 0;
                    outStrBuf.append(new String(b, "unicode"));
                } else {
                    outStrBuf.append(Tstr);
                }
            } catch (UnsupportedEncodingException e) {
                outStrBuf.append(Tstr);
            }
        }
        return outStrBuf.toString();
    }
}
