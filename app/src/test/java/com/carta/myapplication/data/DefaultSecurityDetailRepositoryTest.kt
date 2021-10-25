package com.carta.myapplication.data

import com.carta.myapplication.common.Result.Error
import com.carta.myapplication.common.Result.Success
import com.carta.myapplication.data.cache.SecurityCache
import com.carta.myapplication.data.model.SecurityDetailEntity
import com.carta.myapplication.data.networking.SecurityService
import com.carta.myapplication.ui.model.SecurityDetail
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultSecurityDetailRepositoryTest {

    private val coroutinesDispatcher = Dispatchers.Unconfined
    private lateinit var sut: DefaultSecurityDetailRepository

    private val securityService : SecurityService = mockk()
    private val securityCache : SecurityCache = mockk()

    @Before
    fun setup() {
        sut = DefaultSecurityDetailRepository(securityService, securityCache, coroutinesDispatcher)
    }

    @Test
    fun `getSecurityDetail - server throws exception, return Error`() = runBlockingTest {
        //Arrange
        coEvery { securityCache.getSecurityDetail(any()) } returns null
        coEvery { securityService.getSecurityDetail(any()) } throws Exception("fake exception")
        //Act
        val result = sut.getSecurityDetail(1)
        //Assert
        result `should be instance of`(Error::class)
    }

    @Test
    fun `getSecurityDetail - get from cache`() = runBlockingTest {
        //Arrange
        coEvery { securityCache.getSecurityDetail(any()) } returns securityDetail
        //Act
        val result = sut.getSecurityDetail(1)
        //Assert
        result `should be instance of`(Success::class)
        (result as Success).data shouldEqual securityDetail
        coVerify(exactly = 0) { securityCache.addToCache(any() as SecurityDetail) }
        coVerify(exactly = 0) { securityService.getSecurityDetail(any()) }
        coVerify(exactly = 1) { securityCache.getSecurityDetail(1) }
    }

    @Test
    fun `getSecurityDetail - get from server`() = runBlockingTest {
        //Arrange
        coEvery { securityCache.getSecurityDetail(any()) } returns null
        coEvery { securityService.getSecurityDetail(any()) } returns securityDetailEntity
        coEvery { securityCache.addToCache(any() as SecurityDetail) } returns Unit
        //Act
        val result = sut.getSecurityDetail(1)
        //Assert
        result `should be instance of`(Success::class)
        (result as Success).data shouldEqual securityDetail
        coVerify(exactly = 1) { securityCache.addToCache(securityDetail) }
        coVerify(exactly = 1) { securityService.getSecurityDetail(1) }
        coVerify(exactly = 1) { securityCache.getSecurityDetail(1) }
    }

    private val securityDetail = SecurityDetail(1, "label", "companyName", 123, 1000, true, 1)
    private val securityDetailEntity = SecurityDetailEntity(1, "label", "companyName", 123, 1000, true, 1)
}