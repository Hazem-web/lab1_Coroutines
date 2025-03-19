package com.example.mvvm.db

import com.example.mvvm.models.Product
import kotlinx.coroutines.flow.Flow

interface LocalDataStore {

    suspend fun getProducts():Flow<List<Product>>

    suspend fun addProduct(product: Product):Long

    suspend fun deleteProduct(product: Product):Int
}