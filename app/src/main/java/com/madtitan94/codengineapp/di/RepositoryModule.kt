package com.madtitan94.codengineapp.di

import com.madtitan94.codengineapp.data.data_source.OrderProductDao
import com.madtitan94.codengineapp.data.data_source.ProductDao
import com.madtitan94.codengineapp.data.data_source.TransactionDao
import com.madtitan94.codengineapp.data.data_source.UserDao
import com.madtitan94.codengineapp.data.repository.OrderProductRepositoryImpl
import com.madtitan94.codengineapp.data.repository.ProductRepositoryImpl
import com.madtitan94.codengineapp.data.repository.TransactionRepositoryImpl
import com.madtitan94.codengineapp.data.repository.UserRepositoryImpl
import com.madtitan94.codengineapp.domain.repository.OrderProductRepository
import com.madtitan94.codengineapp.domain.repository.ProductRepository
import com.madtitan94.codengineapp.domain.repository.TransactionRepository
import com.madtitan94.codengineapp.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideProductRepository(productDao: ProductDao): ProductRepository {
        return ProductRepositoryImpl(productDao)
    }

    @Provides
    fun provideOrderProductRepository(orderProductDao: OrderProductDao): OrderProductRepository {
        return OrderProductRepositoryImpl(orderProductDao)
    }

    @Provides
    fun provideTransactionRepository(transactionDao: TransactionDao): TransactionRepository {
        return TransactionRepositoryImpl(transactionDao)
    }

    @Provides
    fun provideUserRepository(userDao: UserDao):UserRepository{
        return UserRepositoryImpl(userDao)
    }
}
