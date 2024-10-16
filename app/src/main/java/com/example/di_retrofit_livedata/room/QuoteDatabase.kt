package com.example.di_retrofit_livedata.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.di_retrofit_livedata.model.Quote

@Database(entities = [Quote::class], version = 1, exportSchema = false)
@TypeConverters(com.example.di_retrofit_livedata.room.TypeConverter::class)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun quoteDao() : QuoteDao
}