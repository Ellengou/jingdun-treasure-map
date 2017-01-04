package com.jd.webkits.interceptors;

import com.jd.account.Account;
import com.jd.account.UserBase;
import com.jd.response.AccountResponse;
import com.jd.webkits.context.XContext;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ellen on 2015/5/20.
 */
public class RequestInterceptor extends XInterceptor {

    private final String REQUEST_LOG_PATTERN = "接口[%s]收到来自[%s]的请求[Hashcode=%s]，参数为:{%s}, 当前用户:{%s}";

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    public RequestInterceptor() {
    }

    @Override
    public boolean internalPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        XContext xContext = XContext.getCurrentContext();
        String paramsString = xContext.getParamsString();
        String userName = "用户尚未登陆...";
        try {
            Object user = SecurityUtils.getSubject().getSession().getAttribute(Account.SESSION_USER_BASE);
            if (user != null)
                userName = "userName=" + ((UserBase) user).getUsername();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info(String.format(REQUEST_LOG_PATTERN, xContext.getServiceUrl(), xContext.getSource(), xContext.getRequestHashCode(), paramsString, userName));
        return true;
    }

}
