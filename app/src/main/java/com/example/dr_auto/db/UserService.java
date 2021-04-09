package com.example.dr_auto.db;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("createUser/")
    Call<User> addUser(@Body User user);
}
