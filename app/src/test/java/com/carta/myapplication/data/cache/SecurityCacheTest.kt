package com.carta.myapplication.data.cache

import app.cash.turbine.test
import com.carta.myapplication.ui.model.SecurityDetail
import com.carta.myapplication.ui.model.SecuritySummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class SecurityCacheTest {

    private val coroutinesDispatcher = Dispatchers.Unconfined
    private lateinit var sut: SecurityCache

    @Before
    fun setup() {
        sut = SecurityCache(coroutinesDispatcher)
    }

    @Test
    fun `getSecurityDetail - cache miss`() = runBlockingTest {
        //Arrange
        //Act
        val result = sut.getSecurityDetail(1)
        //Assert
        result shouldEqual null
    }

    @Test
    fun `getSecurityDetail - add to cache, return SecurityDetail`() = runBlockingTest {
        //Arrange

        //Act
        sut.addToCache(securityDetail)
        val result = sut.getSecurityDetail(1)
        //Assert
        result shouldEqual securityDetail
    }

    @Test
    fun `addToCache security summary - add to cache, flow return security summary`() =
        runBlockingTest {
            //Arrange

            //Act
            sut.addToCache(securitySummary)
            val result = sut.securitySummariesFlow.first()
            //Assert
            result shouldEqual listOf(securitySummary)
        }

    @Test
    fun `refreshCache - add to cache, flow return security summary`() = runBlockingTest {
        //Arrange

        //Act
        sut.refreshCache(listOf(securitySummary))
        val result = sut.securitySummariesFlow.first()
        //Assert
        result shouldEqual listOf(securitySummary)
    }

    @Test
    fun `refreshCache - replace cache, flow return security summary`() = runBlockingTest {
        //Arrange
        //Act
        sut.addToCache(securitySummary)
        sut.refreshCache(listOf(securitySummary.copy(id = 2)))
        sut.securitySummariesFlow.test {
            //Assert
            awaitItem() shouldEqual listOf(securitySummary.copy(id = 2))
        }
    }

    private val securityDetail = SecurityDetail(1, "label", "companyName", 123, 1000, true, 1)
    private val securitySummary = SecuritySummary(1, "label", "companyName", 123, 1000, true, false)
}