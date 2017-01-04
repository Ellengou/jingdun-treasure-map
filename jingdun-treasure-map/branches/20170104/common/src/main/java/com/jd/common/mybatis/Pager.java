package com.jd.common.mybatis;

import java.io.Serializable;

/**
 * @author Ellen.
 * @date 2016/12/1.
 * @since 1.0.
 * com.jd.common.mybatis .by jingdun.tech.
 */
public class Pager implements Serializable {

    private static final long serialVersionUID = -644006827112956016L;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private Long total = 0L;
    private Integer pages = 0;

    public Pager() {

    }

    public Pager(Integer pageNum, Integer pageSize, Long total, Integer pages) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = pages;
    }

    public Pager(Integer pageNum, Integer pageSize, Long total) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
    }

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

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
