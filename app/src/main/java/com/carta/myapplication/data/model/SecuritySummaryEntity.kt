package com.carta.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class SecuritySummaryEntity(
    val id: Int,
    val label: String,
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("issue_date_ts_ms")
    val issueDate: Long,
    val quantity: Long,
    @SerializedName("is_vesting")
    val isVesting: Boolean
)