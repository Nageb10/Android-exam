package com.example.nutritionapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//Uses the SearchHistoryDb objects in the result list to create the views that will be displayed in the RecyclerView
class ItemAdapterHistory(private val result: ArrayList<SearchHistoryDb>, val context: Context) :
RecyclerView.Adapter<ItemAdapterHistory.ViewHolder>() {

//Holds the view and are initialized using itemView parameter
    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var historyLabel: TextView? = itemView.findViewById(R.id.historyLabel)
    }

//Responsible for creating and returning a new viewHolder for each item in the data set.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }
//Sets the value of the historyLabel and converting it to a string
    override fun onBindViewHolder(holder: ItemAdapterHistory.ViewHolder, position: Int) {
        val element = result[position]
        val labelHolder = holder.historyLabel

        labelHolder?.text = element.label.toString()


    }
//Responsible for returning the number of items in the data set.
    override fun getItemCount(): Int {
        return result.size
    }

}
