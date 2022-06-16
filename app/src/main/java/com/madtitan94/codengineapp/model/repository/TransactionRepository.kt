package com.madtitan94.codengineapp.model.repository

import androidx.annotation.WorkerThread
import com.madtitan94.codengineapp.model.dao.ProductDao
import com.madtitan94.codengineapp.model.dao.TransactionDao
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.datamodel.Transaction
import kotlinx.coroutines.flow.Flow

class TransactionRepository (private val transactionDao: TransactionDao) {

    suspend fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions()
    }

    suspend fun getMaxTransactionId(): Int {
        return transactionDao.getMaxTransactionId()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }
}