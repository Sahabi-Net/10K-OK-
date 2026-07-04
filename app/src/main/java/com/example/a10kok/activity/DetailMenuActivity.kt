package com.example.a10kok.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.example.a10kok.R
import com.example.a10kok.api.ApiClient
import com.example.a10kok.response.ProductDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMenuActivity : AppCompatActivity() {

    private lateinit var img: ImageView
    private lateinit var nama: TextView
    private lateinit var harga: TextView
    private lateinit var deskripsi: TextView
    private lateinit var tvRating: TextView
    private lateinit var btnKeranjang: AppCompatButton
    private lateinit var btnBack: ImageButton
    private lateinit var btnMinus: AppCompatButton
    private lateinit var btnPlus: AppCompatButton
    private lateinit var tvQty: TextView
    private lateinit var tvTotal: TextView

    private var idProduk: String = ""
    private var qty = 1
    private var unitPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)
        supportActionBar?.hide()

        img = findViewById(R.id.imgProduk)
        nama = findViewById(R.id.tvNamaMenu)
        harga = findViewById(R.id.tvHarga)
        deskripsi = findViewById(R.id.tvDeskripsi)
        tvRating = findViewById(R.id.tvRating)
        btnKeranjang = findViewById(R.id.btnTambahKeranjang)
        btnBack = findViewById(R.id.btnBack)
        btnMinus = findViewById(R.id.btnMinus)
        btnPlus = findViewById(R.id.btnPlus)
        tvQty = findViewById(R.id.tvQty)
        tvTotal = findViewById(R.id.tvTotal)

        idProduk = intent.getStringExtra("id_produk") ?: ""
        
        if (idProduk.isEmpty()) {
            Toast.makeText(this, "ID Produk tidak valid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        loadDetail()

        btnBack.setOnClickListener { finish() }

        btnMinus.setOnClickListener {
            if (qty > 1) {
                qty--
                updateQtyAndTotal()
            }
        }

        btnPlus.setOnClickListener {
            qty++
            updateQtyAndTotal()
        }

        btnKeranjang.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("id_produk", idProduk)
            intent.putExtra("jumlah", qty)
            startActivity(intent)
        }
    }

    private fun updateQtyAndTotal() {
        tvQty.text = qty.toString()
        val total = unitPrice * qty
        tvTotal.text = "Total: Rp ${formatPrice(total)}"
    }

    private fun formatPrice(price: Int): String {
        return String.format("%,d", price).replace(',', '.')
    }

    private fun loadDetail() {
        ApiClient.instance.getProduct(idProduk)
            .enqueue(object : Callback<ProductDetailResponse> {
                override fun onResponse(call: Call<ProductDetailResponse>, response: Response<ProductDetailResponse>) {
                    if (response.isSuccessful) {
                        val p = response.body()?.data
                        if (p != null) {
                            unitPrice = p.harga
                            nama.text = p.namaProduk
                            harga.text = "Rp ${formatPrice(p.harga)}"
                            deskripsi.text = p.deskripsi
                            tvRating.text = "★ ${p.rating}"
                            updateQtyAndTotal()

                            Glide.with(this@DetailMenuActivity)
                                .load("http://10.0.2.2/10kok_api/uploads/products/" + p.gambar)
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_background)
                                .into(img)
                        } else {
                            Toast.makeText(this@DetailMenuActivity, "Data produk tidak ditemukan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable) {
                    Toast.makeText(this@DetailMenuActivity, "Gagal memuat detail: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
