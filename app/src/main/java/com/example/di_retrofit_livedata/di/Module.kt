package com.example.di_retrofit_livedata.di

import com.example.di_retrofit_livedata.data.ApiService
import com.example.di_retrofit_livedata.data.Comments
import com.example.di_retrofit_livedata.data.PostRequest
import com.example.di_retrofit_livedata.data.Products
import com.example.di_retrofit_livedata.data.login
import com.example.di_retrofit_livedata.utils.NetworkConstaint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Named("base_post")
    fun providesBaseUrl(): String = NetworkConstaint.BASE_URL

    @Provides
    @Named("base_comment")
    fun providesPostCommentBaseUrl(): String = NetworkConstaint.BASE_URL_COMMENTS

    @Provides
    @Named("base_product")
    fun providesProductBaseUrl(): String = NetworkConstaint.BASE_URL_PRODUCT

    @Provides
    @Named("base_upload")
    fun providesUploadCommentBaseUrl(): String = NetworkConstaint.BASE_URL_UPLOAD

    @Provides
    @Named("base_login")
    fun providesLoginBaseUrl(): String = NetworkConstaint.BASE_URL_Login

    @Provides
    @Named("retrofit_login")
    fun providesLoginRetrofit(
        @Named("base_login") base_url: String,
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder().client(okHttpClient)
            .baseUrl(base_url).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Named("retrofit_post")
    fun providesRetrofit(
        @Named("base_post") base_url: String,
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder().client(okHttpClient)
            .baseUrl(base_url).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Named("retrofit_comment")
    fun providesCommentRetrofit(
        @Named("base_comment") base_url: String,
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder().client(okHttpClient)
            .baseUrl(base_url).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Named("retrofit_product")
    fun providesProductRetrofit(
        @Named("base_product") base_url_product: String,
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder().client(okHttpClient)
            .baseUrl(base_url_product).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Named("retrofit_upload")
    fun providesUploadRetrofit(
        @Named("base_upload") base_url: String,
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder().client(okHttpClient)
            .baseUrl(base_url).addConverterFactory(GsonConverterFactory.create())
            .build()


    @Singleton
    @Provides
    fun provideOkHttpClient(authInterCeptor: AuthInterCeptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(authInterCeptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesPostRequest(@Named("retrofit_post") retrofit: Retrofit): PostRequest =
        retrofit.create(
            PostRequest::class.java
        )

    @Singleton
    @Provides
    fun providesPostComment(@Named("retrofit_comment") retrofit: Retrofit): Comments =
        retrofit.create(Comments::class.java)

    @Singleton
    @Provides
    fun providesUpload(@Named("retrofit_upload") retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesProducts(@Named("retrofit_product") retrofit: Retrofit): Products =
        retrofit.create(Products::class.java)


    @Singleton
    @Provides
    fun providesLogin(@Named("retrofit_login") retrofit: Retrofit): login =
        retrofit.create(login::class.java)
}