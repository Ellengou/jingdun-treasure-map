package com.jd.utils;

import java.util.regex.Pattern;
/**
 * 后台验证手机号，新加号段，直接修改，基础验证
 * @author lidong
 * @date 2015 10 04
 */
public class MobileVerifyUtils {

    // 开头1第二位3,4,5,7,8,9
    public static String MOBILR_VERIFY = "^1[3|4|5|7|8|9][0-9]{9}$";

    public static boolean verifyMobile(String mobile) {
        if (mobile != null && mobile.trim().length() == 11) {
            return Pattern.matches(MOBILR_VERIFY, mobile);
        }
        return false;
    }

}
