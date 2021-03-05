package com.example.myspacevendor.model;

import com.example.myspacevendor.data.Shop;
import com.example.myspacevendor.data.Vendor_Name_List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerResponse {

    @SerializedName("error")
    @Expose
    private Boolean error;

    @SerializedName("message")
    @Expose
    private String message;


    @SerializedName("shops")
    @Expose
    private List<Shop> shopList;

    @SerializedName("vendors")
    @Expose
    private List<Vendor_Name_List> vendor_name_lists;


    @SerializedName("shop")
    @Expose
    private Shop shop;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }


    public List<Vendor_Name_List> getVendor_name_lists() {
        return vendor_name_lists;
    }

    public void setVendor_name_lists(List<Vendor_Name_List> vendor_name_lists) {
        this.vendor_name_lists = vendor_name_lists;
    }
}