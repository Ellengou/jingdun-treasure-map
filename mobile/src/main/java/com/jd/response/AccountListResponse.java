package com.jd.response;

import java.io.Serializable;

/**
 * @author Ellen.
 * @date 2016/12/23.
 * @since 1.0.
 * com.jd.response .by jingdun.tech.
 */
public class AccountListResponse implements Serializable{

    private static final long serialVersionUID = -3422802529515194125L;
    private Long id;

    private String username;

    private Long userId;

    private String type;

    private Boolean enable;

    private Boolean locked;

    private String roleName;

    private String roleIds;

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

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

}
