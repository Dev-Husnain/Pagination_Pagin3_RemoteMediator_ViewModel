package com.pagination.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QuotesRemoteKey")
data class QuoteRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id:String,
    val prevPage:Int?,
    val nextPage:Int?
)