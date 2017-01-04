package com.jd.core.filter;

/**
 * Created by ellen on 2015/12/23.
 */
public class XssStringTrimDefender implements Defender {
    @Override
    public void init(String[] values) {

    }

    @Override
    public String doFilter(String value) {
        return value.trim();
    }
}
