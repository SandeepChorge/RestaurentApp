package com.madtitan94.codengineapp.domain.repository

import com.madtitan94.codengineapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun login(username: String,password: String): Flow<List<User>>
    fun allUsers(): Flow<List<User>>
}