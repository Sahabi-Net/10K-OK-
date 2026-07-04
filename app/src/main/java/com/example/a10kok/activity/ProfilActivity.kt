package com.example.a10kok.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.a10kok.R
import com.example.a10kok.session.SessionManager

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()

        val session = SessionManager(this)
        val tvProfileNama  = findViewById<TextView>(R.id.tvProfileNama)
        val tvProfileEmail = findViewById<TextView>(R.id.tvProfileEmail)
        val tvProfileRole  = findViewById<TextView>(R.id.tvProfileRole)
        val tvInfoNama     = findViewById<TextView>(R.id.tvInfoNama)
        val tvInfoEmail    = findViewById<TextView>(R.id.tvInfoEmail)
        val btnLogout      = findViewById<AppCompatButton>(R.id.btnLogout)

        tvProfileNama.text  = session.getNama().ifEmpty { "Pengguna" }
        tvProfileEmail.text = session.getEmail().ifEmpty { "-" }
        tvProfileRole.text  = session.getRole().ifEmpty { "user" }
        tvInfoNama.text     = session.getNama().ifEmpty { "-" }
        tvInfoEmail.text    = session.getEmail().ifEmpty { "-" }

        btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Keluar")
                .setMessage("Apakah Anda yakin ingin keluar dari akun ini?")
                .setPositiveButton("Ya, Keluar") { _, _ ->
                    session.logout()
                    Toast.makeText(this, "Berhasil Logout", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }

        findViewById<android.widget.LinearLayout>(R.id.navHome).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
        findViewById<android.widget.LinearLayout>(R.id.navCart).setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("id_produk", "")
            startActivity(intent)
        }
        findViewById<android.widget.LinearLayout>(R.id.navHistory).setOnClickListener {
            startActivity(Intent(this, TrackingActivity::class.java))
        }
    }
}
