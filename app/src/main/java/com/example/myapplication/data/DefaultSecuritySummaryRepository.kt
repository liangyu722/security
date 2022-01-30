package com.example.myapplication.data

import com.example.myapplication.common.RepositoryLoadingException
import com.example.myapplication.common.Result
import com.example.myapplication.data.cache.SecurityCache
import com.example.myapplication.data.common.toSecuritySummary
import com.example.myapplication.data.networking.SecurityService
import com.example.myapplication.ui.model.SecuritySummary
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DefaultSecuritySummaryRepository(
    private val securityService: SecurityService,
    private val securityCache: SecurityCache,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SecuritySummaryRepository {

    override val securitySummariesFlow: Flow<List<SecuritySummary>> =
        securityCache.securitySummariesFlow

    override suspend fun getSecuritySummaries(): Result<Unit> {
        return withContext(ioDispatcher) {
            if (securityCache.isCacheEmpty()) {
                val securitySummaries = fetchSecuritySummaries()
                if (securitySummaries is Result.Error) {
                    return@withContext Result.Error(RepositoryLoadingException("Cannot load security from server"))
                }

                val result = securityCache.refreshCache((securitySummaries as Result.Success).data)
                if (!result) {
                    return@withContext Result.Error(RepositoryLoadingException("Cannot load security from cache"))
                }
            }
            return@withContext Result.Success(Unit)
        }
    }

    override suspend fun setFavoriteSecurity(securitySummary: SecuritySummary): Result<Unit> {
        return withContext(ioDispatcher) {
            securityCache.addToCache(
                securitySummary.copy(
                    isFavorite = true
                )
            )
            return@withContext Result.Success(Unit)
        }
    }

    override suspend fun setUnFavoriteSecurity(securitySummary: SecuritySummary): Result<Unit> {
        return withContext(ioDispatcher) {
            securityCache.addToCache(
                securitySummary.copy(
                    isFavorite = false
                )
            )
            return@withContext Result.Success(Unit)
        }
    }

    private suspend fun fetchSecuritySummaries(): Result<List<SecuritySummary>> {
        return try {
            val result = securityService.getSecuritySummaries().map { it.toSecuritySummary() }
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}
