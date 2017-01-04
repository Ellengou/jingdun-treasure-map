package com.jd.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * cookie操作类
 * 
 * @author starty
 * @datetime 2012-12-25 下午2:54:31
 */
public class CookieUtil {

    private static final Log log              = LogFactory.getLog(CookieUtil.class);
    // cookie默认保存时间（秒）
    private static final int defaultCookieAge = 3600 * 24 * 7;                      // 7天

    /**
     * 解析cookie对象
     * 
     * @author starty
     * @datetime 2012-12-25 下午2:56:39
     * @param request HttpServletRequest对象
     * @param cookieName cookie名字
     * @return cookie对象
     */
    public static Cookie resolveCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }

        return null;
    }

    /**
     * 解析cookie对象的值
     * 
     * @author starty
     * @datetime 2012-12-25 下午3:56:31
     * @param request HttpServletRequest对象
     * @param cookieName cookie名字
     * @return cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = resolveCookie(request, cookieName);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    /**
     * 设置cookie
     * 
     * @author starty
     * @datetime 2012-12-25 下午3:32:37
     * @param response HttpServletResponse对象
     * @param cookieName cookie名字
     * @param cookieValue cookie值
     * @param expiry 生命周期，单位：秒
     * @param uri 路径，必须以“/”开头
     */
    public static void _SetCookie(HttpServletResponse response, String cookieName, String cookieValue, int expiry,
                                  String uri) {
        try {
            Cookie cookie = new Cookie(cookieName, cookieValue);
            cookie.setMaxAge(expiry);
            cookie.setPath(uri);
            response.addCookie(cookie);
            log.info("(" + cookieName + " : " + cookieValue + ")保存到cookie中！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("(" + cookieName + " : " + cookieValue + ")保存到cookie中出错！");
        }
    }

    /**
     * 设置有生命周期的cookie
     * 
     * @author starty
     * @datetime 2012-12-25 下午3:22:35
     * @param response HttpServletResponse对象
     * @param cookieName cookie名字
     * @param cookieValue cookie值
     * @param expiry 生命周期，单位：秒
     * @param uri 路径，必须以“/”开头
     */
    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, int expiry,
                                 String uri) {
        _SetCookie(response, cookieName, cookieValue, expiry, uri);
    }

    /**
     * 设置无生命周期的cookie
     * 
     * @author starty
     * @datetime 2012-12-25 下午3:28:32
     * @param response HttpServletResponse对象
     * @param cookieName cookie名字
     * @param cookieValue cookie值
     * @param uri 路径，必须以“/”开头
     */
    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, String uri) {
        _SetCookie(response, cookieName, cookieValue, 0, uri);
    }

    /**
     * 设置默认cookie，默认生命周期为7天，默认路径为“/”
     * 
     * @author starty
     * @datetime 2012-12-25 下午3:31:29
     * @param response HttpServletResponse对象
     * @param cookieName cookie名字
     * @param cookieValue cookie值
     */
    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue) {
        _SetCookie(response, cookieName, cookieValue, defaultCookieAge, "/");
    }

    /**
     * 保存对象到cookie中
     * 
     * @author starty
     * @datetime 2012-12-26 下午3:46:43
     * @param response HttpServletResponse对象
     * @param obj 要保存的对象，必须实现Serializable接口
     * @param cookieName cookie名字
     * @param expiry 生命周期，单位：秒
     * @param uri 路径，必须以“/”开头
     */
    public static void saveObjectToCookie(HttpServletResponse response, Object obj, String cookieName, int expiry,
                                          String uri) {
        if (!(obj instanceof Serializable)) {
            log.error(obj.getClass().getName() + "：对象没有实现Serializable接口！");
            return;
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            String cookieValue = baos.toString("ISO-8859-1");
            String encodedCookieValue = URLEncoder.encode(cookieValue, "UTF-8");
            Cookie cookie = new Cookie(cookieName, encodedCookieValue);
            cookie.setMaxAge(expiry);
            cookie.setPath(uri);
            response.addCookie(cookie);
            log.info("(" + cookieName + " : " + obj.getClass().getName() + ")保存到cookie中！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error(obj.getClass().getName() + "：对象保存到cookie中出错！");
        }
    }

    /**
     * 从cookie中读取对象
     * 
     * @author starty
     * @datetime 2012-12-26 下午4:07:07
     * @param request HttpServletRequest对象
     * @param cookieName cookie名字
     * @return 目标对象
     */
    public static Object getObjectFromCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(cookieName)) {
                    String cookieValue = cookie.getValue();
                    if (StringUtils.isEmpty(cookieValue)) return null;
                    else {
                        try {
                            String decoderCookieValue = URLDecoder.decode(cookieValue, "UTF-8");
                            ByteArrayInputStream bais = new ByteArrayInputStream(
                                                                                 decoderCookieValue.getBytes("ISO-8859-1"));
                            ObjectInputStream ios = new ObjectInputStream(bais);
                            return ios.readObject();
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                            log.error(cookieName + "：从cookie中读取对象出错！");
                            return null;
                        }
                    }
                }
            }
        }

        return null;
    }

}
