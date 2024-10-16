package com.example.di_retrofit_livedata.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di_retrofit_livedata.data.CleanRepo
import com.example.di_retrofit_livedata.model.ProductList
import com.example.di_retrofit_livedata.model.loginData
import com.example.di_retrofit_livedata.sealed.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class CleanViewModel @Inject constructor(private val cleanRepo: CleanRepo) : ViewModel() {

    /*private var _product : MutableLiveData<List<ProductList>> = MutableLiveData()
     var product : LiveData<List<ProductList>> = _product

     fun getProducts(): LiveData<List<ProductList>> = product

    fun getProductList(limit : Int) = viewModelScope.launch {
        val data = cleanRepo.getProducts(limit)
        _product.value = data
    }*/

    private val _product : MutableStateFlow<ProductState<List<ProductList>>> = MutableStateFlow(ProductState.Loading())
    var product : StateFlow<ProductState<List<ProductList>>> = _product


    fun getProductList(limit : Int){
        viewModelScope.launch {
            cleanRepo.getProducts(limit).collect{
                _product.value = it
            }
        }
    }

    private var _login = MutableStateFlow<ProductState<loginData>?>(null)
     val login: StateFlow<ProductState<loginData>?> = _login

    fun getLogin(hashMap: HashMap<String, String>) {
        viewModelScope.launch {
            cleanRepo.getLogin(hashMap).collect {
                _login.value = it
            }
        }
    }
}