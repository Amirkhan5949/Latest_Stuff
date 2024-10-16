package com.example.di_retrofit_livedata.model

data class Quotes(
    val limit: Int,
    val quotes: ArrayList<Quote>,
    val skip: Int,
    val total: Int
)