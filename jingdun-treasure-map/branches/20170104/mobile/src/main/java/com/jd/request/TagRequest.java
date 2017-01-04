package com.jd.request;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by renchao on 2016/12/28.
 */
public class TagRequest implements Serializable {

    private static final long serialVersionUID = 6080745441556450277L;

    private Long id;

    private String name;

    private String desc;

    private Long businessId;

    private String key;

    private String[] tags;// 格式 id,sort

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }
}
