package com.madtitan94.codengineapp.viewmodel

import androidx.lifecycle.*
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.repository.ProductRepository
import com.madtitan94.codengineapp.utils.ProductCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AddToCartActivityViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    private lateinit var  product : LiveData<Product>

    fun getProductLiveData(): LiveData<Product> {
        return product
    }

    fun getProductDetails(id: Int){
       product = repository.getProductByID(id).asLiveData()
        /* product.addSource(repository.getProductByID(id).asLiveData()){
                p -> product.postValue(p)
        }*/
    }

    private val _prodQuantity = MutableLiveData<Int>().apply {
        value = 1
    }
    val prodQuantity: LiveData<Int> = _prodQuantity

    fun updateQuantity(q:Int){
        _prodQuantity.postValue(q)
    }

}

