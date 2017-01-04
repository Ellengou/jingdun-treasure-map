package com.jd.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字工具类
 * 
 * @ClassName: NumberUtils
 * @Description: TODO
 * @author Comsys-lanny
 * @date 2015年5月11日 下午2:47:26
 */

public class NumberUtils extends org.apache.commons.lang.math.NumberUtils {

    public static int parseInt(String str) {
        int result = 0;
        if (!str.equals("")) result = Integer.parseInt(str);
        return result;
    }

    public static long parseLong(String str) {
        long result = 0L;
        if (!str.equals("")) result = Long.parseLong(str);
        return result;
    }

    public static float parseFloat(String str) {
        float result = 0.0F;
        if (!str.equals("")) result = Float.parseFloat(str);
        return result;
    }

    public static double parseDouble(String str) {
        double result = 0.0D;
        if (!str.equals("") && null != str && !str.equals("null")) result = Double.parseDouble(str);
        return result;
    }

    public static String formatNumber(double value, int digitNum) {
        String str = "0.0";

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < digitNum; i++) {
            sb.append("0");
        }
        DecimalFormat nf = new DecimalFormat("#." + sb.toString());
        nf.setParseIntegerOnly(false);
        nf.setDecimalSeparatorAlwaysShown(false);
        str = nf.format(value);
        if (StringUtils.isEmpty(str.split("\\.")[0])) str = "0" + str;
        return str;
    }

    /**
     * 进位处理，2.353变成2.36
     * 
     * @param d
     * @return
     */
    public static double format(double d) {
        BigDecimal big = BigDecimal.valueOf(d);
        big = big.setScale(2, BigDecimal.ROUND_UP);
        return big.doubleValue();
    }

    /**
     * 直接删除多余的小数位，如2.356会变成2.35
     * 
     * @param d
     * @return
     */
    public static double format2(double d) {
        BigDecimal big = BigDecimal.valueOf(d);
        big = big.setScale(2, BigDecimal.ROUND_DOWN);
        return big.doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(format2(166.666));
    }
}
