package com.jd.base;

import java.io.Serializable;


/**
 * @author Comsys-lanny
 * @ClassName: BaseDomain
 * @Description: domain父类，放置公用属性
 * @date 2015年5月6日 下午1:27:43 modified by dongxc on 2015/06/03 增加起始记录变量
 */

public class BaseDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private int pageNum = 1;

    private int pageSize = 10;

    private String key;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
