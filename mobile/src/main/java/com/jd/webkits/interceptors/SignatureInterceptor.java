package com.jd.webkits.interceptors;

import com.jd.core.ensure.Ensure;
import com.jd.core.exceptions.XExceptionFactory;
import com.jd.core.utils.DateUtils;
import com.jd.core.utils.SignType;
import com.jd.core.utils.SignUtils;
import com.jd.webkits.context.XContext;
import org.joda.time.DateTime;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ellen on 2015/5/20.
 */
public class SignatureInterceptor extends XInterceptor {
    private Long EXPIRED_MILLSECONDS = 300L * 1000L;

    private List<String> ignoreList;

    private boolean skipCheckTime = false;
    private boolean skipCheckSignature = false;

    public void setIgnoreList(List<String> ignoreList) {
        this.ignoreList = ignoreList;
    }

    public void setSkipCheckTime(boolean skipCheckTime) {
        this.skipCheckTime = skipCheckTime;
    }

    public void setSkipCheckSignature(boolean skipCheckSignature) {
        this.skipCheckSignature = skipCheckSignature;
    }

    @Override
    public boolean internalPreHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        if (skipCheckSignature) {
            XContext.getCurrentContext().setSkipCheckSignature(skipCheckSignature);
            return true;
        }

        if (requestCanBeIgnored(httpServletRequest)) {
            return true;
        }

        XContext context = XContext.getCurrentContext();
        if (!skipCheckTime) {
            checkUpTimestamp(context.getParameterMap().get("timestamp"));  //判断请求是否超过5分钟
        }

        String appId = context.getParameterMap().get("appId");
        Ensure.that(appId).isNotEmpty("F_WEBKITS_SIGN_1006");

        SignType type = context.getSignType();
        String path = context.getServiceUrl();

        //String clientSignature = httpServletRequest.getParameter("sign");
        String clientSignature = httpServletRequest.getHeader("sign");

        Map<String, String> signParamMap = new HashMap();
        if (context.isPostRequest()) {
            // this url is servlet path
            signParamMap.put("data", context.getPostJsonBodyStr());
            signParamMap.put("path", path);
            if (!SignUtils.validateSignature(clientSignature, signParamMap, type, null)) {
                throw XExceptionFactory.create("F_WEBKITS_SIGN_1002");
            }
        } else {
            if (!SignUtils.validateSignature(clientSignature, path, type, null)) {
                throw XExceptionFactory.create("F_WEBKITS_SIGN_1002");
            }
        }

        return true;
    }

    private void checkUpTimestamp(String requestTimestamp) {
        Ensure.that(requestTimestamp).isNotEmpty("F_WEBKITS_COMMON_1008");

        DateTime requestTimestampDate = new DateTime(Long.valueOf(requestTimestamp));

        Long betweenSeconds = DateUtils.getMillSecondsBetween(new DateTime(), requestTimestampDate);
        if (Math.abs(betweenSeconds) > EXPIRED_MILLSECONDS) {
            throw XExceptionFactory.create("F_WEBKITS_SIGN_1005");
        }
    }

    public boolean requestCanBeIgnored(HttpServletRequest httpServletRequest) {
        String servletPath = httpServletRequest.getServletPath();

        if (ignoreList != null && ignoreList.contains(servletPath)) {
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
