package com.jd.face;

import com.jd.common.mybatis.Pager;

/**
 * Created by Administrator on 2016/11/24.
 */
public class Result {
    private Pager pager;
    private Object model;

    public Result() {
    }

    public Result(Object result) {
        this.model = result;
    }

    public Result(Pager pager, Object result) {
        this.pager = pager;
        this.model = result;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object result) {
        this.model = result;
    }
}
