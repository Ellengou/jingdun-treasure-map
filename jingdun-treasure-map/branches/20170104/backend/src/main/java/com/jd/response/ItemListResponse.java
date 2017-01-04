package com.jd.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ellen on 2016/12/28.
 */
public class ItemListResponse implements Serializable{

    private Long id;

    private Long businessId;

    private String businessType;

    private String name;

    private BigDecimal price;

    private BigDecimal inventory;

    private String material;

    private String desc;

    private String dynasty;

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

    private Long storedNumer;

    private Long viewedNumber;

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public BigDecimal getOriPrice() {
        return oriPrice;
    }

    public void setOriPrice(BigDecimal oriPrice) {
        this.oriPrice = oriPrice;
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
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        this.code = code;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
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

    public Boolean getRecommend() {
        return isRecommend;
    }

    public void setRecommend(Boolean recommend) {
        isRecommend = recommend;
    }

    public Boolean getAssessInventory() {
        return isAssessInventory;
    }

    public void setAssessInventory(Boolean assessInventory) {
        isAssessInventory = assessInventory;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public Long getStoredNumer() {
        return storedNumer;
    }

    public void setStoredNumer(Long storedNumer) {
        this.storedNumer = storedNumer;
    }

    public Long getViewedNumber() {
        return viewedNumber;
    }

    public void setViewedNumber(Long viewedNumber) {
        this.viewedNumber = viewedNumber;
    }

    private String cid;//商品证件照

    private String[] itemViews;//商品照片

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String[] getItemViews() {
        return itemViews;
    }

    public void setItemViews(String[] itemViews) {
        this.itemViews = itemViews;
    }
}
