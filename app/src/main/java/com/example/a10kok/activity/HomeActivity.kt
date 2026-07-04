package com.example.a10kok.activity

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a10kok.R
import com.example.a10kok.adapter.ProductAdapter
import com.example.a10kok.api.ApiClient
import com.example.a10kok.response.ProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.TextView
import com.example.a10kok.session.SessionManager

class HomeActivity : AppCompatActivity() {

    private lateinit var rvProduct: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var navCart: LinearLayout
    private lateinit var navHistory: LinearLayout
    private lateinit var navProfile: LinearLayout
    private lateinit var tvUser: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        rvProduct = findViewById(R.id.rvProduct)
        navCart = findViewById(R.id.navCart)
        navHistory = findViewById(R.id.navHistory)
        navProfile = findViewById(R.id.navProfile)
        tvUser = findViewById(R.id.tvUser)

        val session = SessionManager(this)
        tvUser.text = session.getNama().ifEmpty { "Admin" }

        rvProduct.layoutManager = GridLayoutManager(this, 2)

        loadProduct()

        navCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("id_produk", "")
            startActivity(intent)
        }

        navHistory.setOnClickListener {
            val intent = Intent(this, TrackingActivity::class.java)
            intent.putExtra("id_order", "")
            startActivity(intent)
        }

        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun loadProduct() {
        ApiClient.instance.getProducts()
            .enqueue(object : Callback<ProductResponse> {
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    if (response.isSuccessful) {
                        val list = response.body()?.data
                        if (list != null) {
                            adapter = ProductAdapter(
                                context = this@HomeActivity,
                                list = list,
                                layoutRes = R.layout.item_product,
                                onClick = { product ->
                                    val intent = Intent(this@HomeActivity, DetailMenuActivity::class.java)
                                    intent.putExtra("id_produk", product.idProduk)
                                    startActivity(intent)
                                }
                            )
                            rvProduct.adapter = adapter
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ProductResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        this@HomeActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}