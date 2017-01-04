package com.jd.core.utils;

/**
 * Created by ylc on 2015/12/14.
 */
public class ShortUrlUtils {
    public static final String ALPHABET = "17GZaAWqI94bcySwQsgOfDvzJYtNUiCLk5xno3Xpl8hH0rPVdT2EuBeK6FjMRm";
    public static final int BASE = ALPHABET.length();

    public static String encode(Long num) {
        StringBuilder str = new StringBuilder();
        while (num > 0) {
            str.insert(0, ALPHABET.charAt((int) (num % BASE)));
            num = num / BASE;
        }
        return str.toString();
    }

    public static Long decode(String str) {
        Long num = 0l;
        for (int i = 0; i < str.length(); i++) {
            num = num * BASE + ALPHABET.indexOf(str.charAt(i));
        }
        return num;
    }

    public static void main(String[] args) {
        System.out.println(ShortUrlUtils.encode(50000l));
    }
}
