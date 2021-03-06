package com.jd.request;

import com.jd.utils.StringUtil;

/**
 * Created by renchao on 2017/1/5.
 */
public class UserListRequest {

    private Boolean locked;
    private String key;
    private String status;
    private Long tagId;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getLocked() {
        if (StringUtil.isNotBlank(this.status)) {
            locked = Boolean.getBoolean(status);
            if (!locked && Integer.valueOf(status).intValue()!=0)
                locked = null;
        }else
            locked = null;
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
