package com.jd.shiro.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 处理AJAX权限和超时处理以及URL权限请求处理
 * @author dingh
 * @since 2015-9-30
 */
public class UrlFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String requestType = ((HttpServletRequest) request).getHeader("X-Requested-With");
//		if(requestType != null && requestType.indexOf("XMLHttpRequest") > -1) {
//
//			com.jd.common.entity.security.Account user = (com.jd.common.entity.security.Account)SecurityUtils.getSubject().getSession().getAttribute(com.xpos.common.entity.security.Account.SESSION_KEY);
//			// 如果已经超时不用判断权限
//			if(user != null) {
//				chain.doFilter(request, response);
//			} else {
//				((HttpServletResponse) response).setHeader("sessionstatus", "timeout");
//				((HttpServletResponse) response).setContentType("text/x-json;charset=utf-8");
//				((HttpServletResponse) response).setHeader("Cache-Control", "no-cache");
//
//				JSONObject json = new JSONObject();
//				json.put("sessionstatus", "timeout");
//				response.getWriter().write(json.toString());
//			}
//        }else{
//			ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
//			ShiroHttpServletResponseWrapper wrapper = new ShiroHttpServletResponseWrapper((HttpServletResponse)response, context,(ShiroHttpServletRequest)request);
//        	chain.doFilter(request, response);
//        }
	}

	@Override
	public void destroy() {
		
	}

}
