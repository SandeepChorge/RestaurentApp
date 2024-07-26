package com.madtitan94.codengineapp.domain.repository

import androidx.annotation.WorkerThread
import com.madtitan94.codengineapp.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getAllTransactions(): Flow<List<Transaction>>
    suspend fun getMaxTransactionId(): Int
    suspend fun insert(transaction: Transaction)
}