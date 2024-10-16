package com.example.di_retrofit_livedata.data

import com.example.di_retrofit_livedata.model.loginData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface login {
    @POST("auth/login")
    suspend fun getLogin(@Body hashMap: HashMap<String,String>) : loginData
}