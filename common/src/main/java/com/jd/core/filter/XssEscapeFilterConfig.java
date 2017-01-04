package com.jd.core.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author ellen
 * @desc   防xss注入
 * @time  2015/11/13
 *    https://github.com/naver/lucy-xss-servlet-filter
 */
public class XssEscapeFilterConfig {

	private static final Logger LOG = LoggerFactory.getLogger(XssEscapeFilterConfig.class);
	
	private static final String DEFAULT_FILTER_RULE_FILENAME = "xss-servlet-filter-rule.xml";

	private static final String DEFAULT_FILTER_RULE_FILEPATH = "/security/xss/";
	
	/**
	 * shiro的url验证规则
	 */
	private AntPathMatcher antPathMatcher = new AntPathMatcher();
	/**
	 * url路由过滤规则
	 * Map<String1, Map<String2, XssEscapeFilterRule>>, 其中，String1为：url，String2为该url中的param， XssEscapeFilterRule为过滤规则
	 */
	private Map<String, XssURLEscapeFilterRule> urlRuleSetMap = new LinkedHashMap<String, XssURLEscapeFilterRule>();
	
	/**
	 * 全局的过滤规则
	 * 其中，String为该url中的param， XssEscapeFilterRule为过滤规则
	 */
	private Map<String, XssEscapeFilterRule> globalParamRuleMap = new LinkedHashMap<String, XssEscapeFilterRule>();
    /**
     * 过滤规则
     */
	private Map<String, Defender> defenderMap = new LinkedHashMap<String, Defender>();
	
	/**
	 * 默认过滤规则
	 */
	private Defender defaultDefender = null;

	/**
	 * Default Constructor
	 */
	public XssEscapeFilterConfig() throws IllegalStateException {
		this(DEFAULT_FILTER_RULE_FILEPATH+DEFAULT_FILTER_RULE_FILENAME);
	}

	/**
	 * Constructor
	 *
	 * @param filename String
	 */
	public XssEscapeFilterConfig(String filename) throws IllegalStateException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
			Element rootElement = builder.parse(is).getDocumentElement();

			// defender  加载保护模块
			addDefenders(rootElement);
			
			// defaultDefender  加载默认的保护模块
			addDefaultInfo(rootElement);

			// globalParam 加载系统全局参数的保护模块
			addGlobalParams(rootElement);

			// urlRule 加载url路由，处理具体业务
			addUrlRuleSet(rootElement);

		} catch (Exception e) {
			String message = String.format("Cannot parse the RequestParam configuration file [%s].", filename);
			throw new IllegalStateException(message, e);
		}
	}

	/**
	 * 加载默认的保护模块
	 * @param rootElement Element
	 * @return void
	 */
	private void addDefaultInfo(Element rootElement) {
		NodeList nodeList = rootElement.getElementsByTagName("default");
		if (nodeList.getLength() > 0) {
			Element element = (Element)nodeList.item(0);
			addDefaultInfoItems(element);
		}
	}

	/**
	 * @param element Element
	 * @return void
	 */
	private void addDefaultInfoItems(Element element) {
		NodeList nodeList = element.getElementsByTagName("defender");
		if (nodeList.getLength() > 0) {
			defaultDefender = defenderMap.get(nodeList.item(0).getTextContent());
			
			if (defaultDefender == null) {
				LOG.error("Error config 'Default defender': Not found '" + nodeList.item(0).getTextContent() + "'");
			}
		}
	}

	/**
	 * 加载系统全局的保护模块
	 * @param rootElement Element
	 * @return void
	 */
	private void addGlobalParams(Element rootElement) {
		NodeList nodeList = rootElement.getElementsByTagName("global");
		if (nodeList.getLength() > 0) {
			Element params = (Element)nodeList.item(0);
			NodeList paramNodeList = params.getElementsByTagName("params");
			
			if (paramNodeList.getLength() > 0) {
				globalParamRuleMap = createRequestParamRuleMap((Element)nodeList.item(0));
			}			
		}
	}

	/**
	 * @param rootElement Element
	 * @return void
	 */
	private void addUrlRuleSet(Element rootElement) {
		NodeList nodeList = rootElement.getElementsByTagName("url-rule");
		for (int i = 0; nodeList.getLength() > 0 && i < nodeList.getLength(); i++) {
			Element element = (Element)nodeList.item(i);
			addUrlRule(element);
		}
	}

	/**
	 * @param element Element
	 * @return void
	 */
	private void addUrlRule(Element element) { 
		String url = null; 
		NodeList nodeList = element.getElementsByTagName("url");
		if (nodeList.getLength() == 0) {
			return;
		}
		/**待过滤的url*/
		url = nodeList.item(0).getTextContent(); 
		if (StringUtils.isBlank(url)) {
			return;
		}
		XssURLEscapeFilterRule escapeFilterRule  = new XssURLEscapeFilterRule();
		/**是否启用过滤*/
		boolean disable = StringUtils.equalsIgnoreCase(((Element)nodeList.item(0)).getAttribute("disable"), "true") ? true : false;
		escapeFilterRule.setUseDisable(disable);
		 
        /**  url 请求参数过滤  */ 
        nodeList = element.getElementsByTagName("params");
        if (nodeList.getLength() > 0) {
        	escapeFilterRule.setEscapeFilterRuleMap(createRequestParamRuleMap((Element)nodeList.item(0)));
        } 
        String[] urlArr = url.split("\\,");
        for (String urlPath : urlArr) {
        	urlRuleSetMap.put(urlPath,escapeFilterRule); 			
		}
	}
 

	/**
	 * @param element Element
	 * @return Map<String, XssEscapeFilterRule>
	 */
	private Map<String, XssEscapeFilterRule> createRequestParamRuleMap(Element element) {
		Map<String, XssEscapeFilterRule> urlRuleMap = new LinkedHashMap<String, XssEscapeFilterRule>();

		NodeList nodeList = element.getElementsByTagName("param");
		for (int i = 0; nodeList.getLength() > 0 && i < nodeList.getLength(); i++) {
			Element eachElement = (Element)nodeList.item(i);
			String name = eachElement.getAttribute("name");
			boolean useDefender = StringUtils.equalsIgnoreCase(eachElement.getAttribute("useDefender"), "false") ? false : true;
			Defender defender = null;

			NodeList defenderNodeList = eachElement.getElementsByTagName("defender");
			if (defenderNodeList.getLength() > 0) {
				defender = defenderMap.get(defenderNodeList.item(0).getTextContent());
				
				if (defender == null) {
					LOG.error("Error config 'param defender': Not found '" + nodeList.item(0).getTextContent() + "'");
				}
			} else {
				defender = defaultDefender;
			}

			XssEscapeFilterRule urlRule = new XssEscapeFilterRule();
			urlRule.setName(name);
			urlRule.setUseDefender(useDefender);
			urlRule.setDefender(defender);  
			urlRuleMap.put(name, urlRule);
		}

		return urlRuleMap;
	} 

	/** 
	 * 加载保护模块
	 * @param rootElement Element
	 * @return void
	 */
	private void addDefenders(Element rootElement) {
		NodeList nodeList = rootElement.getElementsByTagName("defenders");

		if (nodeList.getLength() > 0) {
			Element element = (Element)nodeList.item(0);
			addDefender(element);
		}
	}

	/**
	 * @param element Element
	 * @return void
	 */
	private void addDefender(Element element) {
		NodeList nodeList = element.getElementsByTagName("defender");
		for (int i = 0; nodeList.getLength() > 0 && i < nodeList.getLength(); i++) {
			Element eachElement = (Element)nodeList.item(i);
			String name = getTagContent(eachElement, "name");
			String clazz = getTagContent(eachElement, "class");
			String[] args = getInitParams(eachElement);
			addDefender(name, clazz, args);
		}
	}

	/**
	 * @param name String
	 * @param clazz String
	 * @param args String[]
	 * @return void
	 */
	private void addDefender(String name, String clazz, String[] args) { 
		if (StringUtils.isBlank(name) || StringUtils.isBlank(clazz)) {
			String message = String.format("The defender's name('%s') or clazz('%s') is empty. This defender is ignored", name, clazz);
			LOG.warn(message);
			return;
		}
		try {
			Defender defender = (Defender)Class.forName(clazz.trim()).newInstance();
			defender.init(args);
			defenderMap.put(name, defender);
		} catch (InstantiationException e) {
			rethrow(name, clazz, e);
		} catch (IllegalAccessException e) {
			rethrow(name, clazz, e);
		} catch (ClassNotFoundException e) {
			rethrow(name, clazz, e);

		}
	}

	/**
	 * @param name String
	 * @param clazz String
	 * @param e Exception
	 * @return void
	 */
	private void rethrow(String name, String clazz, Exception e) {
		String message = String.format("Fail to add defender: name=%s, class=%s", name, clazz);
		throw new IllegalStateException(message, e);
	}

	/**
	 * @param eachElement Element
	 * @return String[]
	 */
	private String[] getInitParams(Element eachElement) {
		NodeList initParamNodeList = eachElement.getElementsByTagName("init-param");
		if (initParamNodeList.getLength() == 0) {
			return new String[]{};
		}
		Element paramValueElement = (Element)initParamNodeList.item(0);
		NodeList paramValueNodeList = paramValueElement.getElementsByTagName("param-value");
	
		String[] args = new String[paramValueNodeList.getLength()];
		for (int j = 0; paramValueNodeList.getLength() > 0 && j < paramValueNodeList.getLength(); j++) {
			args[j] = paramValueNodeList.item(j).getTextContent();
		}
		return args;
	}

	/**
	 * @param eachElement Element
	 * @param tagName String
	 * @return String
	 */
	private String getTagContent(Element eachElement, String tagName) {
		NodeList nodeList = eachElement.getElementsByTagName(tagName);
		if (nodeList.getLength() > 0) {
			return nodeList.item(0).getTextContent();
		}
		return "";
	}
 
	
	/**
	 * 获取参数的具体处理规则
	 * @param url String
	 * @param paramName String
	 * @return XssEscapeFilterRule
	 */
	public XssEscapeFilterRule getUrlParamRule(String url, String paramName) {
	
		Iterator<Entry<String, XssURLEscapeFilterRule>> iter = urlRuleSetMap.entrySet().iterator();
		while (iter.hasNext()) { 
			Entry<String, XssURLEscapeFilterRule> entry = iter.next();
			String pattern = entry.getKey(); 
			 //匹配url
			 if(antPathMatcher.match(pattern, url)){   	 
				 return getUrlRule(url, paramName, entry);  
		     }
		} 
		//默认启用全局
		return checkGlobalParamRule(paramName);
	}
    /**
     * 获取过滤规则
     * @param url
     * @param paramName
     * @param entry
     * @return
     */
	private XssEscapeFilterRule getUrlRule(String url, String paramName,
			Entry<String, XssURLEscapeFilterRule> entry) {
		XssURLEscapeFilterRule xssURLEscapeFilterRule;
		xssURLEscapeFilterRule = entry.getValue(); 
		 /**获取url过滤规则*/
		 if (xssURLEscapeFilterRule == null) {
			/*启用全局参数拦截*/
			return checkGlobalParamRule(paramName);
		 }
		 if(!xssURLEscapeFilterRule.isUseDisable()){ 
			 return null;
		 }
		 Map<String, XssEscapeFilterRule> urlParamRuleMap = xssURLEscapeFilterRule.getEscapeFilterRuleMap(); 
		 if (urlParamRuleMap == null) {
			 /*启用全局参数拦截*/
			 return  checkGlobalParamRule(paramName);
		 }   
		 /*param rule  通过参数滤规则*/  
		 return  checkParamRule(urlParamRuleMap, url, paramName);
	}
 
	/**
	 * @param paramName String
	 * @return XssEscapeFilterRule
	 */
	private XssEscapeFilterRule checkGlobalParamRule(String paramName) { 
		Iterator<Entry<String, XssEscapeFilterRule>> iter = globalParamRuleMap.entrySet().iterator();  
		while (iter.hasNext()) { 
			Entry<String, XssEscapeFilterRule> entry = iter.next(); 
			String patternParamName = entry.getKey(); 
			 //匹配url
			 if(antPathMatcher.match(patternParamName, paramName)){   	 
				return entry.getValue();
		     }
		}   
		return  null;
	}

	/**
	 * @param urlParamRuleMap Map<String, XssEscapeFilterRule>
	 * @param url String
	 * @param paramName String
	 * @return XssEscapeFilterRule
	 */
	private XssEscapeFilterRule checkParamRule(Map<String, XssEscapeFilterRule> urlParamRuleMap, String url, String paramName) {
		//1. url param name 通过请求参数取规则
		 Iterator<Entry<String, XssEscapeFilterRule>> iter = urlParamRuleMap.entrySet().iterator(); 
		 while (iter.hasNext()) { 
			Entry<String, XssEscapeFilterRule> entry = iter.next(); 
			String patternParamName = entry.getKey();  
			 if(antPathMatcher.match(patternParamName, paramName)){  
			   XssEscapeFilterRule xssURLEscapeFilterRule = entry.getValue(); 
			   return xssURLEscapeFilterRule != null && xssURLEscapeFilterRule.isUseDefender() ? xssURLEscapeFilterRule : null;
		     }
		}  
		return globalParamRuleMap.get(paramName) ; 
	}

   /**
    * 是否需要xss过滤
    * @param request
    * @return
    */
	public boolean addXSSFilter(ServletRequest request){
		 String path = ((HttpServletRequest)request).getRequestURI(); 
		 String method =  ((HttpServletRequest)request).getMethod();
		 if(StringUtils.equalsIgnoreCase(method, RequestMethod.POST.toString())){
			String _method = ((HttpServletRequest)request).getParameter("_method"); 
			method = StringUtils.isNotBlank(_method) ? _method : method;
		 }
        return checkXssFilter(method, path);
	}

	/**
	 * 检查是否需要添加过滤
	 * @param method
	 * @param path
	 * @return
	 */
	public boolean checkXssFilter(String method, String path) {
		if(StringUtils.equalsIgnoreCase(method, RequestMethod.GET.toString())){
			return false;
		}  
        Iterator<Entry<String, XssURLEscapeFilterRule>> iter = urlRuleSetMap.entrySet().iterator(); 
		while (iter.hasNext()) { 
			Entry<String, XssURLEscapeFilterRule> entry = iter.next(); 
			String pattern = entry.getKey();  
			XssURLEscapeFilterRule xssURLEscapeFilterRule = entry.getValue(); 
			if(antPathMatcher.match(pattern, path)){  
			   return xssURLEscapeFilterRule != null && xssURLEscapeFilterRule.isUseDisable() ? true : false;
		    }
		}  
        return true;
	};
	
	/**
	 * @return Map<String, Defender>
	 */
	public Map<String, Defender> getDefenderMap() {
		return defenderMap;
	}

	/**
	 * @return Defender
	 */
	public Defender getDefaultDefender() {
		return defaultDefender;
	}
	 
}
