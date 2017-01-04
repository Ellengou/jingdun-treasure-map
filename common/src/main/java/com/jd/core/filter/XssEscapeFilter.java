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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author ellen
 * @desc   防xss注入
 * @time  2015/11/13
 *    https://github.com/naver/lucy-xss-servlet-filter
 */
@Service
public final class XssEscapeFilter { 
	
	private final Logger logger  =  LoggerFactory.getLogger(XssEscapeFilter.class);
	
	private static XssEscapeFilter xssEscapeFilter;
	
	private static XssEscapeFilterConfig config;
 
	
	static {
		try {
			xssEscapeFilter = new XssEscapeFilter(); 
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * Default Constructor
	 */
	private XssEscapeFilter() {
		config = new XssEscapeFilterConfig();
	}

	/**
	 * @return XssEscapeFilter
	 */
	public static XssEscapeFilter getInstance() {
		return xssEscapeFilter;
	}

	/**
	 * @param path String
	 * @param paramName String
	 * @param value String
	 * @return String
	 */
	public String doFilter(String method,String path, String paramName, String value) {
		if (StringUtils.isBlank(value) || StringUtils.equalsIgnoreCase(method, RequestMethod.GET.toString())) {
			return value;
		} 
		Defender  defender = useDefender(path, paramName);  
		if(defender == null){		 
			return  value;
		}
		int turbidityValLength = value.length();
		//xss过滤
		value = defender.doFilter(value);
		int scanitizerValLength = value.length();
		if(turbidityValLength != scanitizerValLength){
			logger.warn(paramName+" 的值存在xss攻击，过滤后安全值为："+value);
		}
		return  value;
	}

    /**
     *用户自定义规则 
     */
	private Defender useDefender(String url, String paramName) { 
		if(StringUtils.isBlank(url)){
			return config.getDefaultDefender();
		}
		if(StringUtils.isBlank(paramName)){
			return null;
		}
		//处理  paramName 取出 member[0].id的bug
		String[] paramNameArry = StringUtils.split(paramName, "\\.");
		XssEscapeFilterRule urlRule = config.getUrlParamRule(url, paramNameArry[paramNameArry.length-1]); 
        /**获取过滤规则*/
		if (urlRule == null) {
			//调用默认的过滤规则
			return  config.getDefaultDefender();
		}
		//启用过滤
		if(urlRule.isUseDefender()){
			  return urlRule.getDefender();
		} 
		return  null;
	}

	public static XssEscapeFilterConfig getConfig() {
		return config;
	}

	public static void setConfig(XssEscapeFilterConfig config) {
		XssEscapeFilter.config = config;
	} 
	 
	
	 
}
