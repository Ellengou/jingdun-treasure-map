package com.jd.webkits.requests;

import javax.validation.constraints.NotNull;

/**
 * Created by nt on 2015-05-28.
 */
public class Request {

    @NotNull(message = "F_WEBKITS_VALIDATION_2001")
    private Long shopId;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
