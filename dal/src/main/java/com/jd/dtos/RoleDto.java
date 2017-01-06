package com.jd.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by renchao on 2016/12/30.
 */
public class RoleDto implements Serializable {

    private static final long serialVersionUID = -6591303655458986707L;
    private Long id;

    private String name;

    private String value;

    private Boolean isSystem;

    private String description;

    private Date createDate;

    private Date modifyDate;

    private Boolean deleted;

    private List<Long> PermissionIds;//权限ID

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<Long> getPermissionIds() {
        return PermissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        PermissionIds = permissionIds;
    }
}