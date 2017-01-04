package com.jd.response;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Ellen.
 * @date 2016/12/28.
 * @since 1.0.
 * com.jd.response .by jingdun.tech.
 */
public class ItemResponse implements Serializable{

    private static final long serialVersionUID = -3743874672044795909L;

    private Long id;

    private Long businessId;

    private String name;

    private BigDecimal price;

    private BigDecimal inventory;

    private String desc;

    private String dynasty;

    private Long categoryId;

    private String spec;

    private String material;

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
