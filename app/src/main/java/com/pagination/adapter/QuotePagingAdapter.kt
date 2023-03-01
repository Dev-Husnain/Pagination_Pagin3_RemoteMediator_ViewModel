package com.pagination.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pagination.databinding.QuoteItemsBinding
import com.pagination.models.Results

class QuotePagingAdapter :
    PagingDataAdapter<Results, QuotePagingAdapter.QuotePagingAdapterViewHolder>(QuoteDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): QuotePagingAdapterViewHolder {
        return QuotePagingAdapterViewHolder(
            QuoteItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: QuotePagingAdapterViewHolder, position: Int) {
        getItem(position)?.let { holder.bindData(it) }
    }


    inner class QuotePagingAdapterViewHolder(private val binding: QuoteItemsBinding) :
        ViewHolder(binding.root) {
        fun bindData(item: Results) {
            binding.quoteNameList.text = item.content
        }
    }

    class QuoteDiffUtil : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem == newItem
        }
    }
}