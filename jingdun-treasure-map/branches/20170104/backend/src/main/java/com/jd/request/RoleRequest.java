package com.jd.request;

import java.util.Date;

/**
 * Created by renchao on 2016/12/30.
 */
public class RoleRequest {

    private Long id;

    private String name;

    private String value;

    private Boolean isSystem;

    private String description;

    private Date createDate;

    private Date modifyDate;

    private Boolean deleted;
    private Long meunIds[];//菜单ID

    private Long resourceIds[];//资源ID

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

    public Long[] getMeunIds() {
        return meunIds;
    }

    public void setMeunIds(Long[] meunIds) {
        this.meunIds = meunIds;
    }

    public Long[] getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Long[] resourceIds) {
        this.resourceIds = resourceIds;
    }
}
