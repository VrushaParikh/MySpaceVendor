package com.example.myspacevendor.model;

import com.example.myspacevendor.data.Shop;
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
}