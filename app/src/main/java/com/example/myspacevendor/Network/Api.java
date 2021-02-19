package com.example.myspacevendor.Network;

import android.graphics.Bitmap;

import com.example.myspacevendor.model.ServerResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    String my_url = "Vendor_Api.php?apicall=";

    @FormUrlEncoded
    @POST(my_url + "login")
    Call<ServerResponse> login(
            @Field("vendor_email") String vendor_email,
            @Field("vendor_pwd") String vendor_pwd);


    @FormUrlEncoded
    @POST(my_url + "register")
    Call<ServerResponse> Register(
            @Field("vendor_name") String vendor_name,
            @Field("vendor_email") String vendor_email,
            @Field("vendor_pwd") String vendor_pwd,
            @Field("vendor_mobile") String vendor_mobile,
            @Field("vendor_aadhar") String vendor_aadhar);

    @FormUrlEncoded
    @POST(my_url + "shopregister")
    Call<ServerResponse> ShopRegister(
            @Field("shop_name") String shop_name,
            @Field("shop_add") String shop_add,
            @Field("shop_pincode") String shop_pincode,
            @Field("shop_email") String shop_email,
            @Field("shop_gst") String shop_gst,
            @Field("shop_pan") String shop_pan,
            @Field("shop_bank_name") String shop_bank_name,
            @Field("shop_ifsc") String shop_ifsc,
            @Field("shop_acc_no") String shop_acc_no,
            @Field("shop_sqft") String shop_sqft,
            @Field("shop_lic_no") String shop_lic_no,
            @Field("shop_timing") String shop_timing);


    @FormUrlEncoded
    @POST(my_url + "offersdeals")
    Call<ServerResponse> OffersDeals(
            @Field("offer_name") String offer_name,
            @Field("offer_desc") String offer_desc,
            @Field("offer_start") String offer_start,
            @Field("offer_end") String offer_end,
            @Field("offer_banner") String image);


    @FormUrlEncoded
    @POST(my_url + "termspolicies")
    Call<ServerResponse> TermsPolicies(
            @Field("tpname") String tp,
            @Field("tpdesc") String desc);

    @FormUrlEncoded
    @POST(my_url + "manageslot")
    Call<ServerResponse> ManageSlot(
            @Field("mduration") String tp,
            @Field("mnumber") String desc);

    @FormUrlEncoded
    @POST(my_url + "all_shop")
    Call<ServerResponse> getAllShop(
            @Field("vendor_id") String vd_id);




}