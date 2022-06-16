package com.madtitan94.codengineapp.utils

import android.app.Application
import com.madtitan94.codengineapp.model.database.AppDatabase
import com.madtitan94.codengineapp.model.repository.OrderProductRepository
import com.madtitan94.codengineapp.model.repository.ProductRepository
import com.madtitan94.codengineapp.model.repository.TransactionRepository
import com.madtitan94.codengineapp.model.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

public class CodeEngineApplication :Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this,applicationScope) }
    val prodRepository by lazy { ProductRepository(database.productDao()) }
    val orderProdRepository by lazy { OrderProductRepository(database.orderProductDao()) }
    val transactionRepository by lazy { TransactionRepository(database.transactionDao()) }
    val userRepository by lazy { UserRepository(database.userDao())}
}