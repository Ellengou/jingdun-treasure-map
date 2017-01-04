package com.jd.entity.user;

import com.jd.base.BaseDomain;

public class UserTag extends BaseDomain {
    private Long tagId;

    private Long userId;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}