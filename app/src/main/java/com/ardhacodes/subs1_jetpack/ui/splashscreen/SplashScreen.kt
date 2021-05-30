package com.ardhacodes.subs1_jetpack.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ardhacodes.subs1_jetpack.R
import com.ardhacodes.subs1_jetpack.ui.main.MainActivity

class SplashScreen : AppCompatActivity() {
    lateinit var handler: Handler

    val second:Long = 7500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        handler = Handler()
        handler.postDelayed({
            val intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
            finish()

        }, second)
    }
}