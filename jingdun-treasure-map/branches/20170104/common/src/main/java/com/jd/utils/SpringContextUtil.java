package com.jd.utils;

import java.net.InetAddress;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * springbean工具类
 * 
 * @ClassName: SpringContextUtil
 * @Description: TODO
 * @author Comsys-lanny
 * @date 2015年5月11日 下午2:47:49
 */

public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext ApplicationContext;

    private static String             HOST_IP;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.ApplicationContext = applicationContext;
        SpringContextUtil.HOST_IP = getInetAddress();
    }

    /**
     * 获取bean
     * 
     * @param name
     * @return
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return ApplicationContext.getBean(name);
    }

    public static String getInetAddress() {
        try {
            InetAddress netAddress = InetAddress.getLocalHost();
            if (netAddress != null) {
                return netAddress.getHostAddress();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static String getHostIp() {
        return HOST_IP;
    }

}
