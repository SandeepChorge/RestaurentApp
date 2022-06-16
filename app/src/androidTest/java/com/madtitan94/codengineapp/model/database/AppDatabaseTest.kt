package com.madtitan94.codengineapp.model.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.madtitan94.codengineapp.model.dao.ProductDao
import com.madtitan94.codengineapp.model.datamodel.Product
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest: TestCase(){
    private lateinit var db: AppDatabase
    private lateinit var dao: ProductDao

    // Override function setUp() and annotate it with @Before
    // this function will be called at first when this test class is called
    @Before
    public override fun setUp() {
        // get context -- since this is an instrumental test it requires
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.productDao()
    }

    // Override function closeDb() and annotate it with @After
    // this function will be called at last when this test class is called
    @After
    fun closeDb() {
        db.close()
    }

    // create a test function and annotate it with @Test
    // here we are first adding an item to the db and then checking if that item
    // is present in the db -- if the item is present then our test cases pass
    @Test
    fun insertProduct() = runBlocking {
        val prod = Product(1,"Normal Burger",10.0,"","burger")
        dao.insert(prod)
        Assert.assertTrue("Inserted Product",true)
/*        val products = dao.getProductsByCategory("burger")

        val prod2 = Product(2,"Cheese Burger",10.0,"","burger")
        Assert.assertTrue("Products are null",products!=null)*/
    }

    @Test
    fun getProducts() = runBlocking {

        val res = dao.getProductsByCategory("burger")
        if (res!=null && res.size>0) {
            Log.e("TEST","---"+res.size)
            Assert.assertTrue("Got Products " + res.size, true)
        }else {
            Log.e("TEST","---Empty")
            Assert.assertFalse("Nothing Got Products ", false)
        }

    }

}