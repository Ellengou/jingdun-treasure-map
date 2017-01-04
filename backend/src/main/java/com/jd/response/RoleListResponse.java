package com.jd.response;

import java.io.Serializable;

/**
 * @author Ellen.
 * @date 2016/12/23.
 * @since 1.0.
 * com.jd.response .by jingdun.tech.
 */
public class RoleListResponse implements Serializable{

    private static final long serialVersionUID = 3741420371250573465L;

    private Long id;

    private String name;

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
