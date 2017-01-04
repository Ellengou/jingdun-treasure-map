/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jd.core.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author ellen
 * @desc 防xss注入
 * @time 2015/11/03
 * https://github.com/naver/lucy-xss-servlet-filter
 */
public class XssEscapeServletFilterWrapper extends HttpServletRequestWrapper {
    private XssEscapeFilter xssEscapeFilter;
    private String path = null;
    private String method = null;

    public XssEscapeServletFilterWrapper(ServletRequest request, XssEscapeFilter xssEscapeFilter) {
        super((HttpServletRequest) request);
        this.xssEscapeFilter = xssEscapeFilter;
        this.path = ((HttpServletRequest) request).getRequestURI();
        this.method = getMethod(request);
    }

    private String getMethod(ServletRequest request) {
        String method = ((HttpServletRequest) request).getMethod();
        if (StringUtils.equalsIgnoreCase(method, RequestMethod.POST.toString())) {
            String _method = ((HttpServletRequest) request).getParameter("_method");
            method = StringUtils.isNotBlank(_method) ? _method : method;
        }
        return method;
    }

    @Override
    public String getParameter(String paramName) {
        String value = super.getParameter(paramName);
        return doFilter(paramName, value);
    }

    @Override
    public String[] getParameterValues(String paramName) {
        String values[] = super.getParameterValues(paramName);
        if (values == null) {
            return values;
        }
        for (int index = 0; index < values.length; index++) {
            values[index] = doFilter(paramName, values[index]);
        }
        return values;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getParameterMap() {
        Map<String, Object> paramMap = super.getParameterMap();
        Map<String, Object> newFilteredParamMap = new HashMap<String, Object>();

        Set<Map.Entry<String, Object>> entries = paramMap.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String paramName = entry.getKey();
            Object[] valueObj = (Object[]) entry.getValue();
            String[] filteredValue = new String[valueObj.length];
            for (int index = 0; index < valueObj.length; index++) {
                //参数过滤
                filteredValue[index] = doFilter(paramName, String.valueOf(valueObj[index]));
            }
            newFilteredParamMap.put(entry.getKey(), filteredValue);
        }

        return newFilteredParamMap;
    }

    /**
     * @param paramName String
     * @param value     String
     * @return String
     */
    private String doFilter(String paramName, String value) {
        return xssEscapeFilter.doFilter(method, path, paramName, value);
    }
}
