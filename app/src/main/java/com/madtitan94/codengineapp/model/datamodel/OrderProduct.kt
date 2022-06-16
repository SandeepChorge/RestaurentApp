package com.madtitan94.codengineapp.model.datamodel

import androidx.room.Entity
import androidx.room.FtsOptions
import androidx.room.PrimaryKey
import com.madtitan94.codengineapp.utils.CartManager
import java.net.IDN
import java.util.concurrent.TimeUnit

@Entity
data class OrderProduct(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var transactionId: Int,
    val productId: Int,
    val name: String,
    val price: Double,
    val image: String,
    val category: String,
    var quantity: Int,
    var totalTax: Double
){
    object ModelMapper {
        fun from(form: Product) =
            OrderProduct(productId = form.id,
            name = form.name,
            price = form.price,
            image = form.image,
            category = form.category,
                quantity = 1,
                totalTax = 0.0,//CartManager.calTax(form.price,1),
                transactionId = 0,
                id = 0
            )
    }


}