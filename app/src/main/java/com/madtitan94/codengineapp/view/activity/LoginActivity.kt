package com.madtitan94.codengineapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.madtitan94.codengineapp.R
import com.madtitan94.codengineapp.databinding.ActivityLogin2Binding
import com.madtitan94.codengineapp.databinding.ActivityMainBinding
import com.madtitan94.codengineapp.utils.CartManager.makeLog
import com.madtitan94.codengineapp.utils.CodeEngineApplication
import com.madtitan94.codengineapp.utils.SharedPrefs
import com.madtitan94.codengineapp.viewmodel.LandingViewModel
import com.madtitan94.codengineapp.viewmodel.LandingViewModelFactory
import com.madtitan94.codengineapp.viewmodel.LoginViewModel
import com.madtitan94.codengineapp.viewmodel.LoginViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLogin2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login2)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewmodel : LoginViewModel by viewModels {
            LoginViewModelFactory((this?.application as CodeEngineApplication).userRepository)
        }

        viewmodel.MatchingUSer().observe(this, Observer {
            if (!it.isEmpty()) {
                    makeLog("IN RES TRUE")
                    CoroutineScope(Dispatchers.Main).launch{
                        makeLog("SHOULD CALL NEW ACTIVITY")
                        SharedPrefs.setLoggedIn(this@LoginActivity,true)
                        SharedPrefs.setAdmin(this@LoginActivity,true)
                        SharedPrefs.setUsername(this@LoginActivity,it.toList().get(0).username)

                        val mIntent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(mIntent)
                        finish()
                    }
                }
                else{
                    makeLog("Please enter valid credentials")
                    CoroutineScope(Dispatchers.Main).launch {
                        makeToast("Please enter valid credentials")
                    }
                }
        })



        binding.login.setOnClickListener {
            if (binding.username.text.isEmpty() || binding.password.text.isEmpty()){
                makeToast("User name and password can not be empty")
            }else if(binding.password.text.length<4){
                makeToast("Password should be 4 characters")
            }else{
                makeLog("IN ELSE CONDITION")

                viewmodel.login(binding.username.text.toString(),binding.password.text.toString())

            }
        }
    }
    fun makeToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}