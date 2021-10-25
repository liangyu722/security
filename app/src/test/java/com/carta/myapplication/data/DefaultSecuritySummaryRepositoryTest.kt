package com.carta.myapplication.data

import app.cash.turbine.test
import com.carta.myapplication.common.Result
import com.carta.myapplication.data.cache.SecurityCache
import com.carta.myapplication.data.common.toSecuritySummary
import com.carta.myapplication.data.model.SecuritySummaryEntity
import com.carta.myapplication.data.networking.SecurityService
import com.carta.myapplication.ui.model.SecuritySummary
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class DefaultSecuritySummaryRepositoryTest {

    private val coroutinesDispatcher = Dispatchers.Unconfined
    private lateinit var sut: DefaultSecuritySummaryRepository

    private val securityService: SecurityService = mockk()
    private val securityCache: SecurityCache = mockk()

    @Before
    fun setup() {
        coEvery { securityCache.securitySummariesFlow } returns flow {  }
        sut = DefaultSecuritySummaryRepository(securityService, securityCache, coroutinesDispatcher)
    }

    @Test
    fun `setFavoriteSecurity - already fav security`() = runBlockingTest {
        //Arrange
        coEvery { securityCache.addToCache(any() as SecuritySummary) } returns Unit
        //Act
        val result = sut.setFavoriteSecurity(securitySummaryFav)
        //Assert
        coVerify(exactly = 1) { securityCache.addToCache(securitySummaryFav) }
        result `should be instance of` (Result.Success::class)
    }

    @Test
    fun `setFavoriteSecurity - unfav security`() = runBlockingTest {
        //Arrange
        coEvery { securityCache.addToCache(any() as SecuritySummary) } returns Unit
        //Act
        val result = sut.setFavoriteSecurity(securitySummaryUnfav)
        //Assert
        coVerify(exactly = 1) { securityCache.addToCache(securitySummaryUnfav.copy(isFavorite = true)) }
        result `should be instance of` (Result.Success::class)
    }

    @Test
    fun `setUnFavoriteSecurity - fav security`() = runBlockingTest {
        //Arrange
        coEvery { securityCache.addToCache(any() as SecuritySummary) } returns Unit
        //Act
        val result = sut.setUnFavoriteSecurity(securitySummaryFav)
        //Assert
        coVerify(exactly = 1) { securityCache.addToCache(securitySummaryFav.copy(isFavorite = false)) }
        result `should be instance of` (Result.Success::class)
    }

    @Test
    fun `setUnFavoriteSecurity - already unfav security`() = runBlockingTest {
        //Arrange
        coEvery { securityCache.addToCache(any() as SecuritySummary) } returns Unit
        //Act
        val result = sut.setUnFavoriteSecurity(securitySummaryUnfav)
        //Assert
        coVerify(exactly = 1) { securityCache.addToCache(securitySummaryUnfav) }
        result `should be instance of` (Result.Success::class)
    }

    @Test
    fun `getSecuritySummaries - cache not empty`() = runBlockingTest {
        //Arrange
        coEvery { securityCache.isCacheEmpty() } returns false
        //Act
        val result = sut.getSecuritySummaries()
        //Assert
        coVerify(exactly = 0) { securityService.getSecuritySummaries() }
        result `should be instance of` (Result.Success::class)
    }

    @Test
    fun `getSecuritySummaries - get from server`() = runBlockingTest {
        //Arrange
        coEvery { securityCache.isCacheEmpty() } returns true
        coEvery { securityCache.refreshCache(any()) } returns true
        coEvery { securityService.getSecuritySummaries() } returns listOf(securitySummaryEntity)
        //Act
        val result = sut.getSecuritySummaries()
        //Assert
        coVerify(exactly = 1) { securityService.getSecuritySummaries() }
        coVerify(exactly = 1) { securityCache.refreshCache(listOf(securitySummaryEntity.toSecuritySummary())) }
        result `should be instance of` (Result.Success::class)
    }

    @Test
    fun `getSecuritySummaries - cache error`() = runBlockingTest {
        //Arrange
        coEvery { securityCache.isCacheEmpty() } returns true
        coEvery { securityCache.refreshCache(any()) } returns false
        coEvery { securityService.getSecuritySummaries() } returns listOf(securitySummaryEntity)
        //Act
        val result = sut.getSecuritySummaries()
        //Assert
        coVerify(exactly = 1) { securityService.getSecuritySummaries() }
        coVerify(exactly = 1) { securityCache.refreshCache(listOf(securitySummaryEntity.toSecuritySummary())) }
        result `should be instance of` (Result.Error::class)
    }

    @Test
    fun `securitySummariesFlow - verify flow`() = runBlockingTest {
        //Arrange
        coEvery { securityCache.securitySummariesFlow } returns flow {
            emit(
                listOf(
                    securitySummaryFav,
                    securitySummaryUnfav
                )
            )
        }
        sut = DefaultSecuritySummaryRepository(securityService, securityCache, coroutinesDispatcher)
        //Act
        sut.securitySummariesFlow.test {
            //Assert
            awaitItem() shouldEqual listOf(securitySummaryFav, securitySummaryUnfav)
            awaitComplete()
        }
    }

    private val securitySummaryEntity =
        SecuritySummaryEntity(1, "label", "companyName", 123, 1000, true)

    private val securitySummaryUnfav =
        SecuritySummary(1, "label", "companyName", 123, 1000, true, false)

    private val securitySummaryFav =
        SecuritySummary(2, "label", "companyName", 123, 1000, true, true)

}