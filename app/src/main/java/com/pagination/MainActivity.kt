package com.pagination

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagination.databinding.ActivityMainBinding
import com.pagination.adapter.QuotePagingAdapter
import com.pagination.adapter.LoaderAdapter
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var quoteViewModel: QuoteViewModel
    private lateinit var quotePagingAdapter: QuotePagingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        quotePagingAdapter = QuotePagingAdapter()
        quoteViewModel = ViewModelProvider(this)[QuoteViewModel::class.java]
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.adapter = quotePagingAdapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )



        quoteViewModel.list.observe(this) { pagingData ->
            quotePagingAdapter.submitData(lifecycle, pagingData)
            binding.progressCircular.visibility = View.GONE
        }
    }
}