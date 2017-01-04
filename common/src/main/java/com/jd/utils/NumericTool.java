package com.jd.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;

/**
 * NumericTool
 *
 * @author zhanghongyuan
 * @date 2015/8/11
 */
public class NumericTool {
    private static NumberFormat nf = new DecimalFormat("##.##");

    /**
     * 转型为integer
     * @param obj
     * @return 整形（integer）
     */
    public static int toInteger(Object obj){
    	int ret=0;
    	try {
    		if(obj==null){
        		return 0;
        	}
    		if(obj instanceof Double){
    			ret= ((Double) obj).intValue();
    		}else
    		if(obj instanceof String){
    			ret= Integer.parseInt(obj.toString());
    		}else
        		if(obj instanceof BigDecimal){
        			ret= ((BigDecimal)obj).intValue();
        		}
    		else{
    			ret=(int)obj;
    		}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
    	return ret;
    }
    /**
     * 格式化参数
     *
     * @param d
     * @param type 1 亿  2 万
     * @return
     */
    public static String toFixedNumber(Object d, String type) {

        if (d == null) {
            return "";
        }
        if(d instanceof Collection){
            return "";
        }
        String result = null;

        Double ds = Double.parseDouble(d.toString());

        if (type.equals("1")) {
            ds = ds / 100000000;
        } else if (type.equals("2")) {
            ds = ds / 10000;
        } else {
            ds = ds / 100000000;
        }

        result = nf.format(ds);
        return result;
    }

    /**
     * 将浮点数小数，固定保留两位小数
     */
    public static String toFixedNumber(Object d) {

        if (d == null) {
            return "";
        }

        int len = 0;
        String str = null;

        if(d instanceof Collection){
            return "";
        }

        if (d instanceof String) {
            str = nf.format(Double.parseDouble(d.toString()));
        } else if (d instanceof Double) {
            str = nf.format(d);
        } else {
            str = nf.format(d);
        }

        if (str.indexOf(".") > -1) {
            len = str.substring(0, str.indexOf(".")).length();
        } else {
            len = str.length();
        }
        Double ds = null;
        String result = "";
        if (len >= 9) {
            ds = Double.parseDouble(str);
            Double temp = ds / 100000000;

            result = nf.format(temp) + "亿";
        } else if (len > 4) {
            ds = Double.parseDouble(str);
            Double temp = ds / 10000;
            result = nf.format(temp) + "万";
        } else {
            result = str;
        }

        return result;
    }

    public static void main(String[] args) {
        Double ddd = 56.66d;
        ddd = 123456789.66d;
        NumberFormat nf = new DecimalFormat("##.##");

        String ddddd = "";
        Double d = 123456789.123131d;
        d = 123456.789123131d;
        String str = nf.format(d);
        int len = 0;
        if (str.indexOf(".") > -1) {
            len = str.substring(0, str.indexOf(".")).length();
        }

        System.out.println(len);
        if (len >= 9) {
            Double ds = Double.parseDouble(str);
            ddd = ds / 100000000;

            ddddd = nf.format(ddd) + "亿";
        }
        if (len >= 4) {
            Double ds = Double.parseDouble(str);
            ddd = ds / 10000;

            ddddd = nf.format(ddd) + "万";
        }

        System.out.println(ddd);
        System.out.println(str);
        System.out.println(ddddd);


    }
}
