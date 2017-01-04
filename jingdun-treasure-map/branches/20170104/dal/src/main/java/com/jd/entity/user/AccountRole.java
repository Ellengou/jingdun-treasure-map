package com.jd.entity.user;

import com.jd.base.BaseDomain;

public class AccountRole extends BaseDomain {
    private Long accountId;

    private Long roleId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}