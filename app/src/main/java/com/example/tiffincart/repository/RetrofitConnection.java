package com.example.tiffincart.repository;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnection {

//    private static final String BASE_URL = "https://api.example.com/";
    private static final String BASE_URL = "https://tiffincare.onrender.com/api/v1/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static ApiCall getApiCall() {

        String url = "https://zerodha-backend.onrender.com/api/v1/";
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS);
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
        ApiCall apiRequest = retrofit.create(ApiCall.class);
        return apiRequest;
    }
}
