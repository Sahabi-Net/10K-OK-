package com.example.a10kok.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.a10kok.R
import com.example.a10kok.api.ApiClient
import com.example.a10kok.response.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: AppCompatButton
    private lateinit var tvLoginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        etNama            = findViewById(R.id.etNama)
        etEmail           = findViewById(R.id.etEmail)
        etPassword        = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister       = findViewById(R.id.btnRegister)
        tvLoginLink       = findViewById(R.id.tvLoginLink)

        tvLoginLink.setOnClickListener {
            finish()
        }

        btnRegister.setOnClickListener {
            prosesRegister()
        }
    }

    private fun prosesRegister() {
        val nama    = etNama.text.toString().trim()
        val email   = etEmail.text.toString().trim()
        val pass    = etPassword.text.toString().trim()
        val confirm = etConfirmPassword.text.toString().trim()

        if (nama.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirm) {
            Toast.makeText(this, "Password dan konfirmasi tidak cocok", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass.length < 6) {
            Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show()
            return
        }

        btnRegister.isEnabled = false
        btnRegister.text      = "Mendaftarkan..."

        ApiClient.instance.register(nama, email, pass)
            .enqueue(object : Callback<BaseResponse> {

                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    btnRegister.isEnabled = true
                    btnRegister.text      = "DAFTAR SEKARANG"

                    val body = response.body()
                    if (response.isSuccessful && body?.success == true) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Registrasi berhasil! Silakan login.",
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            body?.message ?: "Registrasi gagal",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse>,
                    t: Throwable
                ) {
                    t.printStackTrace()

                    Toast.makeText(
                        this@RegisterActivity,
                        t.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}