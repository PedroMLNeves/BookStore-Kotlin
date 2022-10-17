package com.example.android.myapplicationbook.Model

import com.google.gson.annotations.SerializedName

data class ResponseVolumeInfo(
    @SerializedName("title")
    var title: String,
    @SerializedName("authors")
    var authors: List<String>,
    @SerializedName("publisher")
    var publisher: String,
    @SerializedName("publishedDate")
    var publishedDate: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("pageCount")
    var pageCount: Int,
    @SerializedName("imageLinks")
    var imageLinks: ResponseImageLinks
)
