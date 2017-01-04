package com.jd.common.exception;

/**
 * Copyright: Copyright (c) 2015 <br/>
 * ClassName: BusinessException.java
 * 
 * @Description: 业务异常
 * @version: v1.0.0
 * @author: 张宝鑫
 * @date: 2015-7-2 19:59:12
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = 4457113998128457652L;

    private String            code;

    private String            msg;

    private ErrorContent      errorCode;

    public BusinessException(long l, String message){
        super(message);
        this.msg = message;
    }

    public BusinessException(Throwable e){
        super(e);
    }

    public BusinessException(String code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(ErrorContent errorCode){
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ErrorContent getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorContent errorCode) {
        this.errorCode = errorCode;
    }

}
