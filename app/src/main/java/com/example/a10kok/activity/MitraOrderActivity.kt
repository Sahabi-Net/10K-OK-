package com.example.a10kok.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a10kok.R
import com.example.a10kok.adapter.OrderAdapter
import com.example.a10kok.api.ApiClient
import com.example.a10kok.response.BaseResponse
import com.example.a10kok.response.OrderResponse
import com.example.a10kok.session.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.View
import android.widget.TextView

class MitraOrderActivity : AppCompatActivity() {
    private lateinit var rvOrder: RecyclerView
    private lateinit var tvJumlahOrder: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mitra_order)

        supportActionBar?.hide()
        val btnBack = findViewById<View>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }
        rvOrder = findViewById(R.id.rvOrder)
        tvJumlahOrder = findViewById(R.id.tvJumlahOrder)
        rvOrder.layoutManager = LinearLayoutManager(this)

        loadOrder()
    }

    private fun loadOrder() {
        val idMitra = SessionManager(this).getIdUser()

        ApiClient.instance.getOrdersMitra(idMitra)
            .enqueue(object : Callback<OrderResponse> {
                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        val list = response.body()?.data ?: emptyList()
                        tvJumlahOrder.text = "${list.size} Pesanan"
                        rvOrder.adapter = OrderAdapter(list) { order, status ->
                            if (status == "Cancel") {
                                androidx.appcompat.app.AlertDialog.Builder(this@MitraOrderActivity)
                                    .setTitle("Konfirmasi Cancel")
                                    .setMessage("Apakah Anda yakin ingin membatalkan pesanan ini?")
                                    .setPositiveButton("Ya") { _, _ ->
                                        updateStatus(order.id_order, status)
                                    }
                                    .setNegativeButton("Tidak", null)
                                    .show()
                            } else {
                                updateStatus(order.id_order, status)
                            }
                        }
                    } else {
                        Toast.makeText(
                            this@MitraOrderActivity,
                            response.body()?.message ?: "Gagal memuat pesanan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onFailure(
                    call: Call<OrderResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        this@MitraOrderActivity,
                        t.message ?: "Gagal koneksi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
    
    private fun updateStatus(idOrder: String, status: String) {
        ApiClient.instance.updateStatus(idOrder, status)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        Toast.makeText(
                            this@MitraOrderActivity,
                            "Status berhasil diubah",
                            Toast.LENGTH_SHORT
                        ).show()
                        loadOrder()
                    } else {
                        Toast.makeText(
                            this@MitraOrderActivity,
                            response.body()?.message ?: "Gagal mengubah status",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    Toast.makeText(
                        this@MitraOrderActivity,
                        t.message ?: "Gagal koneksi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
