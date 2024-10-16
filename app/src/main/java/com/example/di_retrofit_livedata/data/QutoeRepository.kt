package com.example.di_retrofit_livedata.data

import android.content.Context
import android.database.Cursor
import com.example.di_retrofit_livedata.model.Quote
import com.example.di_retrofit_livedata.model.Quotes
import com.example.di_retrofit_livedata.room.QuoteDao
import com.example.di_retrofit_livedata.sealed.ProductState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QutoeRepository @Inject constructor(
    private val qutoeInterface: QutoeInterface,
    private val quoteDao: QuoteDao,
) {
    suspend fun getQuotes(): Flow<ProductState<Quotes>> = flow {
        emit(ProductState.Loading())
       /* val data = qutoeInterface.getQutoes()
            quoteDao.getInsertData(data.quotes)
        emit(ProductState.Success(qutoeInterface.getQutoes()))*/
         val localData = quoteDao.getQuoteData()
         if (localData == null) {
             try {
                 val remoteQutoes = qutoeInterface.getQutoes()
                 quoteDao.getInsertData(remoteQutoes.quotes)
                 emit(ProductState.Success(qutoeInterface.getQutoes()))

             } catch (e: Exception) {
                 emit(ProductState.Error(e))
             }
         }
    }.catch { e ->
        emit(ProductState.Error(e))
    }

    fun getDataFromLocalDb(): Flow<ProductState<Quotes>> = quoteDao.getQuoteData()
}