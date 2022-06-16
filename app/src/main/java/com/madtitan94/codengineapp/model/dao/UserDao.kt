package com.madtitan94.codengineapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.datamodel.User
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao {
    @Query("SELECT * FROM User WHERE username LIKE :username AND password LIKE :password")
    abstract fun login(username: String,password: String): Flow<List<User>>

    @Query("SELECT * FROM User")
    abstract fun AllUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(user: User):Long

    @Query("DELETE FROM User")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(users : List<User>):List<Long>
}