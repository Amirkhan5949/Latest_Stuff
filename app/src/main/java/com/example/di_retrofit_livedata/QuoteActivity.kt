package com.example.di_retrofit_livedata

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.di_retrofit_livedata.adapter.QuoteAdapter
import com.example.di_retrofit_livedata.model.Quote
import com.example.di_retrofit_livedata.sealed.ProductState
import com.example.di_retrofit_livedata.viewmodel.QuoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuoteActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var quoteAdapter: QuoteAdapter
    private  var  list : ArrayList<Quote> =   ArrayList()
    private val quoteViewModel  : QuoteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote)

        initialize()
        observer()

    }

    private fun observer(){
        lifecycleScope.launch {
            quoteViewModel.quote.collect{
                when(it){
                    is ProductState.Loading->{}
                    is ProductState.Success->{ Toast.makeText(
                        this@QuoteActivity,
                        "your data successfully fetched",
                        Toast.LENGTH_SHORT
                    ).show()
                        list.clear()
                        list.addAll( it.product.quotes)
                        quoteAdapter.notifyDataSetChanged()
                    }
                    is ProductState.Error->{
                        Toast.makeText(
                            this@QuoteActivity,
                            "error +${it.error}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }
    private fun initialize() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@QuoteActivity)
            quoteAdapter = QuoteAdapter(this@QuoteActivity.list)
            adapter = quoteAdapter
        }
    }
}