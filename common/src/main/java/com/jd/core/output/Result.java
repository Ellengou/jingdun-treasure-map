package com.jd.core.output;

/**
 * Created by Ellen on 2016/12/4.
 */
public class Result {

    public Result() {

    }

    public Result(String code, String description) {
        this.code = code;
        this.desc = description;
    }

    public Result(String code, String description, Object result) {
        this.code = code;
        this.desc = description;
        this.result = result;
    }

    private String code;

    private String desc;

    private Object result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String description) {
        this.desc = description;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
