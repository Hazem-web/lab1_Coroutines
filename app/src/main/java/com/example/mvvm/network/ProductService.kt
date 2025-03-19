package com.example.mvvm.network

import retrofit2.Response
import retrofit2.http.GET

interface ProductService {

    @GET("products")
    suspend fun getProducts(): Response<ProductResponse>
}