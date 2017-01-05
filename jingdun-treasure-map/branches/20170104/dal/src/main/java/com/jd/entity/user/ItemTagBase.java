package com.jd.entity.user;

import com.jd.base.BaseDomain;

public class ItemTagBase extends BaseDomain {
    private Long itemTagId;

    private Long itemId;

    public Long getItemTagId() {
        return itemTagId;
    }

    public void setItemTagId(Long itemTagId) {
        this.itemTagId = itemTagId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}