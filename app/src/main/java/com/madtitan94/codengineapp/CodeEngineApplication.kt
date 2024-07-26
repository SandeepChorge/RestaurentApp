package com.madtitan94.codengineapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
public class CodeEngineApplication :Application() {

    /*
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AppDatabase.getDatabase(this,applicationScope) }
    val prodRepository by lazy { ProductRepository(database.productDao()) }
    val orderProdRepository by lazy { OrderProductRepository(database.orderProductDao()) }
    val transactionRepository by lazy { TransactionRepository(database.transactionDao()) }
    val userRepository by lazy { UserRepository(database.userDao())}*/
}