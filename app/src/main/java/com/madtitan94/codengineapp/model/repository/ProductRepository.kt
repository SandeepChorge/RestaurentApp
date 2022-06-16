package com.madtitan94.codengineapp.model.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.madtitan94.codengineapp.model.dao.ProductDao
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.utils.ProductCategory
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    fun getProds(string: String): Flow<List<Product>>{
        return productDao.getProductsByCategory(string)
    }

    fun getProductByID(id: Int):Flow<Product>{
        return productDao.getProductById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }
}