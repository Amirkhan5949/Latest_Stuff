package com.example.di_retrofit_livedata.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class Quote(
    val author: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val quote: String
)