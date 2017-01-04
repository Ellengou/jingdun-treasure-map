package com.jd.utils;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IDCardUtils 身份证验证
 *
 * @author zhanghongyuan
 * @date 2015/6/16
 */
public class IDCardUtils {

    static final Logger        log        = Logger.getLogger(IDCardUtils.class);

    /*********************************** 身份证验证开始 ****************************************/
    /**
     * 身份证号码验证 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码， 八位数字出生日期码，三位数字顺序码和一位数字校验码。 2、地址码(前六位数）
     * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。 3、出生日期码（第七位至十四位） 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
     * 4、顺序码（第十五位至十七位） 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。 5、校验码（第十八位数） （1）十七位数字本体码加权求和公式 S =
     * Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和 Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5
     * 8 4 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
     */

    static String[]            ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
    static String[]            Wi         = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5",
            "8", "4", "2"                };
    // 省份
    static Map<String, String> areaHash   = new Hashtable<String, String>();

    static {
        areaHash.put("11", "北京");
        areaHash.put("12", "天津");
        areaHash.put("13", "河北省");
        areaHash.put("14", "山西省");
        areaHash.put("15", "内蒙古");
        areaHash.put("21", "辽宁省");
        areaHash.put("22", "吉林省");
        areaHash.put("23", "黑龙江省");
        areaHash.put("31", "上海市");
        areaHash.put("32", "江苏省");
        areaHash.put("33", "浙江省");
        areaHash.put("34", "安徽省");
        areaHash.put("35", "福建省");
        areaHash.put("36", "江西省");
        areaHash.put("37", "山东省");
        areaHash.put("41", "河南省");
        areaHash.put("42", "湖北省");
        areaHash.put("43", "湖南省");
        areaHash.put("44", "广东省");
        areaHash.put("45", "广西省");
        areaHash.put("46", "海南省");
        areaHash.put("50", "重庆");
        areaHash.put("51", "四川省");
        areaHash.put("52", "贵州省");
        areaHash.put("53", "云南省");
        areaHash.put("54", "西藏");
        areaHash.put("61", "陕西省");
        areaHash.put("62", "甘肃省");
        areaHash.put("63", "青海省");
        areaHash.put("64", "宁夏");
        areaHash.put("65", "新疆");
        areaHash.put("71", "台湾");
        areaHash.put("81", "香港");
        areaHash.put("82", "澳门");
        areaHash.put("91", "国外");
    }



    /**
     * 功能：省份证前2位判读省
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings("unchecked")
    public static String getAreaCode(String code) {
        return areaHash.get(code);

    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

}
