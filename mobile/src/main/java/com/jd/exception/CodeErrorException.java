package com.jd.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author Ellen.
 * @date 2016/12/6.
 * @since 1.0.
 * com.jd.exception .by jingdun.tech.
 */
public class CodeErrorException extends AuthenticationException {
    private static final long serialVersionUID = 3877991984856517335L;

    public CodeErrorException(String desc) {
        super(desc);
    }
}
