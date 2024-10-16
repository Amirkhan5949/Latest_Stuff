package com.example.di_retrofit_livedata.data

import com.example.di_retrofit_livedata.model.Quotes
import retrofit2.http.GET

interface QutoeInterface {
    @GET("quotes")
    suspend fun getQutoes() : Quotes
}