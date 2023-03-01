package com.pagination.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pagination.models.QuoteRemoteKey

@Dao
interface RemoteKeyDao {

    @Query("SELECT * FROM QuotesRemoteKey WHERE id =:id")
    suspend fun getRemoteKeys(id: String): QuoteRemoteKey


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(remoteKey: List<QuoteRemoteKey>)

    @Query("DELETE FROM QuotesRemoteKey")
    suspend fun deleteRemoteKeys()
}