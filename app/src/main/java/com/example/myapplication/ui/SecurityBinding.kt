package com.example.myapplication.ui

import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.model.SecurityDetail
import com.example.myapplication.ui.model.SecuritySummary

@BindingAdapter("app:items")
fun <T> setItems(listView: RecyclerView, items: List<T>) {
    if (listView.adapter is ListAdapter<*, *>) {
        (listView.adapter as ListAdapter<T, *>).submitList(items)
    }
}


@BindingAdapter("app:favorite")
fun setFavorite(imageView: ImageView, summary: SecuritySummary) {
    val drawable = if (summary.isFavorite) {
        R.drawable.ic_star_filled
    } else {
        R.drawable.ic_star_outline
    }
    imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, drawable))
}

@BindingAdapter("app:setVestingProgress")
fun setVestingProgress(progressBar: ProgressBar, securityDetail: SecurityDetail?) {
    securityDetail?.let {
        val progress = securityDetail.vestedQuantity.toDouble() / securityDetail.quantity * 100
        progressBar.progress = progress.toInt()
    }
}

@BindingAdapter("app:setUnvested")
fun setUnvested(textView: TextView, securityDetail: SecurityDetail?) {
    securityDetail?.let {
        textView.text = textView.context.getString(
            R.string.options,
            securityDetail.quantity - securityDetail.vestedQuantity
        )
    }
}