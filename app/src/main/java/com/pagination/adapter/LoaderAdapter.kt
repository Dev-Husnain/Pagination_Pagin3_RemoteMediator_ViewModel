package com.pagination.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pagination.databinding.LoadingBinding
import com.pagination.adapter.LoaderAdapter.LoaderAdapterViewHolder

class LoaderAdapter : LoadStateAdapter<LoaderAdapterViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoaderAdapterViewHolder {
        return LoaderAdapterViewHolder(
            LoadingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LoaderAdapterViewHolder, loadState: LoadState) {
        holder.bindData(loadState)
    }


    class LoaderAdapterViewHolder(private val binding: LoadingBinding) : ViewHolder(binding.root) {
        fun bindData(loadState: LoadState) {
            if (loadState is LoadState.Loading){
                binding.progressBar.isVisible = true
                binding.textLoad.isVisible = true
            }


        }
    }


}