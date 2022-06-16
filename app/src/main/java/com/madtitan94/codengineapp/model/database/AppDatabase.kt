package com.madtitan94.codengineapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.madtitan94.codengineapp.model.dao.OrderProductDao
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.dao.ProductDao
import com.madtitan94.codengineapp.model.dao.TransactionDao
import com.madtitan94.codengineapp.model.dao.UserDao
import com.madtitan94.codengineapp.model.datamodel.OrderProduct
import com.madtitan94.codengineapp.model.datamodel.Transaction
import com.madtitan94.codengineapp.model.datamodel.User
import com.madtitan94.codengineapp.model.repository.TestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.chrono.HijrahChronology
import java.time.chrono.HijrahChronology.INSTANCE

@Database(entities = [Product::class,Transaction::class,OrderProduct::class, User::class],version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun orderProductDao(): OrderProductDao
    abstract fun transactionDao(): TransactionDao
    abstract fun userDao(): UserDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context,
        scope: CoroutineScope): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(ProductDatabaseCallback(scope))
                    .addCallback(UserDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }


    private class ProductDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.productDao())
                }
            }
        }

        suspend fun populateDatabase(productDao: ProductDao) {
            // Delete all content here.
            productDao.deleteAll()
            productDao.insertAll(TestRepository.Products)

        }
    }

    private class UserDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.userDao())
                }
            }
        }

        suspend fun populateDatabase(userDao: UserDao) {
            // Delete all content here.
            userDao.deleteAll()
            userDao.insertAll(TestRepository.Users)

        }
    }
}
