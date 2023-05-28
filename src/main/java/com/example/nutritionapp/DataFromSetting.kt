package com.example.nutritionapp

import android.content.Context

class DataFromSetting(settingContext: Context) {
    //Retrieves a sharedPreferences that you can access and modify in a file
    val preferences = settingContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

}