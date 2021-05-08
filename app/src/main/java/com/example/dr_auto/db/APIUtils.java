package com.example.dr_auto.db;

public class APIUtils {
    public static final String API_URL = "http://localhost:9192/";

    private APIUtils() {
    }

    public static UserService getUserService() {
        return RetrofitClient.getClient(API_URL).create(UserService.class);
    }

}
