package com.madtitan94.codengineapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.madtitan94.codengineapp.R
import com.madtitan94.codengineapp.databinding.ActivityViewCartBinding
import com.madtitan94.codengineapp.model.datamodel.CustomerDetails
import com.madtitan94.codengineapp.model.datamodel.OrderTotalDetails
import com.madtitan94.codengineapp.utils.CartManager
import com.madtitan94.codengineapp.utils.CartManager.makeLog
import com.madtitan94.codengineapp.utils.CodeEngineApplication
import com.madtitan94.codengineapp.view.adapters.CartAdapter
import com.madtitan94.codengineapp.view.adapters.OnQuantityModified
import com.madtitan94.codengineapp.view.adapters.ProductsAdapter
import com.madtitan94.codengineapp.viewmodel.AddToCartActivityViewModel
import com.madtitan94.codengineapp.viewmodel.AddToCartViewModelFactory
import com.madtitan94.codengineapp.viewmodel.ViewCartViewModel
import com.madtitan94.codengineapp.viewmodel.ViewCartViewModelFactory
import kotlinx.coroutines.*

class ViewCart : AppCompatActivity(), OnQuantityModified {
    private lateinit var  binding : ActivityViewCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_view_cart)
        binding = ActivityViewCartBinding.inflate(layoutInflater)
        binding.setLifecycleOwner(this)
        setContentView(binding.root)

        val viewmodel : ViewCartViewModel by viewModels {
            ViewCartViewModelFactory((this.application as CodeEngineApplication).prodRepository,
                (this.application as CodeEngineApplication).orderProdRepository,
                (this.application as CodeEngineApplication).transactionRepository)
        }

        //viewmodel.getProductList().observe(this, Observer {  })


        val listener= object : OnQuantityModified {
            override fun onQuantityModified() {
                CartManager.makeLog("CALLBACK IS CALLED")
            }
        }

        val recyclerView = binding.recyclerview
        val adapter = CartAdapter(listener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        /*viewmodel.getProductList().observe(this, Observer { products ->
            products?.let { adapter.submitList(it)

                var total: Double = 0.0
                products.forEach { orderProduct ->
                        val totalPrice = orderProduct.quantity * orderProduct.price
                        total =total + totalPrice
                        Log.e("Product --> $orderProduct.name","==>"+totalPrice)
                    Log.e("TOTAL IS ",""+total)
                }
                }
        })*/
        //CartManager.InitiateCart()
        CartManager.getProductList().observe(this, Observer { products ->
            products?.let { adapter.submitList(it)
                adapter.notifyDataSetChanged()
                Log.e("SIZE IS ","==>"+products.size)
                    if (products.size==0)finish()

                CoroutineScope(Dispatchers.Default).launch{
                    var total: Double = 0.0
                    var totalTax: Double = 0.0
                    products.forEach { orderProduct ->
                        val totalPrice = orderProduct.quantity * orderProduct.price
                        total =total + totalPrice

                        /*makeLog("${orderProduct.name} TAX BEFORE IS ==>"+orderProduct.totalTax)
                        var Tax = orderProduct.totalTax * orderProduct.quantity
                        makeLog("${orderProduct.name} TAX IS ==>"+Tax)
                        totalTax = totalTax + Tax*/

                        /*totalTax = CartManager.GetFormattedDouble(totalTax)
                        total = CartManager.GetFormattedDouble(total)
                        makeLog("${orderProduct.name} Total TAX IS ==>"+totalTax)*/
                    }
                    CoroutineScope(Dispatchers.Main).launch {

                        totalTax = (total/100)*8.25
                        makeLog("TOTAL TAX Before IS ==>"+totalTax)
                        totalTax = CartManager.GetFormattedDouble(totalTax)
                        makeLog("TOTAL TAX Aftr IS ==>"+totalTax)
                        binding.SubTotalValue.text =  total.toString();
                        binding.TaxValue.text =  totalTax.toString();
                        binding.totalCost.text = (CartManager.GetFormattedDouble((totalTax+total))).toString()
                        CartManager.orderTotalDetails = OrderTotalDetails(subTotal = total.toString(),
                        totalTax = totalTax.toString(),
                        total = (CartManager.GetFormattedDouble((totalTax+total))).toString())
                    }
                }


            }
        })

        binding.Goto.setOnClickListener {
            val builder = AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog)
                .setTitle("Customer Details")
                .setCancelable(false)
                .create()
            val view = layoutInflater.inflate(R.layout.goto_alert,null)
            val  submit = view.findViewById<Button>(R.id.submit)
            val  cancel = view.findViewById<Button>(R.id.cancel)

            val  first = view.findViewById<EditText>(R.id.firstName)
            val  last = view.findViewById<EditText>(R.id.lastName)
            val  mobile = view.findViewById<EditText>(R.id.mobileNumber)
            val  email = view.findViewById<EditText>(R.id.emailId)
            builder.setView(view)
            cancel.setOnClickListener {
                builder.dismiss()
            }

            submit.setOnClickListener {

                if (first.text.isEmpty())
                    first.setError("Invalid First Name")
                if (last.text.isEmpty())
                    last.setError("Invalid Last Name")
                if (mobile.text.isEmpty() || mobile.text.length<10)
                    mobile.setError("Invalid Mobile Number")
                if (email.text.isEmpty())
                    email.setError("Invalid Email Address")
                else {
                    CartManager.customerDetails = CustomerDetails(first.text.toString(),last.text.toString(),mobile.text.toString(),email.text.toString())

                    CoroutineScope(Dispatchers.Main).launch{
                        binding.customerName.text = first.text.toString()+" "+ last.text.toString()
                    }
                    builder.dismiss()

                }
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }


        binding.confirm.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                if(CartManager.orderProducts.value!!.size>0) {
                    viewmodel.confirmOrder()
                    withContext(Dispatchers.Main) {
                        makeLog("Done with Insertion")
                        finish()
                    }
                }else{
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@ViewCart,"Product needs to be added in cart",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onQuantityModified() {

    }
}