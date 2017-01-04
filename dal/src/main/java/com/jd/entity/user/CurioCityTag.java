package com.jd.entity.user;

import com.jd.base.BaseDomain;

public class CurioCityTag extends BaseDomain {
    private Long tagId;

    private Long shopId;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}