package com.madtitan94.codengineapp.data.repository

import com.madtitan94.codengineapp.data.data_source.UserDao
import com.madtitan94.codengineapp.domain.model.User
import com.madtitan94.codengineapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao): UserRepository {
    override fun login(username: String, password: String): Flow<List<User>> {
        return userDao.login(username,password)
    }

    override fun allUsers(): Flow<List<User>> {
        return userDao.AllUsers()
    }
}