package com.jd.utils;

import java.util.Random;

/**
 * @author Ellen.
 * @date 2016/12/6.
 * @since 1.0.
 * com.jd.utils .by jingdun.tech.
 */
public class RandomVerifyCode {

    /**
     * 生成六位随机验证码
     *
     * @return
     */
    public static String getVerifyCode() {
        Random random = new Random();
        int num = random.nextInt(899999) + 100000;
        return String.valueOf(num);
    }
}