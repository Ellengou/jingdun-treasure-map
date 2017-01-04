package com.jd.dtos;

import java.io.Serializable;

/**
 * @author Ellen.
 * @date 2016/12/23.
 * @since 1.0.
 * com.jd.dtos .by jingdun.tech.
 */
public class AccountListDto implements Serializable {

    private static final long serialVersionUID = -6176236763923804266L;

    private Long id;

    private String username;

    private Long userId;

    private String type;

    private Boolean enable;

    private Boolean locked;

    private String roleName;

    private String roleIds;

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
