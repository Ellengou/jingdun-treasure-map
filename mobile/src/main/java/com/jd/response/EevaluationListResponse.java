package com.jd.response;

import com.jd.core.utils.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ellen.
 * @date 2017/1/2.
 * @since 1.0.
 * com.jd.response .by jingdun.tech.
 */
public class EevaluationListResponse implements Serializable {

    private static final long serialVersionUID = 2965439681340753654L;
    private Long id;

    private Long userId;

    private String type;

    private Long businessId;

    private Double stars;

    private String content;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

    private Long curioCityId;

    private Long shopId;

    private Long itemId;

    private String nickName;

    private String trueName;

    private String mobile;

    private Long against;

    private Long support;

    private String views;

    private String[] view;

    public String[] getView() {
        if (StringUtils.isNotBlank(views) && views.contains(","))
            return views.split(",");
        return view;
    }

    public void setView(String[] view) {
        this.view = view;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Long getCurioCityId() {
        return curioCityId;
    }

    public void setCurioCityId(Long curioCityId) {
        this.curioCityId = curioCityId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getAgainst() {
        return against;
    }

    public void setAgainst(Long against) {
        this.against = against;
    }

    public Long getSupport() {
        return support;
    }

    public void setSupport(Long support) {
        this.support = support;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }
}
