package com.example.mvvm.online

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mvvm.models.Product
import com.example.mvvm.repo.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnlineProductViewModel(private val repo: ProductsRepository): ViewModel() {
    private val mutableProducts: MutableLiveData<List<Product>> = MutableLiveData()
    val product: LiveData<List<Product>> = mutableProducts

    fun addProduct(product: Product?){
        if (product!=null){
            viewModelScope.launch(Dispatchers.IO) {
                repo.addFav(product)
            }
        }
    }

    fun getAll(){
        viewModelScope.launch(Dispatchers.IO) {
                val response=repo.getAllFromInternet()
                if (response.isSuccessful)
                    mutableProducts.postValue(response.body()?.products)
        }
    }
}

class OnlineProductViewModelFactory(private val repo: ProductsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OnlineProductViewModel(repo) as T
    }
}