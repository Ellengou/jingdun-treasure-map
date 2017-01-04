package com.jd.request;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Ellen.
 * @date 2016/12/28.
 * @since 1.0.
 * com.jd.request .by jingdun.tech.
 */
public class ItemRequest implements Serializable{

    private static final long serialVersionUID = -6528588845533018333L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long businessId;

    private String name;

    private BigDecimal price;

    private BigDecimal inventory;

    private String desc;

    private String dynasty;

    private Long categoryId;

    private String spec;

    private String material;

    private Boolean marketAble;

    private String cid;//商品证件照

    private String[] itemViews;//商品照片

    private Long[] ids;//多选操作ID 数组

    private String key;//搜索关键字

    public Long[] getIds() {
        return ids;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
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

    public Boolean getMarketAble() {
        return marketAble;
    }

    public void setMarketAble(Boolean marketAble) {
        this.marketAble = marketAble;
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
