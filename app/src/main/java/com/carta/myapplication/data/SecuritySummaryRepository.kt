package com.carta.myapplication.data

import com.carta.myapplication.common.Result
import com.carta.myapplication.ui.model.SecuritySummary
import kotlinx.coroutines.flow.Flow

interface SecuritySummaryRepository {

    val securitySummariesFlow : Flow<List<SecuritySummary>>

    suspend fun getSecuritySummaries() : Result<Unit>

    suspend fun setFavoriteSecurity(securitySummary: SecuritySummary) : Result<Unit>

    suspend fun setUnFavoriteSecurity(securitySummary: SecuritySummary) : Result<Unit>
}