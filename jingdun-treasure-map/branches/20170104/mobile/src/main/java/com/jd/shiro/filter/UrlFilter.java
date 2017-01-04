package com.jd.shiro.filter;

import com.jd.account.Account;
import com.jd.core.ensure.Ensure;
import org.apache.shiro.SecurityUtils;
import org.json.JSONObject;
import org.springframework.web.context.ContextLoader;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理AJAX权限和超时处理以及URL权限请求处理
 *
 */
public class UrlFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        String requestType = ((HttpServletRequest) request).getHeader("X-Requested-With");

        if (requestType != null && requestType.indexOf("XMLHttpRequest") > -1) {

            Account user = (Account) SecurityUtils.getSubject().getSession().getAttribute(Account.SESSION_KEY);
            // 如果已经超时不用判断权限
            if (user != null) {
                chain.doFilter(request, response);
            } else {
                JSONObject json = new JSONObject();
                Ensure.that(json).isNotNull("1");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

}
