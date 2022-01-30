package com.example.myapplication.data.networking

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceFactory(
    private val gson: Gson,
    private val httpClient: OkHttpClient
) {

    fun <T> createService(clazz: Class<T>, endpoint: String): T {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fake.app.carta.com/api/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(clazz)
    }

}