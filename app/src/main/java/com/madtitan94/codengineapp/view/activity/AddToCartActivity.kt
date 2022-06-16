package com.madtitan94.codengineapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.madtitan94.codengineapp.R
import com.madtitan94.codengineapp.databinding.ActivityAddToCartBinding
import com.madtitan94.codengineapp.databinding.ActivityMainBinding
import com.madtitan94.codengineapp.model.datamodel.Product
import com.madtitan94.codengineapp.utils.CartManager
import com.madtitan94.codengineapp.utils.CartManager.makeLog
import com.madtitan94.codengineapp.utils.CodeEngineApplication
import com.madtitan94.codengineapp.utils.SharedPrefs
import com.madtitan94.codengineapp.viewmodel.AddToCartActivityViewModel
import com.madtitan94.codengineapp.viewmodel.AddToCartViewModelFactory
import com.madtitan94.codengineapp.viewmodel.LandingViewModel
import com.madtitan94.codengineapp.viewmodel.LandingViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class AddToCartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddToCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_add_to_cart)

        binding = ActivityAddToCartBinding.inflate(layoutInflater)
        binding.setLifecycleOwner(this)
        setContentView(binding.root)

        val addtoCartViewModel : AddToCartActivityViewModel by viewModels {
            AddToCartViewModelFactory((this.application as CodeEngineApplication).prodRepository)
        }
        val bundle = intent.extras
        if (bundle!=null ){
            val productId = bundle.getInt("productId",0)
            Log.e("Product id is ",""+productId)
            addtoCartViewModel.getProductDetails(productId)

            val op = CartManager.getCartProductByProductId(productId)

            if(op!=null) {
                addtoCartViewModel.updateQuantity(op.quantity)
                //binding.quantity.text = op.quantity.toString()

            }else {
                addtoCartViewModel.updateQuantity(1)
                //binding.quantity.text = "1"
            }

        }else{
            Log.e("Product id is ","NONE")
        }

        addtoCartViewModel.getProductLiveData().observe(this, Observer{
                product ->
            binding.image.setImageResource(product.image.toInt())
            binding.name.text = product.name
        })

        addtoCartViewModel.prodQuantity.observe(this, Observer{
                product ->
            binding.quantity.text = product.toString()
        })

        binding.addItem.setOnClickListener {

            if (SharedPrefs.isAdmin(this)){


            var oldQuantity = binding.quantity.text.toString().toInt()
//            var newQuantity = oldQuantity+1
            makeLog("<==> ADD ITEM ONCLICK")
            if (oldQuantity<=0){
                makeLog("<==> OLD QUANTITY LESS THAN 0")
                val prod = addtoCartViewModel.getProductLiveData().value
                if (prod != null && CartManager.Contains(prod)) {
                    makeLog("<==> PROD NOT NULL AND CONTAIN")
                    CoroutineScope(Default).launch {
                        val res = CartManager.RemoveProductFromCart(prod, oldQuantity)
                        withContext(Main) {
                            if (res)
                                binding.quantity.text = oldQuantity.toString()
                        }
                    }
                }else {
                    makeLog("<==> PROD NULL OR NOT CONTAIN")
                    makeToast("Quantity cant be zero")
                }
            }else{
                makeLog("<==> IN ELSE CONDITION")
            val prod =addtoCartViewModel.getProductLiveData().value
            if (prod!=null) {
                makeLog("<==> IN NOT NULL")
                CoroutineScope(Default).launch {
                    val res = CartManager.AddProductToCart(prod,oldQuantity)
                    withContext(Main){
                        makeToast("Product added to Cart")
                        if (res)
                            binding.quantity.text = oldQuantity.toString()
                    }
                    }
            }

            }
            }else{
                Toast.makeText(this@AddToCartActivity,"Only Manager is allowed to take orders",Toast.LENGTH_SHORT).show();
            }
        }

        binding.viewCart.setOnClickListener {
            if (SharedPrefs.isAdmin(this)) {
                //if (CartManager.orderProducts.value!!.size>0) {
                    startActivity(Intent(this, ViewCart::class.java))
                    finish()
                /*}else{
                    Toast.makeText(this@AddToCartActivity,"Please add Products in cart first",Toast.LENGTH_SHORT).show();
                }*/
            }else{
                Toast.makeText(this@AddToCartActivity,"Only Manager is allowed to take orders",Toast.LENGTH_SHORT).show();
            }
        }

        binding.increment.setOnClickListener {
            var oldQuantity = binding.quantity.text.toString().toInt()
            var newQuantity = oldQuantity+1
            binding.quantity.text = newQuantity.toString()

           /* val prod =addtoCartViewModel.getProductLiveData().value
            if (prod!=null) {
                CoroutineScope(Default).launch {
                val res = CartManager.AddProductToCart(prod,newQuantity)
                    withContext(Main){
                        makeToast("Addtocart returns "+res)
                    if (res)
                    binding.quantity.text = newQuantity.toString()
                    }
                }
            }else{
                makeToast("Increment not done")
            }*/
        }

        binding.decrement.setOnClickListener {
            var oldQuantity = binding.quantity.text.toString().toInt()
            if (oldQuantity > 0) {
                var newQuantity = oldQuantity-1
                binding.quantity.text = newQuantity.toString()

                /*val prod = addtoCartViewModel.getProductLiveData().value
                if (prod != null) {
                    val res = CartManager.RemoveProductFromCart(prod, newQuantity)
                    if (res)
                        binding.quantity.text = newQuantity.toString()
                }*/
            }else{
                Toast.makeText(this,"Quantity can not be decremented",Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun makeToast(msg:String)
    {Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()}
}