package com.example.tiffincart.repository;

import android.util.Log;

import com.example.tiffincart.Model.ResponseModel;
import com.example.tiffincart.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryApiCall {
    public RepositoryApiCall() {
        apiCall = RetrofitConnection.getClient().create(ApiCall.class);
    }
    private ApiCall apiCall;

    public void createAccountUser(User user, Callback<User> callback) {

        Call<User> call = apiCall.createAccountUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("OkhttpError","success");

                if(response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("OkhttpError", t.getMessage());
            }
        });
    }

    public void loginUserAccount(User user, Callback<ResponseModel> callback) {

        Call<ResponseModel> call = apiCall.loginAccountUser(user);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Log.e("OkhttpError","success");

                if(response.isSuccessful()){
                    callback.onResponse(call, response);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("OkhttpError", t.getMessage());
            }
        });
    }
}
