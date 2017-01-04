package com.jd.core.multipart;

import com.jd.core.filter.XssEscapeFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author ellen
 * @since 2015/11/10
 * @see #(ServletContext)
 * @see #setResolveLazily
 * @see org.apache.commons.fileupload.servlet.ServletFileUpload
 * @see org.apache.commons.fileupload.disk.DiskFileItemFactory
 */
public class XssCommonsMultipartResolver extends CommonsMultipartResolver {
	
	private XssEscapeFilter xssEscapeFilter = XssEscapeFilter.getInstance();
	
	private String path;
	
	private String method;  
	
	@Override
	public MultipartHttpServletRequest resolveMultipart(final HttpServletRequest request) throws MultipartException {
			this.path = ((HttpServletRequest)request).getRequestURI(); 
			this.method = getMethod(request);
			MultipartParsingResult parsingResult = parseRequest(request);
			Map<String, String[]> multipartParameters = parsingResult.getMultipartParameters(); 
			for (Entry<String, String[]> entry : multipartParameters.entrySet()) {
				String paramName = entry.getKey();
				String[] values = entry.getValue();
				if (values == null) {
					continue;
				}
				for (int i = 0; i < values.length; i++) { 
					values[i] = doFilter(paramName, values[i]);
				}
			}
			Map<String, String> multipartParameterContentTypes = parsingResult.getMultipartParameterContentTypes();
			for (Entry<String, String> entry : multipartParameterContentTypes.entrySet()) {
				String paramName = entry.getKey();
				String value = entry.getValue();
				if (StringUtils.isNotBlank(value)) {					 
					multipartParameterContentTypes.put(paramName, doFilter(paramName, value));
				} 
			}
			return new DefaultMultipartHttpServletRequest(request, parsingResult.getMultipartFiles(), multipartParameters, multipartParameterContentTypes);
		 
	}
	/**
	 * @param paramName String
	 * @param value String
	 * @return String
	 */
	private String doFilter(String paramName, String value) {
		return xssEscapeFilter.doFilter(method,path, paramName, value);
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	private String getMethod(ServletRequest request){
		String method =  ((HttpServletRequest)request).getMethod();
		if(StringUtils.equalsIgnoreCase(method, RequestMethod.POST.toString())){
			String _method = ((HttpServletRequest)request).getParameter("_method"); 
			method = StringUtils.isNotBlank(_method) ? _method : method;
		}
	    return method;
	}
}
