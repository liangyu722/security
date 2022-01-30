package com.example.myapplication.di

import com.example.myapplication.data.DefaultSecurityDetailRepository
import com.example.myapplication.data.DefaultSecuritySummaryRepository
import com.example.myapplication.data.SecurityDetailRepository
import com.example.myapplication.data.SecuritySummaryRepository
import com.example.myapplication.data.cache.SecurityCache
import com.example.myapplication.data.networking.SecurityService
import dagger.Module
import dagger.Provides

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
