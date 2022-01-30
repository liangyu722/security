package com.example.myapplication.ui.model

data class SecuritySummary(
    val id: Int,
    val label: String,
    val companyName: String,
    val issueDate: Long,
    val quantity: Long,
    val isVesting: Boolean,
    val isFavorite: Boolean,
)