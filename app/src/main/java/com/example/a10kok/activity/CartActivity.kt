package com.example.a10kok.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
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

class CartActivity : AppCompatActivity() {

    private lateinit var tvMenu: TextView
    private lateinit var tvHarga: TextView
    private lateinit var tvJumlah: TextView
    private lateinit var tvSubtotal: TextView
    private lateinit var tvDelivery: TextView
    private lateinit var tvGrandTotal: TextView
    private lateinit var btnCheckout: AppCompatButton
    private lateinit var btnBack: ImageButton
    private lateinit var imgCartItem: ImageView
    private lateinit var tvItemCount: TextView
    
    private lateinit var optionDelivery: LinearLayout
    private lateinit var optionPickup: LinearLayout
    private lateinit var containerAlamat: LinearLayout
    private lateinit var etAlamatPengiriman: EditText

    private var idProduk: String = ""
    private var jumlah = 1
    private var harga = 0
    private var subtotal = 0
    private var ongkir = 5000
    private var metodePengambilan = "Delivery"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        supportActionBar?.hide()

        tvMenu = findViewById(R.id.tvMenu)
        tvHarga = findViewById(R.id.tvHarga)
        tvJumlah = findViewById(R.id.tvJumlah)
        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvDelivery = findViewById(R.id.tvDelivery)
        tvGrandTotal = findViewById(R.id.tvGrandTotal)
        btnCheckout = findViewById(R.id.btnCheckout)
        btnBack = findViewById(R.id.btnBack)
        imgCartItem = findViewById(R.id.imgCartItem)
        tvItemCount = findViewById(R.id.tvItemCount)
        
        optionDelivery = findViewById(R.id.optionDelivery)
        optionPickup = findViewById(R.id.optionPickup)
        containerAlamat = findViewById(R.id.containerAlamat)
        etAlamatPengiriman = findViewById(R.id.etAlamatPengiriman)

        idProduk = intent.getStringExtra("id_produk") ?: ""
        jumlah = intent.getIntExtra("jumlah", 1)

        btnBack.setOnClickListener { finish() }

        setupDeliveryOptions()

        if (idProduk.isEmpty() || idProduk == "null") {
            tampilkanFallbackEmpty()
        } else {
            loadCart()
        }
    }
    
    private fun setupDeliveryOptions() {
        optionDelivery.setOnClickListener {
            metodePengambilan = "Delivery"
            ongkir = 5000
            optionDelivery.setBackgroundResource(R.drawable.bg_white_card)
            optionPickup.setBackgroundColor(Color.parseColor("#F0E0B0"))
            containerAlamat.visibility = View.VISIBLE
            updateTotal()
        }

        optionPickup.setOnClickListener {
            metodePengambilan = "Pickup"
            ongkir = 0
            optionPickup.setBackgroundResource(R.drawable.bg_white_card)
            optionDelivery.setBackgroundColor(Color.parseColor("#F0E0B0"))
            containerAlamat.visibility = View.GONE
            updateTotal()
        }
    }

    private fun updateTotal() {
        val total = subtotal + ongkir
        tvDelivery.text = "Delivery Fee : Rp ${formatPrice(ongkir)}"
        tvGrandTotal.text = "Total : Rp ${formatPrice(total)}"
    }

    private fun tampilkanFallbackEmpty() {
        tvMenu.text = "Keranjang Kosong"
        tvHarga.text = "Rp 0"
        tvJumlah.text = "Jumlah : 0"
        tvSubtotal.text = "Subtotal : Rp 0"
        tvDelivery.text = "Delivery Fee : Rp 0"
        tvGrandTotal.text = "Total : Rp 0"
        tvItemCount.text = "0 items"
        btnCheckout.isEnabled = false
    }

    private fun loadCart() {
        ApiClient.instance.getProduct(idProduk)
            .enqueue(object : Callback<ProductDetailResponse> {
                override fun onResponse(call: Call<ProductDetailResponse>, response: Response<ProductDetailResponse>) {
                    if (response.isSuccessful) {
                        val product = response.body()?.data
                        if (product != null) {
                            harga = product.harga
                            subtotal = harga * jumlah
                            updateTotal()

                            tvMenu.text = product.namaProduk
                            tvHarga.text = "Rp ${formatPrice(harga)}"
                            tvJumlah.text = "Jumlah : $jumlah"
                            tvSubtotal.text = "Subtotal : Rp ${formatPrice(subtotal)}"
                            tvItemCount.text = "$jumlah items"

                            Glide.with(this@CartActivity)
                                .load("http://10.0.2.2/10kok_api/uploads/products/${product.gambar}")
                                .placeholder(R.mipmap.ic_launcher)
                                .into(imgCartItem)
                        }
                    }
                }

                override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable) {
                    Toast.makeText(this@CartActivity, "Gagal memuat keranjang", Toast.LENGTH_SHORT).show()
                }
            })

        btnCheckout.setOnClickListener {
            val alamat = etAlamatPengiriman.text.toString().trim()
            if (metodePengambilan == "Delivery" && alamat.isEmpty()) {
                Toast.makeText(this, "Alamat wajib diisi untuk Delivery", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, CheckoutActivity::class.java).apply {
                putExtra("id_produk", idProduk)
                putExtra("jumlah", jumlah)
                putExtra("subtotal", subtotal)
                putExtra("ongkir", ongkir)
                putExtra("total", subtotal + ongkir)
                putExtra("metode_pengambilan", metodePengambilan)
                putExtra("alamat", alamat)
            }
            startActivity(intent)
        }
    }

    private fun formatPrice(price: Int): String {
        return String.format("%,d", price).replace(',', '.')
    }
}
