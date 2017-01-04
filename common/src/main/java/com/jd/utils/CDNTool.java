package com.jd.utils;

import java.util.Random;

/**
 * CDNTool
 *
 * @author zhanghongyuan
 * @date 2015/8/11
 */
public class CDNTool {

    /**
     *
     */
    public static String replace(String obj){
    	try {
            int number = new Random().nextInt(4) + 1;
            obj=obj.replace("#1#",String.valueOf(number));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return obj;
    }

    /**
     * 随机数
     * @return
     */
    public static int randomNum(){
        int number = 5;
        try {
             number = new Random().nextInt(5) + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }



    public static void main(String[] args) {
        String url="http://baidu.com";
        System.out.println( CDNTool.replace(url));

    }
}
