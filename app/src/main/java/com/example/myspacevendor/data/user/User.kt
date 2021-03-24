package com.example.myspacevendor.data.user


import com.google.gson.annotations.SerializedName

public data class User(
        @SerializedName("vendor_aadhar")
        val vendorAadhar: String,

        @SerializedName("vendor_email")
        val vendorEmail: String,

        @SerializedName("vendor_id")
        val vendorId: String,

        @SerializedName("vendor_mobile")
        val vendorMobile: String,

        @SerializedName("vendor_name")
        val vendorName: String,

        @SerializedName("vendor_pwd")
        val vendorPwd: String,
)