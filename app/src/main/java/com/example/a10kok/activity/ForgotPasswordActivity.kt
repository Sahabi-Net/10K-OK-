package com.example.a10kok.activity

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

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var etEmailForgot: EditText
    private lateinit var btnReset: AppCompatButton
    private lateinit var tvBackToLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        supportActionBar?.hide()

        etEmailForgot = findViewById(R.id.etEmailForgot)
        btnReset      = findViewById(R.id.btnReset)
        tvBackToLogin = findViewById(R.id.tvBackToLogin)

        tvBackToLogin.setOnClickListener {
            finish()
        }

        btnReset.setOnClickListener {
            prosesReset()
        }
    }

    private fun prosesReset() {
        val email = etEmailForgot.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(this, "Email harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Format email tidak valid", Toast.LENGTH_SHORT).show()
            return
        }

        btnReset.isEnabled = false
        btnReset.text      = "Mengirim..."

        ApiClient.instance.forgotPassword(email)
            .enqueue(object : Callback<BaseResponse> {

                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    btnReset.isEnabled = true
                    btnReset.text      = "RESET PASSWORD"

                    val body = response.body()
                    if (response.isSuccessful && body?.success == true) {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            body.message ?: "Tautan reset telah dikirim ke email Anda",
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            body?.message ?: "Email tidak ditemukan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    btnReset.isEnabled = true
                    btnReset.text      = "RESET PASSWORD"
                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        "Koneksi bermasalah: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}