package com.carta.myapplication.di.networking

import com.appham.mockinizer.mockinize
import com.carta.myapplication.data.networking.SecurityService
import com.carta.myapplication.data.networking.ServiceFactory
import com.carta.myapplication.server.CartaBackend
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ServiceModule {

    companion object {
        private const val ENDPOINT = "https://fake.app.carta.com/api/"
        private const val READ_TIMEOUT_SECONDS = 5L
        private const val CONNECTION_TIMEOUT_SECONDS = 5L
    }

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun providesOkHttp(backend: CartaBackend): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .mockinize(backend.mocks)
            .build()
    }

    @Singleton
    @Provides
    fun providesServiceFactory(gson: Gson, okHttp: OkHttpClient): ServiceFactory {
        return ServiceFactory(gson, okHttp)
    }

    @Singleton
    @Provides
    fun providesSecurityService(factory: ServiceFactory): SecurityService {
        return factory.createService(SecurityService::class.java, ENDPOINT)
    }
}
