package com.example.android.myapplicationbook

import com.example.android.myapplicationbook.Model.ResponseBook
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("/books/v1/volumes")
    suspend fun getAllBooks(@Query("q") q : String, @Query("maxResults") maxResults : Int, @Query("startIndex") startIndex : Int): Response<ResponseBook>

    /*@GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId, @QueryMap Map<String, String> options);*/
}