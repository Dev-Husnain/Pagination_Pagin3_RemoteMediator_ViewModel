package com.pagination.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pagination.models.QuoteRemoteKey
import com.pagination.models.Results

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuotes(quotes: List<Results>)

    @Query("SELECT * FROM QuotesTable")
    fun getQuotes(): PagingSource<Int, Results>

    @Query("DELETE FROM QuotesTable")
    suspend fun deleteQuotes()

}