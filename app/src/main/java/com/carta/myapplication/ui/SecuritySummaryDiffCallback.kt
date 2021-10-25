package com.carta.myapplication.ui

import androidx.recyclerview.widget.DiffUtil
import com.carta.myapplication.ui.model.SecuritySummary


class SecuritySummaryDiffCallback : DiffUtil.ItemCallback<SecuritySummary>() {
    override fun areItemsTheSame(oldItem: SecuritySummary, newItem: SecuritySummary): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SecuritySummary, newItem: SecuritySummary): Boolean {
        return oldItem == newItem
    }
}