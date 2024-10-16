package com.example.di_retrofit_livedata

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.di_retrofit_livedata.R.layout.activity_main
import com.example.di_retrofit_livedata.viewmodel.PostVIewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var file: File
    private lateinit var button : Button
    private lateinit var uploadData : Button
    private lateinit var et_des : EditText
    private lateinit var image : ImageView
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    private val postVIewModel: PostVIewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
         button = findViewById(R.id.btn_pick)
        uploadData = findViewById(R.id.btn_upload)
         et_des = findViewById(R.id.et_description)
         image = findViewById(R.id.iv_Image)

//        getFile()
        initial()
//        observer()
//        postVIewModel.getPost()
//        postVIewModel.getComments()
    }

    fun observer(){

        postVIewModel.getPostComments().observe(this) {
            Log.i("Ammafafir", "onCreate: data" + it.get(1).body)
        }

        postVIewModel.uploadResult.observe(this) { response ->
            if (response != null) {
                if (response.isSuccessful) {
                    // Handle successful response
                    Toast.makeText(this, "File uploaded successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle failure (check for error message in the body)
                    val errorMsg = response.errorBody()?.string() ?: "Unknown error occurred"
                    Toast.makeText(this, "Upload failed: $errorMsg", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Handle null response
                Toast.makeText(
                    this,
                    "An unknown error occurred during the upload.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        postVIewModel.getPostLiveData().observe(this) {
            Log.i("Ammafafir", "onCreate: data" + it.get(1).body)
        }
    }
    fun initial() {
        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            pickImageLauncher.launch(intent)
        }

        uploadData.setOnClickListener {
//            if (file != null) {
//                postVIewModel.uploadFile(et_des.text.toString(), file)
//            } else {
//                Toast.makeText(this, "Please File uploaded!", Toast.LENGTH_SHORT).show()
//            }
            startActivity(Intent(this,MainActivity2::class.java))
        }
    }

    fun getFile() {
        pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val uri = result.data?.data
                    if (uri != null) {
                        file = File(getRealPathFromURI(uri)) // Convert URI to file
                        // Proceed with your Retrofit request using the selected file
                        Log.i("vgchgfhgvgh", "onCreate: " + uri)
                        Log.i("vgchgfhgvgh", "onCreate: " + file)
                        image.setImageURI(uri)
                    }
                }
            }
    }

    fun getRealPathFromURI(uri: Uri): String {
        var path = ""
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            path = cursor.getString(idx)
            cursor.close()
        }
        return path
    }
}
