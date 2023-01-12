package com.example.android.myapplicationbook

import android.content.Context
import com.example.android.myapplicationbook.Activity.MainActivity
import com.example.android.myapplicationbook.Fragments.FirstFragment
import com.example.android.myapplicationbook.Fragments.SecondFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent

@Component(modules = [NetworkModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun bookComponentFactory(): BookSubComponent.Factory
}

@Subcomponent
interface BookSubComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): BookSubComponent
    }
    fun inject(mainActivity: MainActivity)
}