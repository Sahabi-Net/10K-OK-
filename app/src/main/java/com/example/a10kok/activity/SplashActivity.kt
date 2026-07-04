package com.example.a10kok.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.a10kok.R
import com.example.a10kok.session.SessionManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val session = SessionManager(this)
            
            if (session.isLogin()) {
                val role = session.getRole().lowercase()
                val intent = when (role) {
                    "admin" -> Intent(this, AdminActivity::class.java)
                    "mitra" -> Intent(this, MitraDashboardActivity::class.java)
                    else -> Intent(this, HomeActivity::class.java)
                }
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 2000)
    }
}
