package com.jd.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Taken from http://ricardozuasti.com/2012/stronger-anti-cross-site-scripting-xss-filter-for-java-web-apps/
 */
public class XssSanitizerUtil {

	private static Logger logger  =  LoggerFactory.getLogger(XssSanitizerUtil.class);
	
	private static List<Pattern> XSS_INPUT_PATTERNS = new ArrayList<Pattern>();
    
	static {
			// Avoid anything between script tags
			XSS_INPUT_PATTERNS.add(Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE));

			// avoid iframes
			XSS_INPUT_PATTERNS.add(Pattern.compile("<iframe(.*?)>(.*?)</iframe>", Pattern.CASE_INSENSITIVE));

			// Avoid anything in a src='...' type of expression
			//XSS_INPUT_PATTERNS.add(Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

			//XSS_INPUT_PATTERNS.add(Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

			//XSS_INPUT_PATTERNS.add(Pattern.compile("src[\r\n]*=[\r\n]*([^>]+)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

			// Remove any lonesome </script> tag
			XSS_INPUT_PATTERNS.add(Pattern.compile("</script>", Pattern.CASE_INSENSITIVE));

			// Remove any lonesome <script ...> tag
			XSS_INPUT_PATTERNS.add(Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

			// Avoid eval(...) expressions
			XSS_INPUT_PATTERNS.add(Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

			// Avoid expression(...) expressions
			XSS_INPUT_PATTERNS.add(Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

			// Avoid background=expressions expressions
			XSS_INPUT_PATTERNS.add(Pattern.compile("background(-image)?\\s*?(:|=)?\\s*?url\\(.*?\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
 
			// Avoid alert(...) expressions
			XSS_INPUT_PATTERNS.add(Pattern.compile("alert\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

			// Avoid javascript:... expressions
			XSS_INPUT_PATTERNS.add(Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE));

			// Avoid vbscript:... expressions
			XSS_INPUT_PATTERNS.add(Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE));

			// Avoid onload= expressions
			XSS_INPUT_PATTERNS.add(Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
	}

	/**
	 * This method takes a string and strips out any potential script injections.
	 *
	 * @param value
	 * @return String - the new "sanitized" string.
	 */
	public static String stripXSS(String value) {

		try {

			if (value != null) {
				// Avoid null characters
				value = value.replaceAll("\0", "");

				// test against known XSS input patterns
				for (Pattern xssInputPattern : XSS_INPUT_PATTERNS) {
					value = xssInputPattern.matcher(value).replaceAll("");
				}
			}

		} catch (Exception ex) {
			logger.error("Could not strip XSS from value = " + value + " | ex = " + ex.getMessage()); 
		} 
		return value;
	}
	
 

}