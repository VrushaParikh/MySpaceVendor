package com.example.myspacevendor.model

import com.example.myspacevendor.data.Booking
import com.example.myspacevendor.data.Shop
import com.example.myspacevendor.data.Vendor_Name_List
import com.example.myspacevendor.data.slot.SlotData
import com.example.myspacevendor.data.user.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ServerResponse {
    @SerializedName("error")
    @Expose
    var error: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("shops")
    @Expose
    var shopList: List<Shop>? = null

    @SerializedName("vendors")
    @Expose
    var vendor_name_lists: List<Vendor_Name_List>? = null

    @SerializedName("shop")
    @Expose
    var shop: Shop? = null


    @SerializedName("user")
    @Expose
    var user: User? = null

    @SerializedName("bookings")
    @Expose
    var bookingList: List<Booking>? = null



    @SerializedName("slots")
    @Expose
    var slotList: List<SlotData>? = null
}