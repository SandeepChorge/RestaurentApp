package com.madtitan94.codengineapp.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.madtitan94.codengineapp.R
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.model.datamodel.User
import com.madtitan94.codengineapp.utils.ProductCategory
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

object TestRepository {

    val Products by lazy {
        listOf(
        Product(1, "Veg Burger", 10.0, R.drawable.burger1.toString(), ProductCategory.BURGER.category),
        Product(2,"Mac and Cheese Burger",10.0,R.drawable.burger2.toString(),ProductCategory.BURGER.category),
        Product(3, "Chicken Burger", 10.0, R.drawable.burger3.toString(), ProductCategory.BURGER.category),
        Product(4,"Double patty Burger",10.0,R.drawable.burger4.toString(),ProductCategory.BURGER.category),
        Product(5, "Coke", 10.0, R.drawable.coke.toString(), ProductCategory.BEVERAGES.category),
        Product(6,"Sprite",10.0,R.drawable.sprite.toString(),ProductCategory.BEVERAGES.category),
        Product(7, "Lime Soda", 10.0, R.drawable.limesoda.toString(), ProductCategory.BEVERAGES.category),
        Product(8,"Veg Sandwitch",10.0,R.drawable.sandwich1.toString(),ProductCategory.SANDWITCH.category),
        Product(9, "Masala Toast Sandwitch", 10.0, R.drawable.sandwich2.toString(), ProductCategory.SANDWITCH.category),
        Product(10,"Cheese Toast Sandwitch",10.0,R.drawable.sandwich1.toString(),ProductCategory.SANDWITCH.category),
        Product(11, "Normal Sandwitch", 10.0, R.drawable.sandwich3.toString(), ProductCategory.SANDWITCH.category),
        Product(12,"Vanilla Ice cream",10.0,R.drawable.ice1.toString(),ProductCategory.ICECREAM.category),
        Product(13, "chocolate Ice cream", 10.0, R.drawable.ice2.toString(), ProductCategory.ICECREAM.category),
        Product(14,"Butterscotch Ice cream",10.0,R.drawable.ice3.toString(),ProductCategory.ICECREAM.category))
    }

    val Users by lazy {
        listOf(
            User(1, "Manager", "manager",1234 , R.drawable.manager.toString(),true),
            User(2, "Server", "server",5678 , R.drawable.salesman.toString(),false))
    }

  }