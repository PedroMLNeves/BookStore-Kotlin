package com.example.android.myapplicationbook

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class BookApplication : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}