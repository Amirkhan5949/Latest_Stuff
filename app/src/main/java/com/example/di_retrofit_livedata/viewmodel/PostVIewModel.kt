package com.example.di_retrofit_livedata.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.di_retrofit_livedata.data.PostRepository
import com.example.di_retrofit_livedata.model.Comments
import com.example.di_retrofit_livedata.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PostVIewModel @Inject constructor(val repo : PostRepository) : ViewModel() {
  private  val _post : MutableLiveData<List<Post>> = MutableLiveData()
    private val post : LiveData<List<Post>> = _post

    private val _comments : MutableLiveData<List<Comments>> = MutableLiveData()
    private val comments : LiveData<List<Comments>> = _comments

    private val _uploadResult = MutableLiveData<Response<ResponseBody>>()
    val uploadResult: LiveData<Response<ResponseBody>> = _uploadResult

    fun getPostLiveData() : LiveData<List<Post>> = post

    fun getPostComments() : LiveData<List<Comments>> = comments
    fun getPost() = viewModelScope.launch {
        val posts = repo.getPostReq()
        _post.value = posts
    }

    fun getComments() = viewModelScope.launch {
        val comments = repo.getPostComments()
        _comments.value = comments
    }

    fun uploadFile(description: String, file: File) {
        viewModelScope.launch {
            try {
                // Make the network call through the repository
                val response = repo.getPostUpload(description, file)

                // Post the result to LiveData
                _uploadResult.postValue(response)
            } catch (e: Exception) {
                // Handle the exception and post a dummy Response to indicate failure
                val errorResponse = Response.error<ResponseBody>(
                    500, // You can customize the error code if you want
                    ResponseBody.create(null, "Network error occurred: ${e.message}")
                )
                _uploadResult.postValue(errorResponse)
            }
        }
    }
    }