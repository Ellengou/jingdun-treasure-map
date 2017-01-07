package com.jd.request;

import com.jd.utils.StringUtil;

/**
 * Created by ellen on 2016/12/26.
 */
public class AccountListRequest {

    private String roleIds;
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
        if (StringUtil.isNotBlank(this.status) && status != "") {
            if (Integer.valueOf(status) == 1)
                enable = true;
            else if (Integer.valueOf(status) == 0)
                enable = false;
            else if (Integer.valueOf(status) == -1)
                enable = null;
            else
                enable = null;
        } else
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
        if (StringUtil.isNotBlank(roleIds) && roleIds.equals("-1"))
            roleIds = null;
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

}
