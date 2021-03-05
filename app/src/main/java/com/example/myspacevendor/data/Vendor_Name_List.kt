package com.example.myspacevendor.data


import com.google.gson.annotations.SerializedName

data class Vendor_Name_List(
    @SerializedName("vendor_id")
    val vendorId: String,
    @SerializedName("vendor_name")
    val vendorName: String
)