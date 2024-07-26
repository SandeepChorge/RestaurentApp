package com.madtitan94.codengineapp.presentation.viewcart

import androidx.lifecycle.ViewModel
import com.madtitan94.codengineapp.domain.model.OrderProduct
import com.madtitan94.codengineapp.domain.model.Transaction
import com.madtitan94.codengineapp.domain.repository.OrderProductRepository
import com.madtitan94.codengineapp.domain.repository.ProductRepository
import com.madtitan94.codengineapp.domain.repository.TransactionRepository
import com.madtitan94.codengineapp.presentation.utils.CartManager
import com.madtitan94.codengineapp.presentation.utils.CartManager.makeLog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewCartViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val orderProductRepo: OrderProductRepository,
    private val transactionRepo: TransactionRepository
) : ViewModel() {
    suspend fun confirmOrder() {

        var transactionId = transactionRepo.getMaxTransactionId()
        if (transactionId < 0) {
            transactionId = 1;
        } else {
            transactionId += 1
        }

        var transaction = Transaction(
            transactionId,
            "#00$transactionId",
            CartManager.orderTotalDetails.subTotal.toDouble(),
            CartManager.orderTotalDetails.total.toDouble(),
            CartManager.orderTotalDetails.totalTax.toDouble(),
            "Confirmed",
            CartManager.GetFormmatedDateTime(),
            CartManager.customerDetails.firstName,
            CartManager.customerDetails.lastName,
            CartManager.customerDetails.mobile,
            CartManager.customerDetails.email
        )

        var res = transactionRepo.insert(transaction)
        makeLog("TRANSACTION INSERT LOG IS $res")

        var updatedTransactionId = transactionRepo.getMaxTransactionId()

        val odList: MutableList<OrderProduct> = ArrayList()
        CartManager.orderProducts.value?.forEach { orderProduct ->
            orderProduct.transactionId = updatedTransactionId
            odList.add(orderProduct)
        }

        var res2 = orderProductRepo.insertAll(odList)

        makeLog("Inserted order products $res2")

        CartManager.clear()

    }
}