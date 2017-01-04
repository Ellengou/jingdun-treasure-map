package com.jd.request;

/**
 * @author Ellen.
 * @date 2016/12/29.
 * @since 1.0.
 * com.jd.request .by jingdun.tech.
 */
public class CurioCityListRequest {

    private String cityCode;

    private Integer status;

    private String tagId;

    private String key;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
