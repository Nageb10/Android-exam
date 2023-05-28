package com.example.nutritionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.R.layout
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        val settings = getSharedPreferences("settings", MODE_PRIVATE)
        val user = settings.edit()
// Find by id's
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val clearBtn = findViewById<Button>(R.id.clearBtn)

        val dietLabelDropdown = findViewById<Spinner>(R.id.dietLabelDropdown)
        val mealTypeDropdown = findViewById<Spinner>(R.id.priorityDropdown)
// Creating options for the Spinners
        val dietLabel = arrayOf("Low Calorie", "Low Carb", "Atkins", "Low Fat")
        val mealType = arrayOf("Breakfast", "Lunch", "Dinner")
        val dailyIntakeEditText = findViewById<EditText>(R.id.dailyIntakeEditText)

        saveBtn.setOnClickListener{
            val input = dailyIntakeEditText.text.toString().toInt()// Takes input
            val intent = Intent(this, MainActivity::class.java)// Updates Main
            user.putInt("calories", input).apply() // Applies the input value
            startActivity(intent) // Sends user back to home screen
        }
        clearBtn.setOnClickListener{
           dailyIntakeEditText.text.clear() // Clears input values
            settings.edit().clear().apply() // Clears setting changes
            val intent = Intent(this, MainActivity::class.java) // Updates Main
            startActivity(intent) // Sends user back to home screen
        }

        val adapter = ArrayAdapter(this, layout.simple_spinner_dropdown_item, dietLabel)
        val adapter2 = ArrayAdapter(this, layout.simple_spinner_dropdown_item, mealType)

        dietLabelDropdown.adapter = adapter
        mealTypeDropdown.adapter = adapter2

        dietLabelDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(applicationContext, "selected category is = " + dietLabel[position], Toast.LENGTH_SHORT).show() // Lets user know what diet label is chosen
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        mealTypeDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(applicationContext, "selected category is = " + mealType[position], Toast.LENGTH_SHORT).show() // Lets user know what meal Type is chosen
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

}