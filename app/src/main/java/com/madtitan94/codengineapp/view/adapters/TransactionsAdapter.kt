package com.madtitan94.codengineapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.madtitan94.codengineapp.R
import com.madtitan94.codengineapp.model.datamodel.Transaction
import com.madtitan94.codengineapp.utils.CartManager.makeLog

class TransactionsAdapter : ListAdapter<Transaction, TransactionsAdapter.TransactionViewHolder>(TransactionComparator()) /*,Filterable*/ {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val time: TextView = itemView.findViewById(R.id.time)
        private val orderNo: TextView = itemView.findViewById(R.id.orderNo)


        fun bind(transaction: Transaction?) {
            makeLog("=>"+transaction?.toString())
            name.text = transaction?.firstName +" "+transaction?.lastName


            /*val output = SimpleDateFormat(transaction?.orderTime)
            val dateFormated = SimpleDateFormat("HH:mm").format(output)*/

            try {
                var date = transaction?.orderTime

                var res = date?.split(" ")




                /*makeLog("Time was "+transaction?.orderTime)
                makeLog("Time aft "+formatter.parse(date))*/
                time.text = res?.get(1) ?:"-"
            }catch (e:Exception){
                e.stackTrace
                makeLog("EXCEPTIOM IS "+e.message)
            }


            orderNo.text = transaction?.transactionId;


         /*   itemView.setOnClickListener {

                val bundle = Bundle()
                bundle.putInt("productId",product!!.id)
                val intent = Intent(itemView.context, AddToCartActivity::class.java)
                intent.putExtras(bundle)
                itemView.context.startActivity(intent)
            }*/
        }

        companion object {
            fun create(parent: ViewGroup): TransactionViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.transaction_item, parent, false)
                return TransactionViewHolder(view)
            }
        }
    }

    class TransactionComparator : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }
    }
}