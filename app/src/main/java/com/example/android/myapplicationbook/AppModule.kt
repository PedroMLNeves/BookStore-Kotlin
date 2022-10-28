package com.example.android.myapplicationbook

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule (var mApplication: ViewModel?){

    @Provides
    @Singleton
    fun provideApplication(): ViewModel? {
        return mApplication
    }
}