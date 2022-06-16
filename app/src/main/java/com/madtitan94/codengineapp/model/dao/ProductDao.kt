package com.madtitan94.codengineapp.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.madtitan94.codengineapp.model.datamodel.Product
import kotlinx.coroutines.flow.Flow
import java.util.*


@Dao
abstract class ProductDao {
   @Query("SELECT * FROM Product WHERE category LIKE :cat ORDER BY name ASC")
    abstract fun getProductsByCategory(cat: String): Flow<List<Product>>

    @Query("SELECT * FROM Product ORDER BY name ASC")
    abstract fun getAllProducts(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(product: Product):Long

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   abstract fun insertAll(products : List<Product>):List<Long>

    @Query("DELETE FROM Product")
    abstract fun deleteAll()

    @Query("SELECT * FROM Product WHERE id LIKE :id")
    abstract fun getProductById(id: Int): Flow<Product>

/*
@Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(vararg products: Product?)

    @Query("DELETE FROM Product")
    suspend fun deleteAll()*/
}