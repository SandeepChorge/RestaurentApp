package com.madtitan94.codengineapp.data.repository

import com.madtitan94.codengineapp.data.data_source.TransactionDao
import com.madtitan94.codengineapp.domain.model.Transaction
import com.madtitan94.codengineapp.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(private val transactionDao: TransactionDao):
    TransactionRepository {
    override suspend fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions()
    }

    override suspend fun getMaxTransactionId(): Int {
        return transactionDao.getMaxTransactionId()
    }

    override suspend fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }
}