package com.jd.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Ellen.
 * @date 2016/12/27.
 * @since 1.0.
 * com.jd.dtos .by jingdun.tech.
 */
public class ItemDto implements Serializable {

    private static final long serialVersionUID = -5939602412659390257L;

    private Long id;

    private Long businessId;

    private String businessType;

    private String name;

    private BigDecimal price;

    private BigDecimal inventory;

    private String desc;

    private BigDecimal oriPrice;

    private Long categoryId;

    private Integer sequence;

    private Long coverId;

    private String unit;

    private String type;

    private Boolean marketAble;

    private Byte channelWeb;

    private Byte channelShop;

    private String code;

    private String pinyin;

    private BigDecimal purchasePrice;

    private String spec;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Boolean isRecommend;

    private Boolean isAssessInventory;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

    private String key;

    private String tagName;

    private String shopName;

    private String path;

    private String remark;

    private Long storedNumer;

    private Long viewedNumber;

    private Long support;

    private Long against;

    private Long evaluations;

    private String years;

    private String certificate;

    private String material;

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getSupport() {
        return support;
    }

    public void setSupport(Long support) {
        this.support = support;
    }

    public Long getAgainst() {
        return against;
    }

    public void setAgainst(Long against) {
        this.against = against;
    }

    public Long getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(Long evaluations) {
        this.evaluations = evaluations;
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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getInventory() {
        return inventory;
    }

    public void setInventory(BigDecimal inventory) {
        this.inventory = inventory;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material == null ? null : material.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public BigDecimal getOriPrice() {
        return oriPrice;
    }

    public void setOriPrice(BigDecimal oriPrice) {
        this.oriPrice = oriPrice;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Long getCoverId() {
        return coverId;
    }

    public void setCoverId(Long coverId) {
        this.coverId = coverId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Boolean getMarketAble() {
      return marketAble;
    }

    public void setMarketAble(Boolean marketAble) {
        this.marketAble = marketAble;
    }

    public Byte getChannelWeb() {
        return channelWeb;
    }

    public void setChannelWeb(Byte channelWeb) {
        this.channelWeb = channelWeb;
    }

    public Byte getChannelShop() {
        return channelShop;
    }

    public void setChannelShop(Byte channelShop) {
        this.channelShop = channelShop;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Boolean getIsAssessInventory() {
        return isAssessInventory;
    }

    public void setIsAssessInventory(Boolean isAssessInventory) {
        this.isAssessInventory = isAssessInventory;
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

}
