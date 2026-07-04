package com.example.a10kok.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a10kok.R
import com.example.a10kok.api.ApiClient
import com.example.a10kok.response.CheckoutData
import com.example.a10kok.response.CheckoutResponse
import com.example.a10kok.response.PaymentResponse
import com.example.a10kok.session.SessionManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {

    private lateinit var tvJumlahSummary: TextView
    private lateinit var tvHargaSummary: TextView
    private lateinit var tvOngkirValue: TextView
    private lateinit var tvTotalCheckout: TextView

    private lateinit var rgMetodePembayaran: RadioGroup
    private lateinit var btnKonfirmasi: Button

    private var idProduk: String = ""
    private var jumlah = 1
    private var subtotal = 0
    private var ongkir = 0
    private var total = 0
    private var metodePengambilan = ""
    private var alamat = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        supportActionBar?.hide()

        tvJumlahSummary = findViewById(R.id.tvJumlahSummary)
        tvHargaSummary = findViewById(R.id.tvHargaSummary)
        tvOngkirValue = findViewById(R.id.tvOngkirValue)
        tvTotalCheckout = findViewById(R.id.tvTotalCheckout)

        rgMetodePembayaran = findViewById(R.id.rgMetodePembayaran)
        btnKonfirmasi = findViewById(R.id.btnKonfirmasi)

        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        idProduk = intent.getStringExtra("id_produk") ?: ""
        jumlah = intent.getIntExtra("jumlah", 1)
        subtotal = intent.getIntExtra("subtotal", 0)
        ongkir = intent.getIntExtra("ongkir", 0)
        total = intent.getIntExtra("total", 0)
        metodePengambilan = intent.getStringExtra("metode_pengambilan") ?: "Pickup"
        alamat = intent.getStringExtra("alamat") ?: ""

        if (idProduk.isEmpty()) {
            Toast.makeText(this, "Produk tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupSummary()

        btnKonfirmasi.setOnClickListener {
            prosesCheckout()
        }

    }

    private fun setupSummary() {
        val rupiah = NumberFormat.getNumberInstance(Locale.forLanguageTag("id-ID"))

        tvJumlahSummary.text = "$jumlah Menu"
        tvHargaSummary.text = "Rp ${rupiah.format(subtotal)}"
        tvOngkirValue.text = "Rp ${rupiah.format(ongkir)}"
        tvTotalCheckout.text = "Rp ${rupiah.format(total)}"
    }

    private fun prosesCheckout() {
        val checkedRadioButtonId = rgMetodePembayaran.checkedRadioButtonId
        var uiPaymentString = "Tunai (COD)"
        
        if (checkedRadioButtonId != -1) {
            val selectedRb = findViewById<RadioButton>(checkedRadioButtonId)
            uiPaymentString = selectedRb.text.toString().split("\n")[0].trim()
        }

        val paymentMethod = when {
            uiPaymentString.contains("Tunai", ignoreCase = true) -> "Cash"
            uiPaymentString.contains("QRIS", ignoreCase = true) -> "QRIS"
            uiPaymentString.contains("Transfer", ignoreCase = true) -> "Transfer Bank"
            else -> "E-Wallet"
        }

        val session = SessionManager(this)
        val idUser = session.getIdUser()

        if (idUser.isEmpty()) {
            Toast.makeText(
                this,
                "Sesi login tidak ditemukan",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val items = Gson().toJson(
            listOf(
                mapOf(
                    "id_produk" to idProduk,
                    "qty" to jumlah
                )
            )
        )

        btnKonfirmasi.isEnabled = false
        btnKonfirmasi.text = "Memproses..."

        ApiClient.instance.checkout(
            idUser,
            metodePengambilan,
            alamat,
            "Pesanan dari aplikasi Android",
            items,
            paymentMethod
        ).enqueue(object : Callback<CheckoutResponse> {

            override fun onResponse(
                call: Call<CheckoutResponse>,
                response: Response<CheckoutResponse>
            ) {
                val body = response.body()

                if (response.isSuccessful && body?.success == true && body.data != null) {
                    prosesPayment(body.data, paymentMethod)
                } else {
                    btnKonfirmasi.isEnabled = true
                    btnKonfirmasi.text = "Bayar Sekarang"
                    Toast.makeText(
                        this@CheckoutActivity,
                        body?.message ?: "Checkout gagal",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(
                call: Call<CheckoutResponse>,
                t: Throwable
            ) {
                btnKonfirmasi.isEnabled = true
                btnKonfirmasi.text = "Bayar Sekarang"
                Toast.makeText(
                    this@CheckoutActivity,
                    t.message ?: "Checkout gagal",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

    }

    private fun prosesPayment(order: CheckoutData, paymentMethod: String) {

        ApiClient.instance.payment(
            order.id_order,
            paymentMethod
        ).enqueue(object : Callback<PaymentResponse> {

            override fun onResponse(
                call: Call<PaymentResponse>,
                response: Response<PaymentResponse>
            ) {
                val body = response.body()

                if (response.isSuccessful && body?.success == true) {
                    Toast.makeText(
                        this@CheckoutActivity,
                        "Pembayaran via $paymentMethod berhasil",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(
                        this@CheckoutActivity,
                        PaymentSuccessActivity::class.java
                    )
                    intent.putExtra("id_order", order.id_order)
                    intent.putExtra("kode_invoice", order.kode_invoice)
                    intent.putExtra("total", order.total)
                    intent.putExtra("status", "Diproses")
                    startActivity(intent)

                    finish()
                } else {
                    btnKonfirmasi.isEnabled = true
                    btnKonfirmasi.text = "Bayar Sekarang"
                    Toast.makeText(
                        this@CheckoutActivity,
                        body?.message ?: "Pembayaran gagal",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(
                call: Call<PaymentResponse>,
                t: Throwable
            ) {
                btnKonfirmasi.isEnabled = true
                btnKonfirmasi.text = "Bayar Sekarang"
                Toast.makeText(
                    this@CheckoutActivity,
                    t.message ?: "Pembayaran gagal",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

    }

}
