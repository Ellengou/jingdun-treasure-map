package com.jd.response;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ellen.
 * @date 2016/12/29.
 * @since 1.0.
 * com.jd.response .by jingdun.tech.
 */
public class ShopAndCurioResponse implements Serializable{

    private static final long serialVersionUID = 7412972541724011313L;
    private List<ShopResponse> shops;
    private List<CurioCityResponse> curios;

    public ShopAndCurioResponse() {
    }

    public List<ShopResponse> getShops() {
        return shops;
    }

    public void setShops(List<ShopResponse> shops) {
        this.shops = shops;
    }

    public List<CurioCityResponse> getCurios() {
        return curios;
    }

    public void setCurios(List<CurioCityResponse> curios) {
        this.curios = curios;
    }
}
