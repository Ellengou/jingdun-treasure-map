package com.jd.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by renchao on 2016/12/30.
 */
public class RoleMenuRoleResourceResponse implements Serializable {

    private String name;
    private Long id;
    private List<ResourceResponse> resource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ResourceResponse> getResource() {
        return resource;
    }

    public void setResource(List<ResourceResponse> resource) {
        this.resource = resource;
    }
}
