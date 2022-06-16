package com.madtitan94.codengineapp.model.repository

import androidx.annotation.WorkerThread
import com.madtitan94.codengineapp.model.dao.ProductDao
import com.madtitan94.codengineapp.model.dao.UserDao
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.datamodel.User
import kotlinx.coroutines.flow.Flow

class UserRepository (private val userDao: UserDao) {

     fun login(username: String,password: String): Flow<List<User>> {
        return userDao.login(username,password)
    }

     fun Allusers(): Flow<List<User>> {
        return userDao.AllUsers()
    }

}