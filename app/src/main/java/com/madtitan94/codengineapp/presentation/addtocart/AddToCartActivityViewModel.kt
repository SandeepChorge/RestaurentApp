package com.madtitan94.codengineapp.presentation.addtocart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.madtitan94.codengineapp.domain.model.Product
import com.madtitan94.codengineapp.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class AddToCartActivityViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    //private lateinit var  product : LiveData<Product>

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    fun getProductDetails(id: Int){
       viewModelScope.launch {
           repository.getProductByID(id).collect {
               _product.value = it
           }
       }
    }

    private val _prodQuantity = MutableStateFlow(0)
    val prodQuantity: StateFlow<Int> = _prodQuantity

    fun updateQuantity(q:Int){
        _prodQuantity.value = q
    }

}

