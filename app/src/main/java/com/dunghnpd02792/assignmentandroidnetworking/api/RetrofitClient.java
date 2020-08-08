package com.dunghnpd02792.assignmentandroidnetworking.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 7/26/2020
 */
public class RetrofitClient {
    public static Retrofit retrofit = null;

    public static Retrofit getInstance() {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();


        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.ROOT_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
