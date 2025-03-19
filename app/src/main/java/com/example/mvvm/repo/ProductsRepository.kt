package com.example.mvvm.repo

import com.example.mvvm.models.Product
import com.example.mvvm.network.ProductResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ProductsRepository {

    suspend fun getAllFromInternet(): Response<ProductResponse>

    suspend fun addFav(product: Product): Long

    suspend fun getAllFromDatabase(): Flow<List<Product>>

    suspend fun removeFav(product: Product): Int
}