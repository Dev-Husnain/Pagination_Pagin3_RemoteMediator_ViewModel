package com.pagination.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pagination.models.QuoteRemoteKey
import com.pagination.models.Results

@Database(entities = [Results::class,QuoteRemoteKey::class], version = 1)
abstract class QuoteDBClass:RoomDatabase() {

    abstract fun quotesDao():QuoteDao
    abstract fun remoteKeysDao():RemoteKeyDao
}