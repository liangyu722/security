package com.carta.myapplication.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carta.myapplication.databinding.FavSummaryItemBinding
import com.carta.myapplication.databinding.SecuritySummaryItemBinding
import com.carta.myapplication.ui.SecuritySummaryDiffCallback
import com.carta.myapplication.ui.SummaryViewModel
import com.carta.myapplication.ui.model.SecuritySummary
import java.text.DateFormat

class FavSecuritySummaryAdapter(
    private val viewModel: SummaryViewModel,
    private val dateFormat: DateFormat
) :
    ListAdapter<SecuritySummary, FavSecuritySummaryAdapter.ViewHolder>(SecuritySummaryDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewModel, dateFormat, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: FavSummaryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            viewModel: SummaryViewModel,
            dateFormat: DateFormat,
            item: SecuritySummary
        ) {
            binding.viewmodel = viewModel
            binding.dateFormatter = dateFormat
            binding.securitySummary = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavSummaryItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
