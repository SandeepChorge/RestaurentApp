package com.madtitan94.codengineapp.data.repository

import com.madtitan94.codengineapp.data.data_source.ProductDao
import com.madtitan94.codengineapp.domain.model.Product
import com.madtitan94.codengineapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val productDao: ProductDao)
    : ProductRepository {
    override fun getProds(string: String): Flow<List<Product>> {
        return productDao.getProductsByCategory(string)
    }

    override fun getProductByID(id: Int): Flow<Product> {
        return productDao.getProductById(id)
    }

    override suspend fun insert(product: Product) {
        productDao.insert(product)
    }
}