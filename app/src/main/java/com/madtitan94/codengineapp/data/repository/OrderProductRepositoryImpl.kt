package com.madtitan94.codengineapp.data.repository

import com.madtitan94.codengineapp.data.data_source.OrderProductDao
import com.madtitan94.codengineapp.domain.model.OrderProduct
import com.madtitan94.codengineapp.domain.repository.OrderProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderProductRepositoryImpl @Inject constructor(private val orderProductDao: OrderProductDao) :
    OrderProductRepository
{
    override fun getOrderProductByTransactionID(transactionID: String): Flow<List<OrderProduct>> {
        return orderProductDao.getOrderProductByTransactionID(transactionID)
    }

    override suspend fun insert(orderProduct: OrderProduct) {
        orderProductDao.insert(orderProduct)
    }

    override suspend fun insertAll(orderProduct: List<OrderProduct>) {
        orderProductDao.insertAll(orderProduct)
    }

}