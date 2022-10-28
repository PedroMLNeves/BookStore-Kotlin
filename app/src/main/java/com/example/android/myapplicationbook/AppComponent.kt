package com.example.android.myapplicationbook

import com.example.android.myapplicationbook.ViewModel.MainViewModel
import dagger.Component

@Component(modules = [NetworkModule::class, AppModule::class])
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)
}