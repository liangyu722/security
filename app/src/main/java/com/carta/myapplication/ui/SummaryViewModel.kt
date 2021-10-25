package com.carta.myapplication.ui

import com.carta.myapplication.ui.model.SecuritySummary

interface SummaryViewModel {

    fun onClickSecuritySummary(securitySummary: SecuritySummary)

    fun onClickSecuritySummaryFav(securitySummary: SecuritySummary, confirmed: Boolean)
}