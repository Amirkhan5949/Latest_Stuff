package com.example.di_retrofit_livedata.sealed

sealed class ProductState<T> {
    class Loading<T> : ProductState<T>()
    data class Success<T>(val product: T) : ProductState<T>()
    data class Error<T>(val error: Throwable) : ProductState<T>()
}