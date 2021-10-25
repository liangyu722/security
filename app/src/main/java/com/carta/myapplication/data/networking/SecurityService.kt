package com.carta.myapplication.data.networking

import com.carta.myapplication.data.model.SecurityDetailEntity
import com.carta.myapplication.data.model.SecuritySummaryEntity
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