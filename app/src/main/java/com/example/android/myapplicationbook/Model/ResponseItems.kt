package com.example.android.myapplicationbook.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseItems(
    @SerializedName("id")
    var id: String,
    @SerializedName("selfLink")
    var selfLink: String,
    @SerializedName("volumeInfo")
    var volumeInfo: ResponseVolumeInfo,
    @SerializedName("saleInfo")
    var saleInfo: ResponseSaleInfo
) : Serializable
