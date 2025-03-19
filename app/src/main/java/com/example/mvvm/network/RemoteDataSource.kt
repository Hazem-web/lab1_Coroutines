package com.example.mvvm.network

import retrofit2.Response

interface RemoteDataSource {
    suspend fun getProducts(): Response<ProductResponse>
}