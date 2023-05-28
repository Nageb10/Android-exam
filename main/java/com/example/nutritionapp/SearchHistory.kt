package com.example.nutritionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchHistory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_history)

        val db = Room.databaseBuilder(this, AppDatabase::class.java, "SearchHistoryDb").build()
        val adapter = ItemAdapterHistory(result = ArrayList(), this)
        val rv = findViewById<RecyclerView>(R.id.recyclerViewHistory)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter


        GlobalScope.launch(Dispatchers.IO){
            var result = db.SearchDB().getAll()
            rv.adapter = ItemAdapterHistory(result as ArrayList<SearchHistoryDb>, this@SearchHistory )
        }

}}