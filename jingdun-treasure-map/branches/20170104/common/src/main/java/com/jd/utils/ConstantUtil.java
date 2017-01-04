package com.jd.utils;


/**
 * Constant
 *
 * @author zhanghongyuan
 * @date 2015/11/3
 */
public class ConstantUtil {

    /**
     * 默认key
     */
    public final static String DEFAULT_BID_SIGN_KEY = "jd26cdf9207cdfdf2c4fc22f973535a005";

    /**
     * 签名工具类
     *
     * @param sign 签名参数
     * @return
     */
    public static boolean isSign(String sign,String md5str) {
       if(MD5Utils.MD5(DEFAULT_BID_SIGN_KEY+sign).equals(md5str)){
           return true;
       }
        return false;
    }


    public static String getSign(String sign) {
        return  MD5Utils.MD5(DEFAULT_BID_SIGN_KEY+sign);
    }


    public static void main(String[] args) {
        String str="bid=12345646";
        System.out.println(getSign(str));
        System.out.println(isSign(str, "da50ea788ba5810b847a78aeaf8cdb77"));
    }
}
