package com.example.myapplication.data

import com.example.myapplication.common.Result
import com.example.myapplication.ui.model.SecurityDetail
import com.example.myapplication.ui.model.SecuritySummary
import kotlinx.coroutines.flow.Flow

interface SecurityDetailRepository {

    suspend fun getSecurityDetail(securityId: Int): Result<SecurityDetail>

}
