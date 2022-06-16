package com.madtitan94.codengineapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.madtitan94.codengineapp.R
import com.madtitan94.codengineapp.model.datamodel.OrderProduct
import com.madtitan94.codengineapp.utils.CartManager
import com.madtitan94.codengineapp.utils.CartManager.makeLog
import com.madtitan94.codengineapp.view.activity.ViewCart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartAdapter(callback: OnQuantityModified) : ListAdapter<OrderProduct, CartAdapter.CartViewHolder>(CartProductsComparator()) /*,Filterable*/ {

    val callback2: OnQuantityModified = callback
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {

        return CartViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val current = getItem(position)

        holder.bind(current,callback2)
    }

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.name)
        private val prodPrice: TextView = itemView.findViewById(R.id.price)
        private val prodImage: ImageView = itemView.findViewById(R.id.image)

        private val quantityTv: TextView = itemView.findViewById(R.id.quantity)
        private val increment: Button = itemView.findViewById(R.id.increment)
        private val decrement: Button = itemView.findViewById(R.id.decrement)

        fun bind(product: OrderProduct?,callback : OnQuantityModified) {
            wordItemView.text = product?.name
            prodPrice.text = product?.price.toString()
            quantityTv.text = product?.quantity.toString()

            if (product?.image!=null && product.image.length>0)
                prodImage.setImageResource(product.image.toInt())
           /* itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("productId",product!!.productId)
                val intent = Intent(itemView.context, AddToCartActivity::class.java)
                intent.putExtras(bundle)
                itemView.context.startActivity(intent)
            }*/

            increment.setOnClickListener {
                var oldQuantity = quantityTv.text.toString().toInt()
                var newQuantity = oldQuantity+1

                 if (product!=null) {
                     makeLog("Product not null")
                     CoroutineScope(Default).launch {
                         makeLog("INSIDE COROUTINE")
                     val res = CartManager.AddCProductToCart(product,newQuantity)
                         withContext(Main){

                             makeLog("INSIDE WITHCONTENT MAIN INC "+res)
                         if (res)
                             quantityTv.text = newQuantity.toString()
                             if (callback!=null){
                                 callback.onQuantityModified()
                             }else
                                 makeLog("CALLBACK IS NULL")

                         }
                     }

                 }else{
                     makeLog("ELSE IN INCREMENT")
                 }
            }

            decrement.setOnClickListener {
                var oldQuantity = quantityTv.text.toString().toInt()
                if (oldQuantity > 0) {
                    var newQuantity = oldQuantity-1
                    if (product!=null) {
                        CoroutineScope(Default).launch {
                            val res = CartManager.RemoveProductFromCart(product, newQuantity)
                            withContext(Main) {
                                makeLog("RemoveCart returns " + res)
                                if (res)
                                    quantityTv.text = newQuantity.toString()
                            }
                        }
                    }
                }else{
                    makeLog("Quantity can not be decremented")
                }

                /*if (product!=null) {
                    CoroutineScope(Default).launch {
                        val res = CartManager.AddCProductToCart(product,newQuantity)
                        withContext(Main){
                            makAddtocart returns "+reseLog(")
                            if (res)
                                quantityTv.text = newQuantity.toString()
                        }
                    }
                }else{
                    makeLog("Increment not done")
                }*/
            }
        }

        companion object {
            fun create(parent: ViewGroup): CartViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.cart_item, parent, false)
                return CartViewHolder(view)
            }
        }
    }

    class CartProductsComparator : DiffUtil.ItemCallback<OrderProduct>() {
        override fun areItemsTheSame(oldItem: OrderProduct, newItem: OrderProduct): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: OrderProduct, newItem: OrderProduct): Boolean {
            return oldItem == newItem
        }
    }
}

interface OnQuantityModified{
    public fun onQuantityModified()
}