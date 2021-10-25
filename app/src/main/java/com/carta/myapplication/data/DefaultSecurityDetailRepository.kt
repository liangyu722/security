package com.carta.myapplication.data

import com.carta.myapplication.common.RepositoryLoadingException
import com.carta.myapplication.common.Result
import com.carta.myapplication.data.cache.SecurityCache
import com.carta.myapplication.data.common.toSecurityDetail
import com.carta.myapplication.data.common.toSecuritySummary
import com.carta.myapplication.data.networking.SecurityService
import com.carta.myapplication.ui.model.SecurityDetail
import com.carta.myapplication.ui.model.SecuritySummary
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DefaultSecurityDetailRepository(
    private val securityService: SecurityService,
    private val securityCache: SecurityCache,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SecurityDetailRepository {

    override suspend fun getSecurityDetail(securityId: Int): Result<SecurityDetail> {
        return withContext(ioDispatcher) {
            val cachedResult = securityCache.getSecurityDetail(securityId)
            if (cachedResult != null) {
                return@withContext Result.Success(cachedResult)
            }

            val serverResult = fetchSecurityDetail(securityId)
            if (serverResult is Result.Error) {
                return@withContext Result.Error(RepositoryLoadingException("Cannot load security from server"))
            }

            securityCache.addToCache((serverResult as Result.Success).data)

            val result = securityCache.getSecurityDetail(securityId)?.let {
                Result.Success(it)
            } ?: run {
                Result.Error(RepositoryLoadingException("Cannot load security"))
            }

            return@withContext result
        }
    }

    private suspend fun fetchSecurityDetail(securityId: Int): Result<SecurityDetail> {
        return try {
            val result = securityService.getSecurityDetail(securityId).toSecurityDetail()
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}
