package com.example.a10kok.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a10kok.R
import com.example.a10kok.api.ApiClient
import com.example.a10kok.response.LoginResponse
import com.example.a10kok.session.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etPassword)
        login = findViewById(R.id.btnLogin)

        val tvForgot = findViewById<android.widget.TextView>(R.id.tvForgot)
        val tvRegister = findViewById<android.widget.TextView>(R.id.tvRegisterLink)

        login.setOnClickListener {
            prosesLogin()
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        tvForgot.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    private fun prosesLogin() {
        val txtEmail = email.text.toString().trim()
        val txtPassword = password.text.toString().trim()

        if (txtEmail.isEmpty() || txtPassword.isEmpty()) {
            Toast.makeText(this, "Email dan Password harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        ApiClient.instance.login(txtEmail, txtPassword)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        val user = response.body()!!.data!!

                        // Simpan login
                        SessionManager(this@LoginActivity).saveLogin(
                            user.id_user,
                            user.nama,
                            user.email,
                            user.role
                        )

                        Toast.makeText(this@LoginActivity, "Selamat Datang, ${user.nama}", Toast.LENGTH_SHORT).show()

                        val intent = when (user.role.lowercase()) {
                            "admin" -> Intent(this@LoginActivity, AdminActivity::class.java)
                            "mitra" -> Intent(this@LoginActivity, MitraDashboardActivity::class.java)
                            else -> Intent(this@LoginActivity, HomeActivity::class.java)
                        }
                        
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, response.body()?.message ?: "Login gagal", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Koneksi Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
    }
}
