package com.example.di_retrofit_livedata.data

import com.example.di_retrofit_livedata.model.Post
import retrofit2.http.GET

interface PostRequest {
    @GET("posts")
    suspend fun getpostlist() : List<Post>
}