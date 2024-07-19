package com.madtitan94.codengineapp.di

import android.content.Context
import com.madtitan94.codengineapp.model.dao.OrderProductDao
import com.madtitan94.codengineapp.model.dao.ProductDao
import com.madtitan94.codengineapp.model.dao.TransactionDao
import com.madtitan94.codengineapp.model.dao.UserDao
import com.madtitan94.codengineapp.model.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideOrderProductDao(database: AppDatabase): OrderProductDao {
        return database.orderProductDao()
    }

    @Provides
    fun provideTransactionDao(database: AppDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context,coroutineScope: CoroutineScope): AppDatabase {
        return AppDatabase.getDatabase(appContext,coroutineScope)
    }

}