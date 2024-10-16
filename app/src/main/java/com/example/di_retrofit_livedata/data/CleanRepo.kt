package com.example.di_retrofit_livedata.data

import com.example.di_retrofit_livedata.model.ProductList
import com.example.di_retrofit_livedata.model.loginData
import com.example.di_retrofit_livedata.sealed.ProductState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CleanRepo @Inject constructor(private val products: Products, private val login: login) {
//    suspend fun getProducts(limit : Int)  =  products.getProducts(limit)

    suspend fun getLogin(hashMap: HashMap<String, String>): Flow<ProductState<loginData>> =
        flow<ProductState<loginData>> {
            emit(ProductState.Loading())
            emit(ProductState.Success(login.getLogin(hashMap)))
        }.catch { e ->
            emit(ProductState.Error<loginData>(e))
        }


//    suspend fun getProducts(limit: Int): List<ProductList> {
//        return products.getProducts(limit) // This should be a suspend function that returns a List<ProductList>
//    }
//    fun getProducts(limit: Int): Flow<List<ProductList>> = flow {
//        val response : Response<List<ProductList>> = products.getProducts(limit)
//        if (response.isSuccessful) {
//            response.body()?.let { products ->
//                emit(products)
//            }
//        } else {
//            throw Exception("Failed to load products")
//        }
//    }

    suspend fun getProducts(limit: Int): Flow<ProductState<List<ProductList>>> =
        flow {
            emit(ProductState.Loading())
            emit(ProductState.Success(products.getProducts(limit)))
        }.catch { e ->
            emit(ProductState.Error(e))
        }
}