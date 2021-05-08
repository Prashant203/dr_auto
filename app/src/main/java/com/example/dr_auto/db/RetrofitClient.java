package com.example.dr_auto.db;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String API_URL = "http://192.168.43.61:9192/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String apiUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
