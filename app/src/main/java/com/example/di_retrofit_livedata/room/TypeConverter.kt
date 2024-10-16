package com.example.di_retrofit_livedata.room

import androidx.room.TypeConverter

class TypeConverter {
    @TypeConverter
    fun fromString(value : String) : List<String>{
        return value.split(",").map { it.trim() }
    }

   @TypeConverter
    fun fromToList(list : List<String>) : String {
        return list.joinToString { "," }
    }

}