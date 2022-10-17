package com.example.android.myapplicationbook.Model

import com.google.gson.annotations.SerializedName

data class ResponseBook (
    @SerializedName("kind")
    var kind: String,
    @SerializedName("items")
    var items: List<ResponseItems>
)
