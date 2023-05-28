package com.example.nutritionapp

import android.graphics.Bitmap
import java.net.URL

class AssetData : Asset() {
    // Subclass of Asset class, stores data about an assets variable
    var image: Bitmap? = null;
    var cautions: String? = null;
    var url: String? = null;
    var mealType: String? = null
    var dietLabel: String? = null
    var yield: String? = null;
    var healthLabels: String? = null;
    var ingredientList: String? = null;
    var calories: String? = null;
    var priority: String? = null;
    var cookingTime: String? = null;
    var cuisineType: String? = null;
    var dishType: String? = null;
    var totalNutrients: String? = null;

}