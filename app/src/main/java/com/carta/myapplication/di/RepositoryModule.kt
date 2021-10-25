package com.carta.myapplication.di

import com.carta.myapplication.data.DefaultSecurityDetailRepository
import com.carta.myapplication.data.DefaultSecuritySummaryRepository
import com.carta.myapplication.data.SecurityDetailRepository
import com.carta.myapplication.data.SecuritySummaryRepository
import com.carta.myapplication.data.cache.SecurityCache
import com.carta.myapplication.data.networking.SecurityService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    fun providesSecuritySummaryRepository(
        service: SecurityService,
        cache: SecurityCache
    ): SecuritySummaryRepository {
        return DefaultSecuritySummaryRepository(service, cache)
    }

    @Provides
    fun providesSecurityDetailRepository(
        service: SecurityService,
        cache: SecurityCache
    ): SecurityDetailRepository {
        return DefaultSecurityDetailRepository(service, cache)
    }
}
