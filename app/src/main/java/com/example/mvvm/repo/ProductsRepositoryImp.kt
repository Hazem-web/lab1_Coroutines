package com.example.mvvm.repo

import com.example.mvvm.db.LocalDataStore
import com.example.mvvm.db.LocalDataStoreImp
import com.example.mvvm.models.Product
import com.example.mvvm.network.ProductResponse
import com.example.mvvm.network.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class ProductsRepositoryImp(
    private val localDataStore: LocalDataStore,
    private val remoteDataSource: RemoteDataSource
) : ProductsRepository {
    companion object{
        private var INSTANCE:ProductsRepositoryImp?=null

        fun getInstance(localDataStore: LocalDataStore,remoteDataSource: RemoteDataSource):ProductsRepositoryImp{
            return INSTANCE ?: synchronized(this){
                val instance=ProductsRepositoryImp(localDataStore, remoteDataSource)
                INSTANCE=instance
                instance
            }
        }

    }

    override suspend fun getAllFromInternet(): Response<ProductResponse> {
        return remoteDataSource.getProducts()
    }

    override suspend fun addFav(product: Product): Long {
        return localDataStore.addProduct(product)
    }

    override suspend fun getAllFromDatabase(): Flow<List<Product>> {
        return localDataStore.getProducts()
    }

    override suspend fun removeFav(product: Product): Int {
        return localDataStore.deleteProduct(product)
    }

}