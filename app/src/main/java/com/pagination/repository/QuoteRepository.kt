package com.pagination.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.pagination.database.QuoteDBClass
import com.pagination.paging.QuotePagingSource
import com.pagination.paging.QuoteRemoteMediator
import com.pagination.retrofit.QuoteApi
import javax.inject.Inject

@ExperimentalPagingApi
class QuoteRepository @Inject constructor(
    private val quoteApi: QuoteApi,
    private val quoteDBClass: QuoteDBClass
    ) {
    fun getQuotes() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100
        ),
        remoteMediator = QuoteRemoteMediator(quoteApi,quoteDBClass),
        pagingSourceFactory = {

            quoteDBClass.quotesDao().getQuotes()
        }
    ).liveData
}