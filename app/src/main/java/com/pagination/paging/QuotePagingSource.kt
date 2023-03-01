package com.pagination.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pagination.models.Results
import com.pagination.retrofit.QuoteApi


class QuotePagingSource(private val quoteApi: QuoteApi) : PagingSource<Int, Results>() {
    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        return try {
            val position = params.key ?: 1
            val response = quoteApi.getQuotes(position)
            LoadResult.Page(
                data = response.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.totalPages) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}