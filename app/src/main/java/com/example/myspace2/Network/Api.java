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


    @FormUrlEncoded
    @POST(my_url + "register")
    Call<ServerResponse> register(
            @Field("fname") String fname,
            @Field("email") String email,
            @Field("category") String category,
            @Field("dob") String dob,
            @Field("phno") String phno,
            @Field("pwd") String pwd,
            @Field("ad_no") String ad_no);


    @FormUrlEncoded
    @POST(my_url + "shopregister")
    Call<ServerResponse> ShopRegister(
            @Field("shname") String shname,
            @Field("shadd") String shadd,
            @Field("shcategory") String shcategory,
            @Field("sharea") String sharea,
            @Field("shcity") String shcity,
            @Field("shstate") String shstate,
            @Field("shpin") String shpin,
            @Field("shmail") String shmail,
            @Field("shgst") String shgst,
            @Field("shpan") String shpan,
            @Field("shbankname") String shbankname,
            @Field("shbranch") String shbranch,
            @Field("shaccno") String shaccn,
            @Field("shsqft") String shsqft,
            @Field("shlic") String shlic,
            @Field("shtiming") String shtiming);


}