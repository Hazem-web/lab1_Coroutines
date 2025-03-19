package com.example.mvvm.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val instance= Retrofit
        .Builder()
        .baseUrl("https://dummyjson.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: ProductService = instance.create(ProductService::class.java)
}