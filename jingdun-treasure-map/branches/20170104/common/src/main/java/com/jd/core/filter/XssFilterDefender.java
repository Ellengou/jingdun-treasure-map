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


import com.nhncorp.lucy.security.xss.XssFilter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ellen
 * @desc 防xss注入
 * @time 2015/11/03
 * https://github.com/naver/lucy-xss-servlet-filter
 */
public class XssFilterDefender implements Defender {
    private XssFilter filter;

    /**
     * @param values String[]
     * @return void
     */
    @Override
    public void init(String[] values) {
        if (values == null || values.length == 0) {
            filter = XssFilter.getInstance();
        } else {
            switch (values.length) {
                case 1:
                    if (isBoolean(values[0])) {
                        filter = XssFilter.getInstance(convertBoolean(values[0]));
                    } else {
                        filter = XssFilter.getInstance(values[0]);
                    }
                    break;
                case 2:
                    filter = XssFilter.getInstance(values[0], convertBoolean(values[1]));
                    break;
                default:
                    filter = null;
                    break;
            }
        }
    }

    /**
     * @param value String
     * @return String
     */
    @Override
    public String doFilter(String value) {
        return filter.doFilter(SanitizerCharUtil.sanitizerChar(value));
    }

    /**
     * @param value String
     * @return boolean
     */
    private boolean isBoolean(String value) {
        return StringUtils.equalsIgnoreCase(value, "true") || StringUtils.equalsIgnoreCase(value, "false");
    }

    /**
     * @param value String
     * @return boolean
     */
    private boolean convertBoolean(String value) {
        return StringUtils.equalsIgnoreCase(value, "true") ? true : false;
    }
}
