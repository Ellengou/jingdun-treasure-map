package com.jd.webkits.shiro;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 基于ip的过滤器，方便内外网隔离访问
 *
 * @author Guoqw
 * @since 2015-06-09 10:38
 */
public class IPAuthorizationFilter extends AuthorizationFilter {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 未知ip
     */
    private static final String UNKNOWN = "unknown";
    /**
     * 通过代理转发时（通常为Nginx），设置的真实客户端ip的信息头字段
     */
    private static final String FORWARD = "x-forwarded-for";
    /**
     * 通过代理转发时（通常为Apache），设置的真实客户端ip的信息头字段
     */
    private static final String PROXY = "Proxy-Client-IP";
    /**
     * 通过代理转发时（通常为Apache），设置的真实客户端ip的信息头字段
     */
    private static final String WL_PROXY = "WL-Proxy-Client-IP";
    /**
     * IP分隔符
     */
    private static final String IP_SEPARATOR = ",";
    /**
     * 本机访问ip
     */
    private static final String LOCALHOST = "127.0.0.1";
    /**
     * 本机访问iP V6格式
     */
    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
    /**
     * 内网ip前缀
     */
    private static final String LOCAL_NETWORK_PREFIX = "192.168";

    /**
     * 允许通过的内网ips:key_说明|value_ip
     * 如果未设置,则默认不过滤
     */
    private Map<String, String> allowIps;

    /**
     * 当前开发模式：dev|pub
     */
    private String profile;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return allowIps == null || allowIps.size() == 0 || isAllowIp(servletRequest);
    }

    //是否允许的ip,默认本机访问可以通过
    private boolean isAllowIp(ServletRequest servletRequest) {
        String ip = getRequestRealIp(servletRequest);
        return isLocalNetWork(ip) || allowIps.containsValue(ip);
    }

    //是否内网访问
    private boolean isLocalNetWork(String ip) {
        return StringUtils.startsWith(ip, LOCAL_NETWORK_PREFIX) || StringUtils.equals(LOCALHOST, ip) || StringUtils.equals(LOCALHOST_IPV6, ip);
    }

    //获取请求的真实客户端ip：被多级反向代理或者跳转转发的请求
    private String getRequestRealIp(ServletRequest servletRequest) {
        String ip = null;
        HttpServletRequest request = null;
        if (servletRequest instanceof HttpServletRequest) {
            request = (HttpServletRequest) servletRequest;
        }
        if (request != null) {
            ip = request.getHeader(FORWARD);
            if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
                ip = request.getHeader(PROXY);
            }
            if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
                ip = request.getHeader(WL_PROXY);
            }
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(UNKNOWN, ip)) {
            ip = servletRequest.getRemoteAddr();
        }
        //代理ip可能有多级，ip也会有多个，第一个不是“unknown”的ip为真实客户端ip
        if (StringUtils.isNotBlank(ip) && StringUtils.split(ip, IP_SEPARATOR).length > 0) {
            for (String s : StringUtils.split(ip, IP_SEPARATOR)) {
                if (!StringUtils.equalsIgnoreCase(UNKNOWN, s)) {
                    ip = s;
                    break;
                }
            }
        }
        logger.info("client ip:" + ip);
        return ip;
    }

    public Map<String, String> getAllowIps() {
        return allowIps;
    }

    public void setAllowIps(Map<String, String> allowIps) {
        this.allowIps = allowIps;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
