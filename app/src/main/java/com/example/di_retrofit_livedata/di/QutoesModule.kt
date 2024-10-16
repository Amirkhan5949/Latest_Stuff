package com.example.di_retrofit_livedata.di

import com.example.di_retrofit_livedata.data.QutoeInterface
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
class QutoesModule {

    @Provides
    @Named("base_quote")
    fun providesQuotes(): String = NetworkConstaint.BASE_URL_Quotes

    @Provides
    @Named("retrofit_quote")
    fun providesQuoteRetrofit(
        @Named("base_quote") base_url: String,
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder().client(okHttpClient)
            .baseUrl(base_url).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun providesQuote(@Named("retrofit_quote") retrofit: Retrofit): QutoeInterface =
        retrofit.create(QutoeInterface::class.java)


}