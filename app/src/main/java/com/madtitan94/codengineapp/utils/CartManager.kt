package com.madtitan94.codengineapp.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.madtitan94.codengineapp.model.datamodel.CustomerDetails
import com.madtitan94.codengineapp.model.datamodel.OrderProduct
import com.madtitan94.codengineapp.model.datamodel.OrderTotalDetails
import com.madtitan94.codengineapp.model.datamodel.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

object CartManager {

    var customerDetails = CustomerDetails(firstName = "Guest",
    lastName = "",
    mobile = "",
    email = ""
    )
    var orderTotalDetails = OrderTotalDetails(
        subTotal = "0",
        totalTax = "0",
        total = "0"
    )

    fun clear(){
        CoroutineScope(Dispatchers.Default).launch{
        val p: MutableList<OrderProduct> = ArrayList()
        postToMLiveData(p)
            customerDetails = CustomerDetails(firstName = "Guest",
                lastName = "",
                mobile = "",
                email = ""
            )
             orderTotalDetails = OrderTotalDetails(
                subTotal = "0",
                totalTax = "0",
                total = "0"
            )
        }
    }


    fun GetFormmatedDateTime(): String{
    /*    val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted = current.format(formatter)*/
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        return currentDate
    }
    fun getProductList(): LiveData<MutableList<OrderProduct>> {
        return orderProducts
    }

    val orderProducts = MutableLiveData<MutableList<OrderProduct>>()

    suspend fun postToMLiveData(orders: MutableList<OrderProduct>){
        makeLog("<==> IN POST TO ML DATA")
        withContext(Dispatchers.Default){
                orderProducts.postValue(orders)
        }
    }

    suspend fun AddProductToCart(product: Product, quantity: Int):Boolean{


        makeLog("<==> IN ADD PRODUCT TO CART")

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
        }else{
            val newOrderP = OrderProduct.ModelMapper.from(product)
            newOrderP.quantity = quantity
            val p: MutableList<OrderProduct> = ArrayList()
            p.add(newOrderP)
            makeLog("Null so added 1st entry in else")
            postToMLiveData(p)


            return false

        }


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
        /*val df = DecimalFormat("#.##")
        val roundoff = df.format(perPiece)
*/
        return GetFormattedDouble(perPiece)
    }

    fun GetFormattedDouble(arg : Double): Double{
        return String.format("%.2f",arg).toDouble()
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
                makeLog("LAST REMOVAL SIZE IS "+orders.size)
                if (orders.size<=0) {
                    val p: MutableList<OrderProduct> = ArrayList()
                    makeLog("LAST REMOVAL TRY  IS "+p.size)
                    postToMLiveData(p)
                }else
                    postToMLiveData(orders)
                return true
            }
        }
        return false
    }
}

/*{

    private val productList = MutableLiveData<List<OrderProduct>>()

    fun getProductList(): LiveData<List<OrderProduct>> {
        return productList
    }

    fun InitiateCart() {
        productList.postValue(orderProducts)
    }


    val orderProducts : MutableList<OrderProduct> = ArrayList()

    fun getFlowOrderProducts():Flow<List<OrderProduct>>{
        return flowOf(orderProducts)
    }

    suspend fun AddProductToCart(product: Product, quantity: Int):Boolean{
        val oProduct: OrderProduct? = orderProducts.find { it.productId == product.id }
        val index = orderProducts.indexOf(oProduct)
            if (oProduct==null){
                val newOrderP = OrderProduct.ModelMapper.from(product)
                newOrderP.quantity = quantity
                orderProducts.add(newOrderP)
                makeLog("Null so added 1st entry")
            }else{
                oProduct.quantity = quantity
                oProduct.totalTax = calTax(product.price,quantity)
                orderProducts.set(index,oProduct)
                makeLog("Not Null so added at index $index and quantiy $quantity")
            }
        Log.e("Order Products are ","-->"+ orderProducts.toString())
            return true
    }

    fun makeLog(s:String){
        Log.e("CART MANAGER ","==> "+s)
    }

    fun Contains(product: Product):Boolean{
        if (orderProducts.size>0){
            val oProduct: OrderProduct? = orderProducts.find { it.productId == product.id }
            return oProduct!=null
        }
        return false;
    }

    fun getCartProductByProductId(id:Int):OrderProduct?{
        return orderProducts.find { it.productId == id }
    }

    fun RemoveProductFromCart(product: Product, quantity: Int):Boolean{
        if (orderProducts.size>0){
            val oProduct: OrderProduct? = orderProducts.find { it.productId == product.id }

            val index = orderProducts.indexOf(oProduct)

            if (oProduct==null){
               //nothing
                return false
            }else{
                if (quantity <=0) {
                    orderProducts.removeAt(index)
                }else{
                oProduct.quantity = quantity
                oProduct.totalTax = calTax(product.price,quantity)
                orderProducts.set(index,oProduct)
                }
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
        val oProduct: OrderProduct? = orderProducts.find { it.productId == product.productId }
        val index = orderProducts.indexOf(oProduct)
        if (oProduct==null){
            //val newOrderP = OrderProduct.ModelMapper.from(product)
            orderProducts.add(product)
            makeLog("Null so added 1st entry")
        }else{
            oProduct.quantity = quantity
            oProduct.totalTax = calTax(product.price,quantity)
            orderProducts.set(index,oProduct)
            makeLog("Not Null so added at index $index and quantiy $quantity")
        }
        Log.e("Order Products are ","-->"+ orderProducts.toString())
        return true
    }

    fun RemoveProductFromCart(product: OrderProduct, quantity: Int):Boolean{
        if (orderProducts.size>0){
            val oProduct: OrderProduct? = orderProducts.find { it.productId == product.productId }

            val index = orderProducts.indexOf(oProduct)

            if (oProduct==null){
                //nothing
                return false
            }else{
                if (quantity <=0) {
                    val op = orderProducts.removeAt(index)
                    if (op!=null){
                        makeLog("OP IS =="+op.toString())
                    }else{
                        makeLog("OP IS not null")
                    }
                }else{
                    oProduct.quantity = quantity
                    oProduct.totalTax = calTax(product.price,quantity)
                    orderProducts.set(index,oProduct)
                }
                return true
            }
        }
        return false
    }
}*/