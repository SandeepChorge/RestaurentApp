package com.madtitan94.codengineapp.model.repository

import androidx.annotation.WorkerThread
import com.madtitan94.codengineapp.model.dao.OrderProductDao
import com.madtitan94.codengineapp.model.dao.ProductDao
import com.madtitan94.codengineapp.model.datamodel.OrderProduct
import com.madtitan94.codengineapp.model.datamodel.Product
import kotlinx.coroutines.flow.Flow

class OrderProductRepository(private val orderProductDao: OrderProductDao) {

    fun getOrderProductByTransactionID(transactionID: String): Flow<List<OrderProduct>> {
        return orderProductDao.getOrderProductByTransactionID(transactionID)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(orderProduct: OrderProduct) {
        orderProductDao.insert(orderProduct)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAll(orderProduct: List<OrderProduct>) {
        orderProductDao.insertAll(orderProduct)
    }
}