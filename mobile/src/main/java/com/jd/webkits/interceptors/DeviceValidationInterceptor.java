package com.jd.webkits.interceptors;

import com.jd.core.ensure.Ensure;
import com.jd.webkits.context.XContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ellen on 2015/10/20.
 */
public class DeviceValidationInterceptor extends XInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean internalPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        XContext context = XContext.getCurrentContext();

        String deviceNumber = String.valueOf(context.getParameter("deviceNumber"));
        Ensure.that(deviceNumber).isNotEmpty("F_WEBKITS_VALIDATION_2002");

        String shopId = String.valueOf(context.getParameter("shopId"));
        Ensure.that(shopId).isNotEmpty("F_WEBKITS_VALIDATION_2001");
        return true;
    }

}
