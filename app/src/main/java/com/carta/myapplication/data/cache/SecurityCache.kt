package com.carta.myapplication.data.cache

import com.carta.myapplication.ui.model.SecurityDetail
import com.carta.myapplication.ui.model.SecuritySummary
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.selects.selectUnbiased
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class SecurityCache(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val cachedSecuritySummaries: ConcurrentMap<Int, SecuritySummary> = ConcurrentHashMap()
    private val cachedSecurityDetail: ConcurrentHashMap<Int, SecurityDetail> = ConcurrentHashMap()
    private val securitySummaryChannel =
        ConflatedBroadcastChannel<List<SecuritySummary>>(cachedSecuritySummaries.values.toList())

    val securitySummariesFlow = securitySummaryChannel.asFlow()

    fun isCacheEmpty(): Boolean {
        return cachedSecuritySummaries.isEmpty()
    }

    suspend fun refreshCache(securitySummaries: List<SecuritySummary>): Boolean {
        return withContext(ioDispatcher) {
            cachedSecuritySummaries.clear()
            securitySummaries.forEach {
                cachedSecuritySummaries[it.id] = it
            }
            securitySummaryChannel.offer(cachedSecuritySummaries.values.toList())
            return@withContext true
        }
    }

    suspend fun addToCache(securitySummary: SecuritySummary) {
        withContext(ioDispatcher) {
            cachedSecuritySummaries[securitySummary.id] = securitySummary
            securitySummaryChannel.offer(cachedSecuritySummaries.values.toList())
        }
    }

    suspend fun addToCache(securityDetail: SecurityDetail) {
        withContext(ioDispatcher) {
            cachedSecurityDetail[securityDetail.id] = securityDetail
        }
    }

    suspend fun getSecurityDetail(securityId: Int): SecurityDetail? {
        return withContext(ioDispatcher) {
            return@withContext cachedSecurityDetail[securityId]
        }
    }
}