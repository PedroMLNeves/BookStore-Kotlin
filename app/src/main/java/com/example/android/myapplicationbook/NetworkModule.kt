package com.example.android.myapplicationbook

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        var mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        var mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .build()


        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()
        return retrofit
    }

    @Provides
    fun getApiInterface(retrofit: Retrofit) : ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }
}