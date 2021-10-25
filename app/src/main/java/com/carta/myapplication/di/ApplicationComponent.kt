package com.carta.myapplication.di

import android.content.Context
import com.carta.myapplication.CartaApplication
import com.carta.myapplication.di.networking.ServiceModule
import com.carta.myapplication.di.presentation.SecuritySummaryModule
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
interface ApplicationComponent : AndroidInjector<CartaApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}
