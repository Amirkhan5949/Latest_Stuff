package com.example.di_retrofit_livedata


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.di_retrofit_livedata.sealed.ProductState
import com.example.di_retrofit_livedata.viewmodel.CleanViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity2 : AppCompatActivity() {
    private lateinit var et_user_name: EditText
    private lateinit var et_user_pass: EditText
    private lateinit var btn_login: Button
    lateinit var progressDialog: ProgressDialog
    private val cleanViewModel: CleanViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        initialize()
        observer()

        productObserver()
        cleanViewModel.getProductList(limit = 10)

    }

    private fun initialize() {
        et_user_name = findViewById(R.id.et_user_name)
        et_user_pass = findViewById(R.id.et_password)
        btn_login = findViewById(R.id.btn_login)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false) // Prevent user from dismissing it


        btn_login.setOnClickListener {
            if (validateInput()) {
                val hasMap = HashMap<String, String>().apply {
                    put("username", et_user_name.text.toString())
                    put("password", et_user_pass.text.toString())
                    put("expiresInMins", "30")
                }
                cleanViewModel.getLogin(hasMap)
            }
        }
    }

    fun productObserver() {
        lifecycleScope.launch {
            cleanViewModel.product.collect {
                when (it) {
                    is ProductState.Loading -> {
                        progressDialog.show()
                        Toast.makeText(this@MainActivity2, "product is loading", Toast.LENGTH_SHORT)
                            .show()
                    }

                    is ProductState.Success -> {
                        progressDialog.dismiss()
                        Toast.makeText(
                            this@MainActivity2,
                            "product is successfully loaded",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@MainActivity2,QuoteActivity::class.java))
                    }

                    is ProductState.Error -> {
                        progressDialog.dismiss()
                        Toast.makeText(
                            this@MainActivity2,
                            "error + ${it.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }
        }
    }

    fun observer() {
        lifecycleScope.launch {
            cleanViewModel.login.collect {
                when (it) {
                    is ProductState.Loading -> {
                        progressDialog.show()

                        Log.d("Observer", "observer: ")
                    }

                    is ProductState.Success -> {
                        progressDialog.dismiss()
                        Toast.makeText(
                            this@MainActivity2,
                            "you successfully logged in",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@MainActivity2,QuoteActivity::class.java))
                        Log.d("Observer", "observer: ${it.product.firstName}")
                    }

                    is ProductState.Error -> {
                        progressDialog.dismiss()
                        Toast.makeText(this@MainActivity2, "error +${it.error}", Toast.LENGTH_SHORT)
                            .show()

                        Log.d("Observer", "observer: ${it.error}")
                    }

                    else -> {
                        progressDialog.dismiss()
                    }
                }
            }
        }
    }

    fun validateInput(): Boolean {
        val userName = et_user_name.text.toString().trim()
        val userPass = et_user_pass.text.toString().trim()

        if (userName.isEmpty()) {
            et_user_name.error = "Please enter your username"
            return false
        }
        if (userPass.isEmpty()) {
            et_user_pass.error = "Please enter your password"
            return false
        }
        return true
    }
}
