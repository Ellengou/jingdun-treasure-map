package com.jd.core.exceptions;

/**
 * Created by Ellen on 2016/12/4.
 */
public class XException extends Exception {
    private String code;
    private String message;


    public XException(String code, String message) {
        this(code, message, new Throwable());
    }

    public XException(String code, String message, String internalMessage) {
        this(code, message, internalMessage, null);
    }

    public XException(String code, String message, Throwable throwable) {
        this(code, message, throwable.getMessage(), throwable);
    }

    public XException(String code, String message, String internalMessage, Throwable throwable) {
        super("[" + code + "] - [" + message +"]" + internalMessage, throwable);
        this.message = message;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
