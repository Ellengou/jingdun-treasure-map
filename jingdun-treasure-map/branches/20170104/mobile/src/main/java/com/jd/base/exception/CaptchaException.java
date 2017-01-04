package com.jd.base.exception;


import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("serial")
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg){
        super(msg);
    }

    public CaptchaException(String msg, Throwable t){
        super(msg, t);
    }
}
