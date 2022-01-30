package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.Application
import com.example.myapplication.di.networking.ServiceModule
import com.example.myapplication.di.presentation.SecuritySummaryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ServiceModule::class,
        SecuritySummaryModule::class,
        RepositoryModule::class,
        ApplicationModule::class,
    ]
)
interface ApplicationComponent : AndroidInjector<Application> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}
