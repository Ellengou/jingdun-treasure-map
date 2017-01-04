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

import org.apache.commons.lang3.StringEscapeUtils;


/**
 * @author ellen
 * @desc 防xss注入
 * @time 2015/11/03
 * https://github.com/naver/lucy-xss-servlet-filter
 */
public class XssScanitizerFilterDefender implements Defender {

    @Override
    public void init(String[] values) {
    }

    /**
     * @param value String
     * @return String  清理掉有安全问题的内容：<script xx='xx'>xxx</script>
     */
    @Override
    public String doFilter(String value) {
        //&amp;lt;script &amp;gt;alert(&#39;1&#39;)&amp;lt;/script&amp;gt; --> &lt;script &gt;alert('1')&lt;/script&gt;
        value = StringEscapeUtils.unescapeHtml4(value);
        //&lt;script &gt;alert('1')&lt;/script&gt;  --> <script >alert('1')</script>
        value = StringEscapeUtils.unescapeHtml4(value);
        //<script >alert('1')</script> -- > null
        return XssSanitizerUtil.stripXSS(SanitizerCharUtil.sanitizerChar(value));
        //return new HTMLFilter(false).filter(value);
    }


}
