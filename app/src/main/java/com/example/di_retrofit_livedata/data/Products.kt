package com.example.di_retrofit_livedata.data

import com.example.di_retrofit_livedata.model.ProductList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Products {
    @GET("products")
   suspend fun getProducts(@Query("limit") limit : Int) : List<ProductList>
//    suspend fun getProducts(@Query("limit") limit : Int) : Response<List<ProductList>>
}