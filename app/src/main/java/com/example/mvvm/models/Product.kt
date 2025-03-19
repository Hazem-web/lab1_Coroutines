package com.example.mvvm.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "PRODUCTS")
data class Product (
    @PrimaryKey
    @NonNull
    var title: String = "",
    var price: Double = 0.0,
    var description: String? = null,
    var category: String? = null,
    @SerializedName("thumbnail")
     var imgUrl: String? = null
)