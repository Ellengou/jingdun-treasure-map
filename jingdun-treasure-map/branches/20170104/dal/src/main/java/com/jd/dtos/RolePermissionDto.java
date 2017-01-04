package com.jd.dtos;

import java.io.Serializable;

/**
 * @author Ellen.
 * @date 2017/1/2.
 * @since 1.0.
 * com.jd.dtos .by jingdun.tech.
 */
public class RolePermissionDto implements Serializable{


    private static final long serialVersionUID = -3521718551688355328L;

    private Long id;
    private Integer level;
    private String name;
    private String menuIds;
    private String menuNames;
    private String buttonNames;
    private String buttonIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public String getMenuNames() {
        return menuNames;
    }

    public void setMenuNames(String menuNames) {
        this.menuNames = menuNames;
    }

    public String getButtonNames() {
        return buttonNames;
    }

    public void setButtonNames(String buttonNames) {
        this.buttonNames = buttonNames;
    }

    public String getButtonIds() {
        return buttonIds;
    }

    public void setButtonIds(String buttonIds) {
        this.buttonIds = buttonIds;
    }
}
