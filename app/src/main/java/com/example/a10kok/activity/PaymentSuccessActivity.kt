package com.example.a10kok.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.a10kok.R

class PaymentSuccessActivity : AppCompatActivity() {

    private lateinit var tvOrderInvoice: TextView
    private lateinit var btnLanjutTracking: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        supportActionBar?.hide()

        tvOrderInvoice = findViewById(R.id.tvOrderInvoice)
        btnLanjutTracking = findViewById(R.id.btnLanjutTracking)

        val invoice = intent.getStringExtra("kode_invoice") ?: ""
        if (invoice.isNotEmpty()) {
            tvOrderInvoice.text = "Orderan #$invoice"
        } else {
            val orderId = intent.getStringExtra("id_order") ?: ""
            tvOrderInvoice.text = "Orderan #$orderId"
        }

        btnLanjutTracking.setOnClickListener {
            val trackingIntent = Intent(
                this,
                TrackingActivity::class.java
            )
            trackingIntent.putExtra(
                "id_order",
                intent.getStringExtra("id_order") ?: ""
            )
            trackingIntent.putExtra(
                "kode_invoice",
                intent.getStringExtra("kode_invoice") ?: ""
            )
            trackingIntent.putExtra(
                "total",
                intent.getIntExtra("total", 0)
            )
            trackingIntent.putExtra(
                "status",
                intent.getStringExtra("status") ?: "Diproses"
            )

            startActivity(trackingIntent)
            finish()
        }
    }
}
