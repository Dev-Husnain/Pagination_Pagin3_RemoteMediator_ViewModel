package com.pagination.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QuotesTable")
data class Results(
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,

)