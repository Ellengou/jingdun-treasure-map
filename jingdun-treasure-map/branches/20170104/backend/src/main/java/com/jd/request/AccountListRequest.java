package com.jd.request;

import com.jd.utils.StringUtil;

/**
 * Created by ellen on 2016/12/26.
 */
public class AccountListRequest {

    private String roleIds;
    private Boolean locked;
    private String key;
    private String status;
    private Boolean enable;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Boolean getEnable() {
        if (StringUtil.isNotBlank(this.status)) {
            enable = Boolean.getBoolean(status);
            if (!enable && Integer.valueOf(status).intValue()!=0)
                enable = null;
        }else
            enable = null;
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
