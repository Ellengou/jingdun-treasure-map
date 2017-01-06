package com.jd.response;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jd.utils.StringUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Ellen.
 * @date 2016/12/28.
 * @since 1.0.
 * com.jd.response .by jingdun.tech.
 */
public class ItemResponse implements Serializable {

    private static final long serialVersionUID = -3743874672044795909L;

    private Long id;

    @JsonIgnore
    private Long businessId;

    @JsonIgnore
    private String businessType;

    private String name;

    private BigDecimal price;

    private BigDecimal inventory;

    private String desc;

    @JsonIgnore
    private BigDecimal oriPrice;

    @JsonIgnore
    private Long categoryId;

    private Integer sequence;

    @JsonIgnore
    private Long coverId;

    private String unit;

    @JsonIgnore
    private String type;

    private Boolean marketAble;

    @JsonIgnore
    private Byte channelWeb;

    @JsonIgnore
    private Byte channelShop;

    private String code;

    @JsonIgnore
    private String pinyin;

    @JsonIgnore
    private BigDecimal purchasePrice;

    private String spec;

    @JsonIgnore
    private BigDecimal minPrice;

    @JsonIgnore
    private BigDecimal maxPrice;

    @JsonIgnore
    private Boolean isRecommend;

    @JsonIgnore
    private Boolean isAssessInventory;

    private Date createTime;

    @JsonIgnore
    private Date updateTime;

    private Boolean deleted;

    @JsonIgnore
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

    private String certificate;//商品证件照

    private String material;

    private String[] itemViews;//商品照片

    private List<EevaluationListResponse> eevaluationList;

    public List<EevaluationListResponse> getEevaluationList() {
        return eevaluationList;
    }

    public void setEevaluationList(List<EevaluationListResponse> eevaluationList) {
        this.eevaluationList = eevaluationList;
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
        this.businessType = businessType;
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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String[] getItemViews() {
        if (StringUtil.isNotBlank(path) && path.contains(","))
            return path.split(",");
        return new String[]{path};
    }

    public void setItemViews(String[] itemViews) {
        this.itemViews = itemViews;
    }


    public static void main(String[] args) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId(1l);
        itemResponse.setAgainst(100l);
        itemResponse.setSupport(1090l);
        itemResponse.setBusinessId(100l);
        itemResponse.setCategoryId(12l);
        itemResponse.setCertificate("http://sda");
        itemResponse.setCode("121029");
        itemResponse.setCreateTime(new Date());
        itemResponse.setEvaluations(12189l);
        itemResponse.setInventory(new BigDecimal(219.45));
        itemResponse.setYears("1988");
        itemResponse.setTagName("文玩 玉器");
        itemResponse.setViewedNumber(12131231l);
        itemResponse.setRemark("商品备注");

        System.out.println(JSON.toJSON(itemResponse));

    }
}