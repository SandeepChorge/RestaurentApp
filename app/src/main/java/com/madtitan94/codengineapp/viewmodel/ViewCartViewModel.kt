package com.madtitan94.codengineapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.room.FtsOptions
import com.madtitan94.codengineapp.model.datamodel.OrderProduct
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.datamodel.Transaction
import com.madtitan94.codengineapp.model.repository.OrderProductRepository
import com.madtitan94.codengineapp.model.repository.ProductRepository
import com.madtitan94.codengineapp.model.repository.TransactionRepository
import com.madtitan94.codengineapp.utils.CartManager
import com.madtitan94.codengineapp.utils.CartManager.makeLog
import com.madtitan94.codengineapp.utils.ProductCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewCartViewModel(private val repository: ProductRepository,
                        private val orderProductRepo: OrderProductRepository,
                        private val transactionRepo: TransactionRepository) : ViewModel() {
     suspend fun confirmOrder() {

            var transactionId = transactionRepo.getMaxTransactionId()
            if (transactionId<0){
               transactionId = 1;
            }else{
                transactionId = transactionId+1
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
            makeLog("TRANSACTION INSERT LOG IS "+res)

            var updatedTransactionId = transactionRepo.getMaxTransactionId()

         val odList: MutableList<OrderProduct> = ArrayList()
            CartManager.orderProducts.value?.forEach { orderProduct ->
                orderProduct.transactionId = updatedTransactionId
                odList.add(orderProduct)
            }

                var res2 = orderProductRepo.insertAll(odList)

            makeLog("Inserted order products "+res2)

         CartManager.clear()

    }

}

class ViewCartViewModelFactory(private val repository: ProductRepository,
                               private val orderProductRepo: OrderProductRepository,
                               private val transactionRepo: TransactionRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewCartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewCartViewModel(repository,orderProductRepo,transactionRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}