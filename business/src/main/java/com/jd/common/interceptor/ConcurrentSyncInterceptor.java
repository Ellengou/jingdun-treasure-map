package com.jd.common.interceptor;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jd.common.annotation.ConcurrentSync;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jd.utils.SpringContextUtil;

/**
 * 类ConcurrentSyncInterceptor.java的实现描述：并发同步拦截器
 * 
 * @author Starty 2015年9月8日 下午11:54:47
 */
public class ConcurrentSyncInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            ConcurrentSync annotation = method.getAnnotation(ConcurrentSync.class);
            if (annotation != null) {
                String redisKey = annotation.redisKey();
                if (StringUtils.isBlank(redisKey)) {
                    redisKey = SpringContextUtil.getHostIp() + "_" + method.getName();

                }
                int timeOut = annotation.timeOut();

            }
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
                                                                                                                       throws Exception {
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
                                                                                                                        throws Exception {
    }

}
