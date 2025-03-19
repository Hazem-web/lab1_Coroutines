package com.example.mvvm.models

sealed class Response {
    data object Loading : Response()
    data class Success(val data: List<Product>) : Response()
    data class Failure(val error: Throwable) : Response()
}