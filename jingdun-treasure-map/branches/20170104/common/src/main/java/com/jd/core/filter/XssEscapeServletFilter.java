package com.jd.core.filter;

import org.apache.shiro.config.Ini;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author ellen
 * @desc 防xss注入
 * @time 2015/11/03
 * https://github.com/naver/lucy-xss-servlet-filter
 */
public class XssEscapeServletFilter implements Filter {

    private XssEscapeFilter xssEscapeFilter = XssEscapeFilter.getInstance();

    public XssEscapeServletFilter() {
        super();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    @SuppressWarnings("static-access")
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (xssEscapeFilter.getConfig().addXSSFilter(request)) {
            chain.doFilter(new XssEscapeServletFilterWrapper(request, xssEscapeFilter), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    /**
     * 加载过滤配置
     *
     * @param definitions
     */
    public void setFilterChainDefinitions(String definitions) {
        Ini ini = new Ini();
        ini.load(definitions);
        Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        }
    }


}
