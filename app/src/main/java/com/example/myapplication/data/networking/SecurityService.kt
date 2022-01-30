package com.example.myapplication.data.networking

import com.example.myapplication.data.model.SecurityDetailEntity
import com.example.myapplication.data.model.SecuritySummaryEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface SecurityService {

    @GET("securities")
    suspend fun getSecuritySummaries(): List<SecuritySummaryEntity>

    @GET("security-details")
    suspend fun getSecurityDetail(
        @Query("security_id") securityId: Int,
    ): SecurityDetailEntity

}