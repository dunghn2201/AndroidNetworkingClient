package com.dunghnpd02792.assignmentandroidnetworking.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 8/13/2020
 */
public class Product {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name_food")
    @Expose
    private String nameFood;
    @SerializedName("address_food")

    @Expose
    private String addressFood;
    @SerializedName("price_food")
    @Expose
    private String priceFood;
    @SerializedName("description_food")
    @Expose
    private String descriptionFood;
    @SerializedName("numRating_food")
    @Expose
    private String numRatingFood;
    @SerializedName("image_food")
    @Expose
    private String imageFood;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getAddressFood() {
        return addressFood;
    }

    public void setAddressFood(String addressFood) {
        this.addressFood = addressFood;
    }

    public String getPriceFood() {
        return priceFood;
    }

    public void setPriceFood(String priceFood) {
        this.priceFood = priceFood;
    }

    public String getDescriptionFood() {
        return descriptionFood;
    }

    public void setDescriptionFood(String descriptionFood) {
        this.descriptionFood = descriptionFood;
    }

    public String getNumRatingFood() {
        return numRatingFood;
    }

    public void setNumRatingFood(String numRatingFood) {
        this.numRatingFood = numRatingFood;
    }

    public String getImageFood() {
        return imageFood;
    }

    public void setImageFood(String imageFood) {
        this.imageFood = imageFood;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}
