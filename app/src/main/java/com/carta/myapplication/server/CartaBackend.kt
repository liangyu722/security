package com.carta.myapplication.server

import com.appham.mockinizer.RequestFilter
import com.appham.mockinizer.mockinize
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min


class CartaBackend @Inject constructor(){

    companion object {
        // Requests should take between 0.5 and 2.0 seconds
        val outgoingRequestTimeRange = (500..2000)
    }

    private val securityIds = (1..25)
    private val securities by lazy {
        securityIds.joinToString(prefix = "[", postfix = "]") { createSecuritySummaryJson(id = it) }
    }

    val mocks: Map<RequestFilter, MockResponse> = mutableMapOf(
        RequestFilter(path = "/api/securities") to MockResponse().apply {
            // Delay .5s - 2s
            setBodyDelay(outgoingRequestTimeRange.random().toLong(), TimeUnit.MILLISECONDS)
            setResponseCode(200)
            setBody(securities)
        }
    ).plus(securityIds.map { securityId ->
        RequestFilter(
            path = "/api/security-details",
            query = "security_id=$securityId"
        ) to MockResponse().apply {
            // Delay .5s - 2s
            setBodyDelay(outgoingRequestTimeRange.random().toLong(), TimeUnit.MILLISECONDS)
            setResponseCode(200)
            setBody(createSecurityDetailsJson(securityId))
        }
    })

    /**
     * Creates a stubbed security object with different properties (though the same each app launch)
     */
    private fun createSecuritySummaryJson(id: Int): String {
        val companyName = listOf("Carta", "eShares", "Meetly")[id % 3]
        val issueDate = Date(1609538147000L + id * 86400000)
        val quantity = id * 1000L
        val isVesting = id % 3 == 0

        return """
        {
          "id": $id,
          "label": "OG-$id",
          "company_name": "$companyName",
          "issue_date_ts_ms": ${issueDate.time},
          "quantity": $quantity,
          "is_vesting": $isVesting
        }
        """
    }

    /**
     * Creates JSON data for the security details response, which pertains a specified security.
     */
    private fun createSecurityDetailsJson(id: Int): String {
        val percentageVested = max(0.0, min(1.0, id * 4.0 / 100))
        val companyName = listOf("Carta", "eShares", "Meetly")[id % 3]
        val issueDate = Date(1609538147000L + id * 86400000)
        val quantity = id * 1000L
        val vestedQuantity = (quantity * percentageVested).toInt()
        val isVesting = id % 3 == 0
        return """
        {
          "id": $id,
          "label": "OG-$id",
          "company_name": "$companyName",
          "issue_date_ts_ms": ${issueDate.time},
          "quantity": $quantity,
          "is_vesting": $isVesting,
          "vested_quantity": $vestedQuantity
        }
        """
    }
}
