package com.example.myspace2.Network;

import com.example.myspace2.model.ServerResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    String my_url = "Api.php?apicall=";

    @FormUrlEncoded
    @POST(my_url + "login")
    Call<ServerResponse> login(
            @Field("email") String email,
            @Field("password") String password);
}