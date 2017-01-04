package com.jd.response;

/**
 * @author Ellen.
 * @date 2016/12/22.
 * @since 1.0.
 * com.jd.response .by jingdun.tech.
 */
public class RolesResponse {

    private Long id;

    private String name;

    private String value;

    private Boolean isSystem;

    private String description;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getSystem() {
        return isSystem;
    }

    public void setSystem(Boolean system) {
        isSystem = system;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
