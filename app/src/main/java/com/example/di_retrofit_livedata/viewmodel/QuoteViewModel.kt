package com.example.di_retrofit_livedata.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di_retrofit_livedata.data.QutoeRepository
import com.example.di_retrofit_livedata.model.Quotes
import com.example.di_retrofit_livedata.sealed.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(private val qutoeRepository: QutoeRepository) :
    ViewModel() {

    private val _quote : MutableStateFlow<ProductState<Quotes>> = MutableStateFlow(ProductState.Loading())
    var quote : StateFlow<ProductState<Quotes>> = _quote

    init {
        getQuotes()
    }

    fun getQuotes() {
     viewModelScope.launch {
          qutoeRepository.getDataFromLocalDb().collect{localdata->
           if (localdata==null){
               fetchFromApiAndStore()
           }else{
               _quote.value = localdata
           }
          }
      }

    }

    private fun fetchFromApiAndStore() {
        viewModelScope.launch {
            qutoeRepository.getQuotes().collect { state ->
                _quote.value = state
            }
        }
    }
}