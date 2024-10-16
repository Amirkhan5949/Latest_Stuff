package com.example.di_retrofit_livedata.data

import com.example.di_retrofit_livedata.model.Comments
import retrofit2.http.GET

interface Comments {
    @GET("1/comments")
  suspend fun getComments() : List<Comments>
}