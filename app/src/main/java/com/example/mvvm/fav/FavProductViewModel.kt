package com.example.mvvm.fav


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mvvm.models.Product
import com.example.mvvm.models.Response
import com.example.mvvm.repo.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavProductViewModel(private val repo:ProductsRepository): ViewModel() {
    private val mutableProducts: MutableStateFlow<Response> = MutableStateFlow(Response.Loading)
    val product:StateFlow<Response> = mutableProducts

    fun deleteProduct(product: Product?){
        if (product!=null){
            viewModelScope.launch(Dispatchers.IO) {
                repo.removeFav(product)
            }
        }
    }

    fun getAll(){
        viewModelScope.launch(Dispatchers.IO) {
            val list=repo.getAllFromDatabase()
            list
                .catch {
                    mutableProducts.value=Response.Failure(it)
                }
                .collect{
                mutableProducts.value= Response.Success(it)
            }

        }
    }
}

class FavProductViewModelFactory(private val repo:ProductsRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavProductViewModel(repo) as T

    }
}