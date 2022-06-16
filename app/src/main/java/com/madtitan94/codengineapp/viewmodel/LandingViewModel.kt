package com.madtitan94.codengineapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.repository.ProductRepository
import com.madtitan94.codengineapp.utils.ProductCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class LandingViewModel(private val repository: ProductRepository) : ViewModel() {

    private val productList = MediatorLiveData<List<Product>>()

    fun getProductList(): LiveData<List<Product>> {
        return productList
    }

    init {
        getProductByCategory(ProductCategory.BURGER.category)
    }

    fun getProductByCategory(category: String) {
        productList.addSource(repository.getProds(category).asLiveData()){
            peoples -> productList.postValue(peoples)
        }
    }

    suspend fun <T> Flow<List<T>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()

    /*private val _text = MutableLiveData<String>().apply {
        value = ProductCategory.BURGER.category
    }
    val text: LiveData<String> = _text
*/
}
    class LandingViewModelFactory(private val repository: ProductRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LandingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LandingViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
