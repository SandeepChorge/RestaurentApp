package com.madtitan94.codengineapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.madtitan94.codengineapp.model.datamodel.OrderProduct
import com.madtitan94.codengineapp.model.datamodel.Product
import kotlinx.coroutines.flow.Flow

@Dao
abstract class OrderProductDao {
    @Query("SELECT * FROM OrderProduct WHERE transactionId LIKE :transactionId")
    abstract fun getOrderProductByTransactionID(transactionId: String): Flow<List<OrderProduct>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(orderProduct: OrderProduct):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(orderProduct: List<OrderProduct>):List<Long>
}