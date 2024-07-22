package com.madtitan94.codengineapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.repository.ProductRepository
import com.madtitan94.codengineapp.utils.ProductCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    //private val productList = MediatorLiveData<List<Product>>()
    private lateinit var productList : LiveData<List<Product>>

    fun getProductList(): LiveData<List<Product>> {
        return productList
    }

    init {
        getProductByCategory(ProductCategory.BURGER.category)
    }

    fun getProductByCategory(category: String) {
        /*productList.addSource(repository.getProds(category).asLiveData()){
            peoples -> productList.postValue(peoples)
        }*/
        productList = repository.getProds(category).asLiveData()
    }


}