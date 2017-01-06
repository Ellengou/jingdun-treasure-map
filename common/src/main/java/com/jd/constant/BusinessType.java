package com.jd.constant;

/**
 * Created by ellen on 2016/12/26.
 */
public class BusinessType {

    /**
     * 商户类型
     */
    public enum Type {
        SHOP(1, "商户");
        private String desc;
        private Integer code;

        Type(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 店铺状态
     */
    public enum ShopStatus {
        AUDIT(1, "待审核"), NORMAL(2, "正常"), OTHER(3, "其他"), CLOSE(4, "已关闭");
        private String desc;
        private Integer code;

        ShopStatus(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 古玩城状态
     */
    public enum CurioCityStatus {
        AUDIT(0, "待审核"), NORMAL(1, "正常"), OTHER(2, "其他"), CLOSE(4, "已关闭");
        private String desc;
        private Integer code;

        CurioCityStatus(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 照片类型
     */
    public enum PictureType {
        SHOP_ID(1, "营业执照"), SHOP_BANNER(1, "店铺门头照"), SHOP_VIEW(2, "店铺照片"), ITEM(3, "商品照片"), ITEM_ID(4, "商品证书照"), CURIO_BANNER(5, "古玩城门头照"), EVAL(6, "评价图片");
        private String desc;
        private Integer code;

        PictureType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 收藏类型
     */
    public enum FavoritesType {
        SHOP(0, "商家"), CURIO(1, "古玩城"), ITEM(2, "商品");
        private String desc;
        private Integer code;

        FavoritesType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

}
