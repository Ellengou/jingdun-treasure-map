package com.jd.response;

import com.jd.utils.NumberUtils;

import java.io.Serializable;

/**
 * @author Ellen.
 * @date 2016/12/29.
 * @since 1.0.
 * com.jd.response .by jingdun.tech.
 */
public class CurioCityListResponse implements Serializable{

    private static final long serialVersionUID = -5371342028334942486L;

    private Long id;

    private String province;

    private String city;

    private String address;

    private String contact;

    private String domain;

    private String shopHours;

    private String name;

    private String fullName;

    private String scope;

    private Double stars;

    private String position;

    private String status;

    //古玩城状态 0 待审核 1 正常 2 其他 4 关闭
    public String getStatus() {
        if (status!=null && NumberUtils.isNumber(status)){
            switch (Integer.valueOf(status)){
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
            }
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String tagsName;

    public String getTagsName() {
        return tagsName;
    }

    public void setTagsName(String tagsName) {
        this.tagsName = tagsName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
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

}
