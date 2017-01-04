package com.jd.response;

import com.jd.utils.NumberUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ellen.
 * @date 2016/12/29.
 * @since 1.0.
 * com.jd.response .by jingdun.tech.
 */
public class CurioCityResponse implements Serializable{

    private static final long serialVersionUID = 8947368404127837823L;

    private Long id;

    private String regionId;

    private String cityCode;

    private Long curioCityId;

    private String province;

    private String city;

    private String address;

    private String scope;

    private String contact;

    private String domain;

    private String shopHours;

    private String bannerId;

    private String avatarId;

    private String name;

    private String fullName;

    private Boolean visible;

    private String brandId;

    private String wechat;

    private String weibo;

    private Double stars;

    private String position;

    private String slogans;

    private String distribution;

    private String signboards;

    private String wechatCode;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

    private String tagsName;

    private String tagId;

    private Long viewedNumber;

    private Long storedNumer;

    private String curioCityName;

    private String tagsId;//标签ID集合

    private String banner;

    private String shopViews[];


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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String[] getShopViews() {
        return shopViews;
    }

    public void setShopViews(String[] shopViews) {
        this.shopViews = shopViews;
    }

    public String getTagsId() {
        return tagsId;
    }

    public void setTagsId(String tagsId) {
        this.tagsId = tagsId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
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

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSlogans() {
        return slogans;
    }

    public void setSlogans(String slogans) {
        this.slogans = slogans;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getSignboards() {
        return signboards;
    }

    public void setSignboards(String signboards) {
        this.signboards = signboards;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getWechatCode() {
        return wechatCode;
    }

    public void setWechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
    }
}
