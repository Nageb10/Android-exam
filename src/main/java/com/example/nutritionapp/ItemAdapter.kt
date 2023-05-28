package com.example.nutritionapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.nutritionapp.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//Custom adapter for recyclerview that extends RecyclerView.Adapter
class ItemAdapter(private val contextSettings: Context, val allHits: ArrayList<AssetData>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

//Holds the different Views and are initialized using itemView parameter
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.imageview)
        val label: TextView = itemView.findViewById(R.id.labelView)
        val mealType: TextView = itemView.findViewById(R.id.mealTypeView)
        val dietLabel: TextView = itemView.findViewById(R.id.dietLabel)

    }
//Responsible for creating and returning a new viewHolder for each item in the data set.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }
//Retrieves the ViewHolder objects & sets the values of the holder objects using Asset(Data) object
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageView = holder.foodImage
        val label = holder.label
        val mealType = holder.mealType
        val dietLabel = holder.dietLabel
        val item = allHits[position]

        imageView.setImageBitmap(allHits.get(position).image)
        label.setText(allHits.get(position).label)
        mealType.setText(allHits.get(position).mealType)
        dietLabel.setText(allHits.get(position).dietLabel)

        val calorieValue = DataFromSetting(contextSettings).preferences.getInt("calories", 0)
        val calories = item.calories

//Makes the edges of the cards red if the calories are more than your preferred calorie intake
        if (calories != null){
            if (calorieValue.toString() >= calories){
                holder.itemView.setBackgroundColor(Color.parseColor("red"))
            }
        }
//At the onClick of the label user gets sent to the website of the recipe
        holder.label.setOnClickListener{
            val url = item.url
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            holder.itemView.context.startActivity(intent)

        }

    }
//Responsible for returning the number of items in the data set.
    override fun getItemCount(): Int {
        return allHits.size
    }
}
