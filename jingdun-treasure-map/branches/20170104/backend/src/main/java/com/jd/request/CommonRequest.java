package com.jd.request;

import com.alibaba.fastjson.JSONObject;
import com.jd.common.mybatis.Pager;

import java.io.Serializable;

/**
 * @author Ellen.
 * @date 2016/12/1.
 * @since 1.0.
 * com.jd.request .by jingdun.tech.
 * 查询参数封装
 */
public class CommonRequest<T> implements Serializable {
    private static final long serialVersionUID = -7841481836554110824L;
    private String key;
    private String areaCode;
    private String type;
    private T param;
    private Pager pager;

    public CommonRequest(){
        super();
    }
    public CommonRequest(T param){
        this.param = param;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getParam(Class<T> clazz) {
        if (param == null)
            return null;
        return  JSONObject.parseObject(String.valueOf(param),clazz);
    }

    public void setParam(T param) {
        this.param = param;
    }
}
