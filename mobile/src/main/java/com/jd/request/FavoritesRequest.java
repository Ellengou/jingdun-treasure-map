package com.jd.request;

/**
 * @author Ellen.
 * @date 2017/1/6.
 * @since 1.0.
 * com.jd.request .by jingdun.tech.
 */
public class FavoritesRequest {

    private Long id;

    private Long userId;

    private String type;

    private Long businessId;

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
}
