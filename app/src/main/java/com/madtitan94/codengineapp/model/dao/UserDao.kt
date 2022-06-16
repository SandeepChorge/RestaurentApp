package com.madtitan94.codengineapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.datamodel.User

@Dao
abstract class UserDao {
    @Query("SELECT * FROM User WHERE username LIKE :username AND password LIKE :password")
    abstract fun login(username: String,password: String): List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(user: User):Long
}