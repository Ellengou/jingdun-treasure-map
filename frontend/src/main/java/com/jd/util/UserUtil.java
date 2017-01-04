package com.jd.util;

import java.util.Random;

/**
 * UserUtil
 *
 * @author zhanghongyuan
 * @date 2015/6/29
 */
public class UserUtil {
    /**
     * 获取盐 注册
     * @return
     */
    public  static String getsalt(){
       return new Random().nextInt(10000000)+"";
    }

    /**
     * 手机验证码
     * @return
     */
    public  static String getmoblecode(){
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int) (Math.random() * 9);
        }

        return vcode;
    }


}
