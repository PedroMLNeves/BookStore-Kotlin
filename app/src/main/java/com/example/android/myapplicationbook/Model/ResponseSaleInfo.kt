package com.example.android.myapplicationbook.Model

import com.google.gson.annotations.SerializedName

data class ResponseSaleInfo(
    @SerializedName("country")
    var country: String,
    @SerializedName("saleability")
    var saleability: String,
    @SerializedName("isEbook")
    var isEbook: Boolean
)
