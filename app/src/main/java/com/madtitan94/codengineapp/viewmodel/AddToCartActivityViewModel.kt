package com.madtitan94.codengineapp.viewmodel

import androidx.lifecycle.*
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.repository.ProductRepository
import com.madtitan94.codengineapp.utils.ProductCategory
import kotlinx.coroutines.flow.Flow

class AddToCartActivityViewModel(private val repository: ProductRepository) : ViewModel() {

    public val product = MediatorLiveData<Product>()

    fun getProductLiveData(): LiveData<Product> {
        return product
    }

    fun getProductDetails(id: Int){
        product.addSource(repository.getProductByID(id).asLiveData()){
                p -> product.postValue(p)
        }
    }

    private val _prodQuantity = MutableLiveData<Int>().apply {
        value = 1
    }
    val prodQuantity: LiveData<Int> = _prodQuantity

    fun updateQuantity(q:Int){
        _prodQuantity.postValue(q)
    }

}

class AddToCartViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddToCartActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddToCartActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}