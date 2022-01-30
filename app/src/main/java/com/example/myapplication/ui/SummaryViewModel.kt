package com.example.myapplication.ui

import com.example.myapplication.ui.model.SecuritySummary

interface SummaryViewModel {

    fun onClickSecuritySummary(securitySummary: SecuritySummary)

    fun onClickSecuritySummaryFav(securitySummary: SecuritySummary, confirmed: Boolean)
}