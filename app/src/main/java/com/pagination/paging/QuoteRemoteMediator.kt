package com.pagination.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.pagination.database.QuoteDBClass
import com.pagination.models.QuoteRemoteKey
import com.pagination.models.Results
import com.pagination.retrofit.QuoteApi

@ExperimentalPagingApi
class QuoteRemoteMediator(private val quoteApi: QuoteApi, private val quoteDBClass: QuoteDBClass) :
    RemoteMediator<Int, Results>() {
    private val quoteDao = quoteDBClass.quotesDao()
    private val quoteRemoteKeyDao = quoteDBClass.remoteKeysDao()
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Results>,
    ): MediatorResult {
        //fetch quotes from API
        // Save these quotes and remote keys in database
        //logics for states - REFRESH, APPEND, PREPEND

        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }


            val response = quoteApi.getQuotes(currentPage)
            val endOfPaginationReached = response.totalPages == currentPage
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1
            quoteDBClass.withTransaction {

                if (loadType == LoadType.REFRESH){
                    quoteDao.deleteQuotes()
                    quoteRemoteKeyDao.deleteRemoteKeys()
                }


                quoteDao.addQuotes(response.results)
                val keys = response.results.map {
                    QuoteRemoteKey(
                        id = it._id,
                        prevPage = prevPage,
                        nextPage = nextPage,

                        )
                }
                quoteRemoteKeyDao.addRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)

        } catch (e: java.lang.Exception) {
            MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Results>,
    ): QuoteRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?._id?.let { id ->
                quoteRemoteKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Results>,
    ): QuoteRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { quote ->
                quoteRemoteKeyDao.getRemoteKeys(id = quote._id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Results>,
    ): QuoteRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { quote ->
                quoteRemoteKeyDao.getRemoteKeys(id = quote._id)
            }
    }
}