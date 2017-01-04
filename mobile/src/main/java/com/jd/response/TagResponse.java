package com.jd.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by ellen on 2016/12/26.
 */
public class TagResponse {

    private Long id;
    private String name;
    @JsonIgnore
    private String desc;
    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
