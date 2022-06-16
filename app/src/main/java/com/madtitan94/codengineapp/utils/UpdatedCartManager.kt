package com.madtitan94.codengineapp.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.madtitan94.codengineapp.model.datamodel.OrderProduct
import com.madtitan94.codengineapp.model.datamodel.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class UpdatedCartManager {

    fun getProductList(): LiveData<MutableList<OrderProduct>> {
        return orderProducts
    }
    val orderProducts = MutableLiveData<MutableList<OrderProduct>>()

    suspend fun postToMLiveData(orders: MutableList<OrderProduct>){
        withContext(Dispatchers.Default){
            if (orders!=null) {
                orderProducts.postValue(orders)
            }
        }
    }

    suspend fun AddProductToCart(product: Product, quantity: Int):Boolean{

        var orders = orderProducts.value

        if(orders!=null) {
            val oProduct: OrderProduct? = orders.find { it.productId == product.id }
            val index = orders.indexOf(oProduct)
            if (oProduct == null) {
                val newOrderP = OrderProduct.ModelMapper.from(product)
                newOrderP.quantity = quantity

                orders.add(newOrderP)
                makeLog("Null so added 1st entry")
            } else {
                oProduct.quantity = quantity
                oProduct.totalTax = calTax(product.price, quantity)
                orders.set(index, oProduct)
                makeLog("Not Null so added at index $index and quantiy $quantity")
            }
            Log.e("Order Products are ", "-->" + orders.toString())
            postToMLiveData(orders)
            return true
        }else
            return false

    }

    fun makeLog(s:String){
        Log.e("CART MANAGER ","==> "+s)
    }

    fun Contains(product: Product):Boolean{
        var orders = orderProducts.value
        if (orders!=null){
        if (orders.size>0){
            val oProduct: OrderProduct? = orders.find { it.productId == product.id }
            return oProduct!=null
        }
        }
        return false;
    }

    fun getCartProductByProductId(id:Int): OrderProduct?{
        var orders = orderProducts.value
        return orders?.find { it.productId == id }
    }

    suspend fun RemoveProductFromCart(product: Product, quantity: Int):Boolean{
        var orders = orderProducts.value
        if (orders!=null && orders.size>0){
            val oProduct: OrderProduct? = orders.find { it.productId == product.id }

            val index = orders.indexOf(oProduct)

            if (oProduct==null){
                //nothing
                return false
            }else{
                if (quantity <=0) {
                    orders.removeAt(index)
                }else{
                    oProduct.quantity = quantity
                    oProduct.totalTax = calTax(product.price,quantity)
                    orders.set(index,oProduct)
                }

                postToMLiveData(orders)
                return true
            }
        }
        return false
    }

    fun calTax(price: Double , quantity: Int) : Double{

        val perPiece = ((price/100)*8.25);

        return perPiece*quantity
    }

    suspend fun AddCProductToCart(product: OrderProduct, quantity: Int):Boolean{
        makeLog("INSIDE ADD C PRODUCT TO CART")
        var orders = orderProducts.value
        if (orders!=null) {
            val oProduct: OrderProduct? = orders.find { it.productId == product.productId }
            val index = orders.indexOf(oProduct)
            if (oProduct == null) {
                orders.add(product)
                makeLog("Null so added 1st entry")
            } else {
                oProduct.quantity = quantity
                oProduct.totalTax = calTax(product.price, quantity)
                orders.set(index, oProduct)
                makeLog("Not Null so added at index $index and quantiy $quantity")
            }
            postToMLiveData(orders)
            Log.e("Order Products are ", "-->" + orderProducts.toString())
            return true
        }else
            return false

    }

    suspend fun RemoveProductFromCart(product: OrderProduct, quantity: Int):Boolean{
        var orders = orderProducts.value

        if (orders!=null && orders.size>0){
            val oProduct: OrderProduct? = orders.find { it.productId == product.productId }

            val index = orders.indexOf(oProduct)

            if (oProduct==null){
                //nothing
                return false
            }else{
                if (quantity <=0) {
                    val op = orders.removeAt(index)
                    if (op!=null){
                        makeLog("OP IS =="+op.toString())
                    }else{
                        makeLog("OP IS not null")
                    }
                }else{
                    oProduct.quantity = quantity
                    oProduct.totalTax = calTax(product.price,quantity)
                    orders.set(index,oProduct)
                }
                postToMLiveData(orders)
                return true
            }
        }
        return false
    }
}