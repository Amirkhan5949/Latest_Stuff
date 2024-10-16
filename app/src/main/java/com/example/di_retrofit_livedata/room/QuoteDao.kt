package com.example.di_retrofit_livedata.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.di_retrofit_livedata.model.Quote
import com.example.di_retrofit_livedata.model.Quotes
import com.example.di_retrofit_livedata.sealed.ProductState
import java.util.concurrent.Flow

@Dao
interface QuoteDao {

     @Query("Select  * from quotes")
     fun getQuoteData() : kotlinx.coroutines.flow.Flow<ProductState<Quotes>>

     @Insert
     suspend fun getInsertData(data : ArrayList<Quote>)
}