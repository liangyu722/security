package com.example.myapplication.data

import com.example.myapplication.common.Result
import com.example.myapplication.common.RepositoryLoadingException
import com.example.myapplication.data.cache.SecurityCache
import com.example.myapplication.data.common.toSecurityDetail
import com.example.myapplication.data.networking.SecurityService
import com.example.myapplication.ui.model.SecurityDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
            } else {
                securityCache.addToCache((serverResult as Result.Success).data)
            }

            return@withContext serverResult
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
