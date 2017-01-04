package com.jd.core.filter;

import java.util.Map;


/**
 * @author ellen
 * @desc 防xss注入, URL过滤器
 * @time 2015/11/03
 * https://github.com/naver/lucy-xss-servlet-filter
 */
public class XssURLEscapeFilterRule {

    /**
     * 过滤规则
     */
    private Map<String, XssEscapeFilterRule> escapeFilterRuleMap;

    /*是否启用过滤安全*/
    private boolean useDisable;

    public Map<String, XssEscapeFilterRule> getEscapeFilterRuleMap() {
        return escapeFilterRuleMap;
    }

    public void setEscapeFilterRuleMap(
            Map<String, XssEscapeFilterRule> escapeFilterRuleMap) {
        this.escapeFilterRuleMap = escapeFilterRuleMap;
    }

    public boolean isUseDisable() {
        return useDisable;
    }

    public void setUseDisable(boolean useDisable) {
        this.useDisable = useDisable;
    }


}
