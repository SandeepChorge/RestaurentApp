package com.madtitan94.codengineapp.view.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.madtitan94.codengineapp.R
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.view.activity.AddToCartActivity


class ProductsAdapter : ListAdapter<Product, ProductsAdapter.ProductViewHolder>(ProductsComparator()) /*,Filterable*/ {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.name)
        private val prodPrice: TextView = itemView.findViewById(R.id.price)
        private val prodImage: ImageView = itemView.findViewById(R.id.image)

        fun bind(product: Product?) {
            wordItemView.text = product?.name
            prodPrice.text = product?.price.toString()
            //prodImage.setImageURI(product?.image)
            //Glide.with(itemView).load(product?.image).into(prodImage);

            if (product?.image!=null && product.image.length>0)
                prodImage.setImageResource(product.image.toInt())
            itemView.setOnClickListener {

                val bundle = Bundle()
                bundle.putInt("productId",product!!.id)
                val intent = Intent(itemView.context,AddToCartActivity::class.java)
                intent.putExtras(bundle)
                itemView.context.startActivity(intent)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ProductViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_item, parent, false)
                return ProductViewHolder(view)
            }
        }
    }

    class ProductsComparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }
    /*override fun getFilter(): Filter {
        return
    }*/
}