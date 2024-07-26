package com.madtitan94.codengineapp.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.madtitan94.codengineapp.domain.model.Product
import com.madtitan94.codengineapp.domain.repository.ProductRepository
import com.madtitan94.codengineapp.presentation.utils.ProductCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    private val _productList = MutableStateFlow<List<Product>>(emptyList())

    // Expose as Flow for UI to collect
    val productList: Flow<List<Product>> = _productList

    init {
        getProductByCategory(ProductCategory.BURGER.category)
    }

    fun getProductByCategory(category: String) {
        viewModelScope.launch {
            repository.getProds(category).collect {
                _productList.value = it
            }
        }
    }
}