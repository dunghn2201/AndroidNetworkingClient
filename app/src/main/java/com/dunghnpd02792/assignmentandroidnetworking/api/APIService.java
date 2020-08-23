package com.dunghnpd02792.assignmentandroidnetworking.api;

import androidx.annotation.NonNull;

import com.dunghnpd02792.assignmentandroidnetworking.config.Constants;
import com.dunghnpd02792.assignmentandroidnetworking.model.Product;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserData;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserInfo;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserLogin;
import com.dunghnpd02792.assignmentandroidnetworking.model.UserResetPass;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Khởi tạo file interface Retrofit định nghĩa kiểu request
 */
public interface APIService {
    //API USER
    @FormUrlEncoded
    @POST(Constants.URL_REGISTER)
    Call<UserLogin> createUser(@Field("fullName") String fullName,
                               @Field("email") String email,
                               @Field("hashed_password") String hashed_password
    );

    @FormUrlEncoded
    @POST(Constants.URL_LOGIN)
    Call<UserLogin> loginUser(
            @Field("email") String email,
            @Field("hashed_password") String hashed_password
    );

    @FormUrlEncoded
    @POST(Constants.REQUEST_TOKEN_API)
    Call<UserInfo> getTokenAPI(
            @Field("email") String email

    );


    @Multipart
    @PUT(Constants.UPDATE_USER)
    Call<ResponseBody> updateInfoUser(@Path("id") @NonNull String id,
                                      @Part("fullName") @NonNull RequestBody fullName,
                                      @Part("numberPhone") @NonNull RequestBody numberPhone,
                                      @Part("dateOfbirth") @NonNull RequestBody dateOfbirth,
                                      @Part("bio") @NonNull RequestBody bio,
                                      @Part("address") @NonNull RequestBody address);


    @Multipart
    @PUT(Constants.UPDATE_USER)
    Call<ResponseBody> updateAvatarUser(@Path("id") @NonNull String id,
                                        @Part MultipartBody.Part image, @Part("avatar") RequestBody avatar);

    @PUT(Constants.DELETE_USER)
    Call<UserInfo> deleteUser(@Path("id") @NonNull String id);

    @FormUrlEncoded
    @POST(Constants.SEARCH_USER)
    Call<List<UserData>> searchUser(@Field("keysearch") @NonNull String keysearch);

    @FormUrlEncoded
    @PUT(Constants.CHANGE_PASSWORD)
    Call<UserResetPass> changePassword(@Path("id") String id,
                                       @Field("currentPassword") String currentPassword,
                                       @Field("newPassword") String newPassword);


    @FormUrlEncoded
    @POST(Constants.RESET_PASSWORD)
    Call<UserResetPass> resetPasswordInit(@Field("email") @NonNull String email);

    @FormUrlEncoded
    @POST(Constants.RESET_PASSWORD)
    Call<UserResetPass> resetPasswordFinish(@Field("email") @NonNull String email, @Field("newPassword") @NonNull String newPassword);


    //API PRODUCT
    @Multipart
    @POST(Constants.ADD_PRODUCT)
    Call<ResponseBody> addProduct(@Part("name_food") @NonNull RequestBody name_food,
                                  @Part("address_food") @NonNull RequestBody address_food,
                                  @Part("price_food") @NonNull RequestBody price_food,
                                  @Part("description_food") @NonNull RequestBody description_food,
                                  @Part MultipartBody.Part image, @Part("image_food") RequestBody image_food);
//
//    @POST(Constants.GET_ALL_PRODUCT)
//    Call<List<Product>> getAllProduct();

    @Multipart
    @PUT(Constants.UPDATE_PRODUCT)
    Call<ResponseBody> updateProduct(@Path("id") @NonNull String id,
                                     @Part("address_food") @NonNull RequestBody address_food,
                                     @Part("price_food") @NonNull RequestBody price_food,
                                     @Part("description_food") @NonNull RequestBody description_food,
                                     @Part MultipartBody.Part image, @Part("image_food") RequestBody image_food);

    @PUT(Constants.DELETE_PRODUCT)
    Call<Product> deleteProduct(@Path("id") @NonNull String id);

    @FormUrlEncoded
    @POST(Constants.SEARCH_PRODUCT)
    Call<List<Product>> searchProduct(@Field("keysearch") @NonNull String keysearch);

    @POST(Constants.GET_PRODUCT_BY_ID)
    Call<Product> getProductByID(@Path("id") @NonNull String id);
}
