package com.madtitan94.codengineapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.datamodel.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract
class TransactionDao{

    @Query("SELECT * FROM `Transaction` ORDER BY id DESC")
    abstract fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT MAX(id) FROM `Transaction`")
    abstract fun getMaxTransactionId(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(transaction: Transaction):Long

}