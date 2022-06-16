package com.madtitan94.codengineapp.utils

import android.app.Application
import com.madtitan94.codengineapp.model.database.AppDatabase
import com.madtitan94.codengineapp.model.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

public class CodeEngineApplication :Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this,applicationScope) }
    val prodRepository by lazy { ProductRepository(database.productDao()) }
}