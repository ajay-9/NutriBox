package com.example.tiffincart.repository;

import com.example.tiffincart.Model.ResponseModel;
import com.example.tiffincart.Model.User;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiCall {

    /*@POST("configs/getRolesAndFeatures")
    Call<User> setUser(@Header("Content-Type") String contentType, @Body MultipartBody body);
*/
    @POST("users/create-account")
    Call<User> createAccountUser(@Body User user);

    @POST("users/login")
    Call<ResponseModel> loginAccountUser(@Body User user);

}
