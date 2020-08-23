package com.dunghnpd02792.assignmentandroidnetworking.model;

/**
 * Created on 8/17/2020
 */
public class FoodBrand {
    String nameFoodBrand;
    int imgFoodBrand;

    public FoodBrand(String nameFoodBrand, int imgFoodBrand) {
        this.nameFoodBrand = nameFoodBrand;
        this.imgFoodBrand = imgFoodBrand;
    }

    public String getNameFoodBrand() {
        return nameFoodBrand;
    }

    public void setNameFoodBrand(String nameFoodBrand) {
        this.nameFoodBrand = nameFoodBrand;
    }

    public int getImgFoodBrand() {
        return imgFoodBrand;
    }

    public void setImgFoodBrand(int imgFoodBrand) {
        this.imgFoodBrand = imgFoodBrand;
    }
}
