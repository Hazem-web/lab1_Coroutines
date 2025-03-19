package com.example.mvvm.network

import retrofit2.Response

class RemoteDataSourceImp private constructor():RemoteDataSource{

    companion object{
        private var INSTANCE:RemoteDataSourceImp?=null

        fun getInstance():RemoteDataSourceImp{
            return INSTANCE ?: synchronized(this){
                val instance=RemoteDataSourceImp()
                INSTANCE=instance
                instance
            }
        }
    }

    override suspend fun getProducts(): Response<ProductResponse> {
       return RetrofitHelper.apiService.getProducts()
    }
}