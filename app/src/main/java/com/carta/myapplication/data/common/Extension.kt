package com.carta.myapplication.data.common

import com.carta.myapplication.data.model.SecurityDetailEntity
import com.carta.myapplication.data.model.SecuritySummaryEntity
import com.carta.myapplication.ui.model.SecurityDetail
import com.carta.myapplication.ui.model.SecuritySummary

fun SecuritySummaryEntity.toSecuritySummary(): SecuritySummary {
    return SecuritySummary(
        id = this.id,
        label = this.label,
        companyName = this.companyName,
        issueDate = this.issueDate,
        quantity = this.quantity,
        isVesting = this.isVesting,
        isFavorite = false
    )
}

fun SecurityDetailEntity.toSecurityDetail(): SecurityDetail {
    return SecurityDetail(
        id = this.id,
        label = this.label,
        companyName = this.companyName,
        issueDate = this.issueDate,
        quantity = this.quantity,
        isVesting = this.isVesting,
        vestedQuantity = this.vestedQuantity
    )
}