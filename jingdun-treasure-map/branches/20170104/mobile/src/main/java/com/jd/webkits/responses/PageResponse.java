package com.jd.webkits.responses;

import java.io.Serializable;

/**
 * Created by Jintao on 2015/6/17.
 */
public class PageResponse implements Serializable{


    private static final long serialVersionUID = -4791781435470554597L;
    /**
     * 总条数
     */
    private Integer totalCount;



    /**
     * 当前第几页
     */
    private Integer currentPage;

    /**
     * 每页多少条
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer pageCount = 0;
    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
}
