package com.dunghnpd02792.assignmentandroidnetworking.api;

import androidx.annotation.NonNull;

import com.dunghnpd02792.assignmentandroidnetworking.model.UserInfo;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserLogin;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Khởi tạo file interface Retrofit định nghĩa kiểu request
 */
public interface APIService {

    @FormUrlEncoded
    @POST(Constants.URL_REGISTER)
    Call<UserLogin> createUser(@Field("fullName") String fullName,
                               @Field("email") String email,
                               @Field("hash") String hash
    );

    @FormUrlEncoded
    @POST(Constants.URL_LOGIN)
    Call<UserLogin> loginUser(
            @Field("email") String email,
            @Field("hash") String hash
    );

    @FormUrlEncoded
    @POST(Constants.REQUEST_TOKEN_API)
    Call<UserInfo> getTokenAPI(
            @Field("email") String email

    );

    @Multipart
    @POST(Constants.UPLOAD_FILE)
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("upload") RequestBody name);

    @Multipart
    @POST(Constants.ADD_PRODUCT)
    Call<ResponseBody> addProduct(@Part("product_title") @NonNull RequestBody product_title,
                                  @Part("product_address") @NonNull RequestBody product_address,
                                  @Part("product_phone") @NonNull RequestBody product_phone,
                                  @Part("product_content") @NonNull RequestBody product_content,
                                  @Part MultipartBody.Part image, @Part("image") RequestBody name);

    @Multipart
    @POST(Constants.UPDATE_USER)
    Call<UserInfo> updateUser(@Part("fullName") @NonNull RequestBody fullName,
                              @Part("address") @NonNull RequestBody address,
                              @Part MultipartBody.Part image, @Part("avatar") RequestBody avatar);
}
