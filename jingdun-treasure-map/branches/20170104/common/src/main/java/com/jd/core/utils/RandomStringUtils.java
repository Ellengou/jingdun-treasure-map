package com.jd.core.utils;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import java.util.*;

public class RandomStringUtils extends org.apache.commons.lang3.RandomStringUtils{

    //默认随机生成的uuuid长度
    public static final int DEFAULT_UUUID_LENGTH = 32;

    public static String generateSalt() {
        return new SecureRandomNumberGenerator().nextBytes().toBase64();

    }

    public static String generateRandomCharAndNumber(int count) {
        if (count < 2) {
            throw new IllegalArgumentException(
                    "Requested random string length " + count
                            + " is less than 1.");
        }
        int numberCount = RandomUtils.nextInt(count - 1);
        numberCount = numberCount == 0 ? 1 : numberCount;
        StringBuffer stringBuffer = new StringBuffer();
        // 生成随机数字
        stringBuffer.append(randomNumeric(numberCount));
        // 生成随机字符(剔除'l','o')
        stringBuffer.append(random((count - numberCount),
                "abcdefghijkmnpqrstuvwxyz"));
        // 打乱
        char[] c = stringBuffer.toString().toCharArray();
        List<Character> lst = new ArrayList<Character>();
        for (int i = 0; i < c.length; i++)
            lst.add(c[i]);
        Collections.shuffle(lst);
        stringBuffer = new StringBuffer();
        for (int i = 0; i < lst.size(); i++)
            stringBuffer.append(lst.get(i));
        return stringBuffer.toString();
    }

    public static String getRandomString() {
        return getRandomString(DEFAULT_UUUID_LENGTH);
    }

    public static String getRandomString(int length){
        return getRandomString(length, false);
    }
    
    public static String getRandomString(int length, Boolean upper){
        String string = getUUID();
        if(string.length() >= length){
            string = string.substring(0, length);
        }else{
            while(string.length() < length){
                int i = new Random().nextInt(36);
                string+= i>25?(char)('0'+(i-26)):(char)('a'+i);
            }
        }
        if(upper)
            string = string.toUpperCase();
        
        return string;
    }
    
    public static String getUUID(){
        UUID uuid  =  UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
