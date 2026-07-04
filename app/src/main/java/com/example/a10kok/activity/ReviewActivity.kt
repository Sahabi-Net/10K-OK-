package com.example.a10kok.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.a10kok.R
import com.example.a10kok.api.ApiClient
import com.example.a10kok.response.BaseResponse
import com.example.a10kok.session.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.RatingBar
import android.widget.EditText

class ReviewActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var etKomentar: EditText
    private lateinit var btnKirimReview: AppCompatButton
    private lateinit var btnBack: android.widget.ImageButton
    private lateinit var tvRincianReview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        supportActionBar?.hide()

        ratingBar = findViewById(R.id.ratingBar)
        etKomentar = findViewById(R.id.etKomentar)
        btnKirimReview = findViewById(R.id.btnKirimReview)
        btnBack = findViewById(R.id.btnBack)
        tvRincianReview = findViewById(R.id.tvRincianReview)

        val idOrder = intent.getStringExtra("id_order") ?: ""
        val idProduk = intent.getStringExtra("id_produk") ?: ""

        btnBack.setOnClickListener { finish() }

        if (idProduk.isNotEmpty()) {
            ApiClient.instance.getProduct(idProduk)
                .enqueue(object : Callback<com.example.a10kok.response.ProductDetailResponse> {
                    override fun onResponse(
                        call: Call<com.example.a10kok.response.ProductDetailResponse>,
                        response: Response<com.example.a10kok.response.ProductDetailResponse>
                    ) {
                        if (response.isSuccessful && response.body()?.success == true) {
                            val p = response.body()!!.data
                            tvRincianReview.text = "• ${p.namaProduk}\n  Rp ${p.harga}"
                        }
                    }

                    override fun onFailure(
                        call: Call<com.example.a10kok.response.ProductDetailResponse>,
                        t: Throwable
                    ) {
                        tvRincianReview.text = "• Produk tidak dapat dimuat"
                    }
                })
        } else {
            tvRincianReview.text = "• Pesanan #$idOrder"
        }

        btnKirimReview.setOnClickListener {
            val idUser = SessionManager(this).getIdUser()
            val rating = ratingBar.rating.toInt()
            val komentar = etKomentar.text.toString()

            if (rating == 0) {
                Toast.makeText(this, "Pilih rating bintang terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ApiClient.instance.insertReview(
                idOrder,
                idProduk,
                idUser,
                rating,
                komentar
            ).enqueue(object : Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        startActivity(Intent(this@ReviewActivity, ReviewSuccessActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this@ReviewActivity,
                            response.body()?.message ?: "Gagal mengirim ulasan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        this@ReviewActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}