package com.example.a10kok.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.a10kok.R
import com.example.a10kok.api.ApiClient
import com.example.a10kok.model.Order
import com.example.a10kok.response.BaseResponse
import com.example.a10kok.response.OrderDetailResponse
import com.google.android.material.card.MaterialCardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrackingActivity : AppCompatActivity() {

    private lateinit var tvOrderInvoice: TextView
    private lateinit var tvOrderStatus: TextView
    private lateinit var tvOrderTime: TextView
    
    private lateinit var icStep1: ImageView
    private lateinit var line1: View
    private lateinit var icStep2: ImageView
    private lateinit var line2: View
    private lateinit var icStep3: ImageView
    private lateinit var line3: View
    private lateinit var icStep4: ImageView
    
    private lateinit var tvTime1: TextView
    private lateinit var tvTime2: TextView
    private lateinit var tvTime3: TextView
    private lateinit var tvTime4: TextView
    
    private lateinit var cardCelebration: MaterialCardView
    private lateinit var btnReview: AppCompatButton
    private lateinit var btnHome: AppCompatButton
    
    private lateinit var btnBack: ImageButton

    private var currentOrder: Order? = null
    private var orderId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking)
        supportActionBar?.hide()

        tvOrderInvoice = findViewById(R.id.tvOrderInvoice)
        tvOrderStatus = findViewById(R.id.tvOrderStatus)
        tvOrderTime = findViewById(R.id.tvOrderTime)
        
        icStep1 = findViewById(R.id.icStep1)
        line1 = findViewById(R.id.line1)
        icStep2 = findViewById(R.id.icStep2)
        line2 = findViewById(R.id.line2)
        icStep3 = findViewById(R.id.icStep3)
        line3 = findViewById(R.id.line3)
        icStep4 = findViewById(R.id.icStep4)
        
        tvTime1 = findViewById(R.id.tvTime1)
        tvTime2 = findViewById(R.id.tvTime2)
        tvTime3 = findViewById(R.id.tvTime3)
        tvTime4 = findViewById(R.id.tvTime4)
        
        cardCelebration = findViewById(R.id.cardCelebration)
        btnReview = findViewById(R.id.btnReview)
        btnHome = findViewById(R.id.btnHome)

        btnBack = findViewById(R.id.btnBack)

        orderId = intent.getStringExtra("id_order") ?: ""

        btnBack.setOnClickListener { finish() }

        btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        btnReview.setOnClickListener {
            val order = currentOrder
            if (order != null && order.items.isNotEmpty()) {
                val intent = Intent(this, ReviewActivity::class.java)
                intent.putExtra("id_order", order.id_order)
                intent.putExtra("id_produk", order.items[0].id_produk)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Review belum tersedia", Toast.LENGTH_SHORT).show()
            }
        }
        
        if (orderId.isEmpty()) {
            tampilkanFallback()
        } else {
            loadOrder(orderId)
        }
    }

    private fun loadOrder(orderId: String) {
        ApiClient.instance.getOrderDetail(orderId)
            .enqueue(object : Callback<OrderDetailResponse> {
                override fun onResponse(call: Call<OrderDetailResponse>, response: Response<OrderDetailResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        currentOrder = response.body()?.data
                        currentOrder?.let { tampilkanOrder(it) }
                    } else {
                        tampilkanFallback()
                    }
                }

                override fun onFailure(call: Call<OrderDetailResponse>, t: Throwable) {
                    Toast.makeText(this@TrackingActivity, "Gagal memuat status", Toast.LENGTH_SHORT).show()
                    tampilkanFallback()
                }
            })
    }

    private fun tampilkanOrder(order: Order) {
        tvOrderInvoice.text = "#10K-${order.kode_invoice ?: order.id_order}"
        tvOrderStatus.text = order.status ?: "Diproses"
        
        val timeStr = order.created_at?.split(" ")?.lastOrNull()?.substringBeforeLast(":") ?: "--:--"
        tvOrderTime.text = timeStr

        val cal = java.util.Calendar.getInstance()
        val sdf = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
        
        tvTime1.text = sdf.format(cal.time)
        cal.add(java.util.Calendar.MINUTE, 5)
        tvTime2.text = sdf.format(cal.time)
        cal.add(java.util.Calendar.MINUTE, 15)
        tvTime3.text = sdf.format(cal.time)
        cal.add(java.util.Calendar.MINUTE, 25)
        tvTime4.text = sdf.format(cal.time)
        
        // Use time1 for the top order time
        tvOrderTime.text = tvTime1.text
        
        updateTimeline(order.status ?: "")
    }

    private fun updateTimeline(status: String) {
        val colorGrey = Color.parseColor("#D0D0D0")
        val colorGreen = Color.parseColor("#6D9227")
        val colorRed = Color.parseColor("#F44336")

        icStep1.setImageResource(R.drawable.ic_circle_hexagon_grey)
        line1.setBackgroundColor(colorGrey)
        icStep2.setImageResource(R.drawable.ic_circle_hexagon_grey)
        line2.setBackgroundColor(colorGrey)
        icStep3.setImageResource(R.drawable.ic_circle_hexagon_grey)
        line3.setBackgroundColor(colorGrey)
        icStep4.setImageResource(R.drawable.ic_circle_hexagon_grey)
        cardCelebration.visibility = View.VISIBLE
        tvOrderStatus.setTextColor(Color.parseColor("#E65100"))

        when (status) {
            "Diterima", "Diproses" -> {
                icStep1.setImageResource(R.drawable.ic_check_circle_green)
                if (status == "Diproses") {
                    line1.setBackgroundColor(colorGreen)
                    icStep2.setImageResource(R.drawable.ic_check_circle_green)
                }
            }
            "Dikirim", "Siap Diambil", "Dalam Pengantaran" -> {
                icStep1.setImageResource(R.drawable.ic_check_circle_green)
                line1.setBackgroundColor(colorGreen)
                icStep2.setImageResource(R.drawable.ic_check_circle_green)
                line2.setBackgroundColor(colorGreen)
                icStep3.setImageResource(R.drawable.ic_check_circle_green)
            }
            "Selesai" -> {
                icStep1.setImageResource(R.drawable.ic_check_circle_green)
                line1.setBackgroundColor(colorGreen)
                icStep2.setImageResource(R.drawable.ic_check_circle_green)
                line2.setBackgroundColor(colorGreen)
                icStep3.setImageResource(R.drawable.ic_check_circle_green)
                line3.setBackgroundColor(colorGreen)
                icStep4.setImageResource(R.drawable.ic_check_circle_green)
                
                tvOrderStatus.setTextColor(colorGreen)
            }
            "Cancel", "Dibatalkan" -> {
                tvOrderStatus.text = "Dibatalkan"
                tvOrderStatus.setTextColor(colorRed)
            }
            else -> {
            }
        }
    }
    private fun tampilkanFallback() {
        tvOrderInvoice.text = "Belum Ada Pesanan"
        tvOrderStatus.text = "-"
        tvOrderTime.text = "--:--"
        updateTimeline("")
    }
}
