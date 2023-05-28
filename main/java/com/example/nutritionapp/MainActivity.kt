package com.example.nutritionapp

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

//Defines a data access object (DAO) for a database.
//addSearch inserts a new row into the database
//getAll is a query that returns a list of the database objects
@Dao
interface SearchHistoryDao {
    @Insert fun addSearch(SearchHistory: SearchHistoryDb)

    @Query("SELECT * FROM SearchHistoryDb") fun getAll(): List<SearchHistoryDb>
}

//Extends the RoomDatabase class and defines an abstract SearchDB method
@Database (entities = [SearchHistoryDb:: class], version = 1)

abstract class AppDatabase : RoomDatabase(){
    abstract fun SearchDB(): SearchHistoryDao
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//Adapter variable is set as the adapter for the RecyclerView using the setAdapter method
        val adapter = ItemAdapter(this, allHits = ArrayList())
        val rvHomeScreen = findViewById<RecyclerView>(R.id.rvHomeScreen);
        rvHomeScreen.layoutManager = LinearLayoutManager(this)
        rvHomeScreen.adapter = adapter

//Retrieves the different views and give them a variable
        val searchHistoryBtn = findViewById<Button>(R.id.searchHistoryBtn)
        val settingsBtn = findViewById<Button>(R.id.settingsBtn)
        val searchLabelBtn = findViewById<Button>(R.id.searchLabelBtn)
        val searchText = findViewById<EditText>(R.id.searchText)

        rvHomeScreen.layoutManager = LinearLayoutManager(this);

//Hides the supportActionBar
        supportActionBar?.hide()

//Takes you to the searchHistory activity
        searchHistoryBtn.setOnClickListener {
            val intent = Intent(this, SearchHistory::class.java)
            startActivity(intent)
        }

//Takes you the Settings activity
        settingsBtn.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

//Sets the parameter to all in downloadAssetList which is the default start of our API
        GlobalScope.launch(Dispatchers.Main) {
            var allHits = downloadAssetList("all")
            rvHomeScreen.adapter = ItemAdapter(this@MainActivity, allHits)
        }
//When pressed search button takes the input of the input text and searches for it in the API
        searchLabelBtn.setOnClickListener() {
            GlobalScope.launch(Dispatchers.Main) {
                var allHits = downloadAssetList(searchText.text.toString())
                rvHomeScreen.adapter = ItemAdapter(this@MainActivity,allHits)

            }
        }

//Creates an instance of a database using the Room persistence library.
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "SearchHistoryDb").build()

//When pressed search the input gets pushed to the database
        searchLabelBtn.setOnClickListener{
            val input = findViewById<EditText>(R.id.searchText)
            GlobalScope.launch(Dispatchers.Main) {
                val allHits = downloadAssetList(input.text.toString())
                rvHomeScreen.adapter = ItemAdapter(this@MainActivity,allHits)
            }
            GlobalScope.launch(Dispatchers.IO) {
                val search = input.text.toString()
                val pushData = SearchHistoryDb()
                pushData.label = search
                db.SearchDB().addSearch(pushData)
                }
                }

            }
        }

// The code below reads and gets the API. We get all the info from the API which we feel is necessary information for the user
    suspend fun downloadAssetList(q: String): ArrayList<AssetData> {
        val allHits = ArrayList<AssetData>() // Makes arraylist of type assetData
        GlobalScope.async {
            val assetData =
                URL("https://api.edamam.com/api/recipes/v2?type=public&beta=true&q=${q}&app_id=b9228466&app_key=c95657f4ded585edabc141248a3fc3cb").readText() // This code reads and gets the API
            val assetHitsArray = (JSONObject(assetData).get("hits") as JSONArray)
            (0 until assetHitsArray.length()).forEach { item ->
                val hitsItem = AssetData()
                val assetItem = assetHitsArray.get(item)
                val recipe = (assetItem as JSONObject).getJSONObject("recipe")
                val images = (recipe).getJSONObject("images")
                val thumbnail = (images as JSONObject).getJSONObject("THUMBNAIL")
                val value = URL((thumbnail as JSONObject).getString("url").toString()).openStream()
                val bitmap = BitmapFactory.decodeStream((value))
                val calories = recipe.getInt("calories").toString()
                hitsItem.calories = calories
                hitsItem.label = recipe.getString("label")
                hitsItem.image = bitmap
                hitsItem.dietLabel = recipe.getJSONArray("dietLabels").toString()
                arrayOf(hitsItem.dietLabel).forEach {
                    item -> val dietLabel = mutableListOf<String>()
                    dietLabel.add(item.toString())
                    for (i in dietLabel){
                        hitsItem.dietLabel = i.replace(",", " ,").replace("[", " ").replace("\"", " ").replace("]", " ")
                    }
                }

                hitsItem.mealType = recipe.getJSONArray("mealType")[0].toString()
                hitsItem.cautions = recipe.getString("cautions")
                hitsItem.url = recipe.getString("url")
                allHits.add(hitsItem)

            }

        }.await()
        return allHits
    }















