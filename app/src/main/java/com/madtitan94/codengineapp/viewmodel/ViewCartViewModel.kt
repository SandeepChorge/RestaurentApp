package com.madtitan94.codengineapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.room.FtsOptions
import com.madtitan94.codengineapp.model.datamodel.OrderProduct
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.repository.ProductRepository
import com.madtitan94.codengineapp.utils.CartManager
import com.madtitan94.codengineapp.utils.ProductCategory

class ViewCartViewModel(private val repository: ProductRepository) : ViewModel() {


    private val productList = MediatorLiveData<List<OrderProduct>>()

    fun getProductList(): LiveData<List<OrderProduct>> {
        return productList
    }
    init {
       // getProductByCategory()
    }

   /* fun getProductByCategory() {
        productList.addSource(CartManager.getFlowOrderProducts().asLiveData()){
                peoples -> productList.postValue(peoples)
            Log.e("Peoples","===>"+peoples.size)
        }
    }*/
/*

    public val product = MediatorLiveData<Product>()

    fun getProductLiveData(): LiveData<Product> {
        return product
    }

    fun getProductDetails(id: Int){
        product.addSource(repository.getProductByID(id).asLiveData()){
                p -> product.postValue(p)
        }
    }*/

    private val _prodQuantity = MutableLiveData<Int>().apply {
        value = 1
    }
    val prodQuantity: LiveData<Int> = _prodQuantity

    fun updateQuantity(q:Int){
        _prodQuantity.postValue(q)
    }

}

class ViewCartViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewCartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewCartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}