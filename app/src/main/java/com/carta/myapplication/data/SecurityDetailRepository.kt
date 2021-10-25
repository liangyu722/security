package com.carta.myapplication.data

import com.carta.myapplication.common.Result
import com.carta.myapplication.ui.model.SecurityDetail
import com.carta.myapplication.ui.model.SecuritySummary
import kotlinx.coroutines.flow.Flow

interface SecurityDetailRepository {

    suspend fun getSecurityDetail(securityId: Int): Result<SecurityDetail>

}
