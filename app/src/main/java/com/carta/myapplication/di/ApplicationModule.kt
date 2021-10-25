package com.carta.myapplication.di

import com.carta.myapplication.data.cache.SecurityCache
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import java.text.DateFormat
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun providesDateFormat(
    ): DateFormat {
        return DateFormat.getDateInstance()
    }

    @Singleton
    @Provides
    fun providesSecurityCache() : SecurityCache {
        return SecurityCache(Dispatchers.IO)
    }
}
