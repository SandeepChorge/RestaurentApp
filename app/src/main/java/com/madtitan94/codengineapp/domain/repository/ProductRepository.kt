package com.madtitan94.codengineapp.domain.repository

import androidx.annotation.WorkerThread
import com.madtitan94.codengineapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProds(string: String): Flow<List<Product>>
    fun getProductByID(id: Int): Flow<Product>
    suspend fun insert(product: Product)
}