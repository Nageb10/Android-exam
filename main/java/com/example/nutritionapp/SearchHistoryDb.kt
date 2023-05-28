package com.example.nutritionapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SearchHistoryDb" )
class SearchHistoryDb: Asset() {
    @PrimaryKey(autoGenerate = true) var Id = 0;
// This code defines a class called SearchHistoryDb that represents a search history item in a database. The class extends the Asset class
    // It also autoGenerates a new Id for every searched item
}