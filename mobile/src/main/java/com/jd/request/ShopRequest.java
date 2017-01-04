package com.jd.request;

import java.io.Serializable;

/**
 * Created by ellen on 2016/12/26.
 */
public class ShopRequest implements Serializable{

    private static final long serialVersionUID = -110874734413615445L;

    private Long id;

    private String cityCode;

    private Long curioCityId;

    private String address;

    private String scope;

    private String contact;

    private String shopHours;

    private String name;

    private String position;

    private Integer status;//店铺状态  0 待审核 1 正常 2 其他 4 关闭

    private Long[] tagIds;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private String slogans;//簡介

    private String[] shopViews;//店鋪照片

    private String cid;//營業執照

    private String banner;//店鋪門頭


    public String[] getShopViews() {
        return shopViews;
    }

    public void setShopViews(String[] shopViews) {
        this.shopViews = shopViews;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Long getCurioCityId() {
        return curioCityId;
    }

    public void setCurioCityId(Long curioCityId) {
        this.curioCityId = curioCityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getShopHours() {
        return shopHours;
    }

    public void setShopHours(String shopHours) {
        this.shopHours = shopHours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long[] getTagIds() {
        return tagIds;
    }

    public void setTagIds(Long[] tagIds) {
        this.tagIds = tagIds;
    }

    public String getSlogans() {
        return slogans;
    }

    public void setSlogans(String slogans) {
        this.slogans = slogans;
    }
}
