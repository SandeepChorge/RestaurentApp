package com.madtitan94.codengineapp.domain.repository

import androidx.annotation.WorkerThread
import com.madtitan94.codengineapp.domain.model.OrderProduct
import kotlinx.coroutines.flow.Flow

interface OrderProductRepository {
    fun getOrderProductByTransactionID(transactionID: String): Flow<List<OrderProduct>>
    suspend fun insert(orderProduct: OrderProduct)
    suspend fun insertAll(orderProduct: List<OrderProduct>)
}