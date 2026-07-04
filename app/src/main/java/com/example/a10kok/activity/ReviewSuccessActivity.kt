package com.example.a10kok.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.a10kok.R

class ReviewSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_success)

        supportActionBar?.hide()

        val btnKembaliHome = findViewById<AppCompatButton>(R.id.btnKembaliHome)
        val btnLihatRiwayat = findViewById<AppCompatButton>(R.id.btnLihatRiwayat)

        btnKembaliHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        btnLihatRiwayat.setOnClickListener {
            val intent = Intent(this, TrackingActivity::class.java)
            intent.putExtra("id_order", "")
            startActivity(intent)
        }
    }
}