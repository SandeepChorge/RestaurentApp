package com.madtitan94.codengineapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.madtitan94.codengineapp.R
import com.madtitan94.codengineapp.utils.SharedPrefs

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            lateinit var mIntent : Intent
            if(SharedPrefs.isLoggedIn(this)) {
                 mIntent = Intent(this@SplashActivity, MainActivity::class.java)
            }else{
                mIntent = Intent(this@SplashActivity, LoginActivity::class.java)
            }
            startActivity(mIntent)
            finish()
        }, 2000)
    }
}