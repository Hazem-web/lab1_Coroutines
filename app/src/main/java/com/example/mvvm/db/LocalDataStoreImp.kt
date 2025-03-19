package com.example.mvvm.db

import android.content.Context
import com.example.mvvm.models.Product
import kotlinx.coroutines.flow.Flow

class LocalDataStoreImp private constructor(context: Context) :LocalDataStore {

    private val productsDao:ProductDao

    init {
        val database=ProductDatabase.getInstance(context)
        productsDao=database.getProductDao()
    }

    companion object{
        @Volatile
        private var INSTANCE:LocalDataStoreImp? = null

        fun getInstance(context: Context):LocalDataStoreImp{
            return INSTANCE ?: synchronized(this){
                val instance=LocalDataStoreImp(context)
                INSTANCE=instance
                instance
            }
        }
    }

    override suspend fun getProducts(): Flow<List<Product>> {
        return productsDao.getAll()
    }

    override suspend fun addProduct(product: Product): Long {
        return productsDao.insert(product)
    }

    override suspend fun deleteProduct(product: Product): Int {
        return productsDao.delete(product)
    }

}