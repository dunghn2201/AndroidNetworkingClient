package com.dunghnpd02792.assignmentandroidnetworking.config;

import com.dunghnpd02792.assignmentandroidnetworking.R;

/**
 * Created on 7/26/2020
 */
public class Constants {
    public static final String ROOT_URL = "http://192.168.43.31:3000";
    //    public static final String ROOT_URL = "http://10.0.228.249:3000";

    //API USER
    public static final String URL_LOGIN = "/api/login-user";
    public static final String URL_REGISTER = "/api/register-user";
    public static final String UPDATE_USER = "/api/update-user/{id}";
    public static final String DELETE_USER = "/api/delete-user/{id}";
    public static final String SEARCH_USER = "/api/search-user/";
    public static final String CHANGE_PASSWORD = "/api/change-password/{id}";
    public static final String RESET_PASSWORD = "/api/reset-password";
    public static final String REQUEST_TOKEN_API = "/api/request-token-api";


    //API PRODUCT
    public static final String GET_ALL_PRODUCT = "/api/get-all-product";
    public static final String ADD_PRODUCT = "/api/add-product";
    public static final String UPDATE_PRODUCT = "/api/update-product/{id}";
    public static final String DELETE_PRODUCT = "/api/delete-product/{id}";
    public static final String SEARCH_PRODUCT = "/api/search-product/";
    public static final String GET_PRODUCT_BY_ID = "/api/get-product-by-id/{id}";

    //DATA FOODBRAND
    public static final String[] nameArray = {"KFC", "SUBWAY", "MC DONALD'S", "STARBUCKS", "BURGERKING", "DUNKIN' DONUTS"};

    public static final Integer[] drawableArray = {R.drawable.logobrand1, R.drawable.logobrand2, R.drawable.logobrand3,
            R.drawable.logobrand4, R.drawable.logobrand5, R.drawable.logobrand6};


}
