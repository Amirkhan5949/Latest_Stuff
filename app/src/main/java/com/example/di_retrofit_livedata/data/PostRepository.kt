package com.example.di_retrofit_livedata.data

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class PostRepository @Inject constructor(private val req : PostRequest,private val comments: Comments,private val apiService: ApiService) {
    suspend fun getPostReq() = req.getpostlist()

    suspend fun getPostComments() = comments.getComments()

    suspend fun getPostUpload(description: String, file: File): Response<ResponseBody> {
        val descriptionRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), description)

        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file) // Adjust media type as needed
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

        // Call the Retrofit function
        return apiService.uploadFile(descriptionRequestBody, filePart)
    }

}