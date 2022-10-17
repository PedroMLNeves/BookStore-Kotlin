package com.example.android.myapplicationbook.Model

import com.google.gson.annotations.SerializedName

data class ResponseImageLinks(
    @SerializedName("smallThumbnail")
    var smallThumbnail: String,
    @SerializedName("thumbnail")
    var thumbnail: String
)
