package com.jd.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author Ellen.
 * @date 2016/12/6.
 * @since 1.0.
 * com.jd.exception .by jingdun.tech.
 */
public class CodeNullException extends AuthenticationException {

    private static final long serialVersionUID = 6716197805902804154L;

    public CodeNullException(String desc){
        super(desc);
    }
}
