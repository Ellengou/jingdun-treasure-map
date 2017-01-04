package com.jd.face;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jd.common.mybatis.Pager;
import com.jd.entity.user.CityArea;
import com.jd.response.CityAreaResponse;
import com.jd.utils.DozerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/22.
 */
public class JsonResult {

    private String code = "0";

    private String desc = "OK";

    private Result result;

    public JsonResult() {
        this.result = new Result();
    }

    public JsonResult(Object result) {
        this.result = new Result(result);
    }

    public JsonResult(Pager pager, Object result) {
        this.result = new Result(pager, result);
    }

    public JsonResult(String code, Result result) {
        this.code = code;
        this.result = result;
    }

    public JsonResult(String code, String desc, Result result) {
        this.code = code;
        this.result = result;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
