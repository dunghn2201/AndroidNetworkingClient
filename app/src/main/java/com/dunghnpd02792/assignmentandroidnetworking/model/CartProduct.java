package com.dunghnpd02792.assignmentandroidnetworking.model;

/**
 * Created on 8/18/2020
 */
public class CartProduct {
    private String imgProductCart;
    private String tvNameProductCart;
    private String tvQuantityProductCart;
    private String tvPriceProductCart;

    public CartProduct(String imgProductCart, String tvNameProductCart, String tvQuantityProductCart, String tvPriceProductCart) {
        this.imgProductCart = imgProductCart;
        this.tvNameProductCart = tvNameProductCart;
        this.tvQuantityProductCart = tvQuantityProductCart;
        this.tvPriceProductCart = tvPriceProductCart;
    }

    public CartProduct() {
    }

    public String getImgProductCart() {
        return imgProductCart;
    }

    public void setImgProductCart(String imgProductCart) {
        this.imgProductCart = imgProductCart;
    }

    public String getTvNameProductCart() {
        return tvNameProductCart;
    }

    public void setTvNameProductCart(String tvNameProductCart) {
        this.tvNameProductCart = tvNameProductCart;
    }

    public String getTvQuantityProductCart() {
        return tvQuantityProductCart;
    }

    public void setTvQuantityProductCart(String tvQuantityProductCart) {
        this.tvQuantityProductCart = tvQuantityProductCart;
    }

    public String getTvPriceProductCart() {
        return tvPriceProductCart;
    }

    public void setTvPriceProductCart(String tvPriceProductCart) {
        this.tvPriceProductCart = tvPriceProductCart;
    }
}
