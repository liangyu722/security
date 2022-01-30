package com.example.myapplication.data.common

import com.example.myapplication.data.model.SecurityDetailEntity
import com.example.myapplication.data.model.SecuritySummaryEntity
import com.example.myapplication.ui.model.SecurityDetail
import com.example.myapplication.ui.model.SecuritySummary

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