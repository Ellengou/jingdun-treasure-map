package com.jd.response;

import com.jd.utils.NumberUtils;

/**
 * Created by ellen on 2016/12/26.
 */
public class ShopListResponse {

    private Long id;

    private Long regionId;

    private String cityCode;

    private Long curioCityId;

    private String province;

    private String city;

    private String address;

    private String scope;

    private String contact;

    private String domain;

    private String shopHours;

    private Long bannerId;

    private Long avatarId;

    private String name;

    private String fullName;

    private String tagsName;

    private Long viewedNumber;

    private Long storedNumer;

    private String curioCityName;

    private String position;

    private String status;

    //0 待审核 1 正常 2 其他 4 关闭
    public String getStatus() {
        if (status != null && NumberUtils.isNumber(status))
            switch (Integer.valueOf(status)) {
                case 0:
                    status = "待审核";
                    break;
                case 1:
                    status = "正常";
                    break;
                case 4:
                    status = "已关闭";
                    break;
                default:
                    status = "其他";
                    break;
            }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getShopHours() {
        return shopHours;
    }

    public void setShopHours(String shopHours) {
        this.shopHours = shopHours;
    }

    public Long getBannerId() {
        return bannerId;
    }

    public void setBannerId(Long bannerId) {
        this.bannerId = bannerId;
    }

    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTagsName() {
        return tagsName;
    }

    public void setTagsName(String tagsName) {
        this.tagsName = tagsName;
    }

    public Long getViewedNumber() {
        return viewedNumber;
    }

    public void setViewedNumber(Long viewedNumber) {
        this.viewedNumber = viewedNumber;
    }

    public Long getStoredNumer() {
        return storedNumer;
    }

    public void setStoredNumer(Long storedNumer) {
        this.storedNumer = storedNumer;
    }

    public String getCurioCityName() {
        return curioCityName;
    }

    public void setCurioCityName(String curioCityName) {
        this.curioCityName = curioCityName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
