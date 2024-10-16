package com.example.di_retrofit_livedata.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.di_retrofit_livedata.R
import com.example.di_retrofit_livedata.model.Quote

class QuoteAdapter(private val quotes: ArrayList<Quote>) : RecyclerView.Adapter<QuoteAdapter.QutoeViewHolder>() {

    class QutoeViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val tvQuote: TextView = itemView.findViewById(R.id.tvQuote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QutoeViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.qutoe,parent,false)
        return QutoeViewHolder(itemview)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    override fun onBindViewHolder(holder: QutoeViewHolder, position: Int) {
        val currentQuote = quotes[position]
        Log.i("fsfsgsgg", "onBindViewHolder: $quotes")
        if (quotes!=null){
            holder.tvAuthor.text = currentQuote.author
            holder.tvQuote.text = currentQuote.quote
        }

    }
}