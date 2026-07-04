package com.example.a10kok.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a10kok.R
import com.example.a10kok.adapter.ProductAdapter
import com.example.a10kok.api.ApiClient
import com.example.a10kok.response.ProductResponse
import com.example.a10kok.session.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.app.AlertDialog
import com.example.a10kok.response.BaseResponse

class MitraDashboardActivity : AppCompatActivity() {

    private lateinit var btnTambah: Button
    private lateinit var btnPesanan: Button
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var tvTotalProduk: TextView
    private lateinit var tvTerjual: TextView
    private lateinit var tvNamaMitra: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mitra_dashboard)

        supportActionBar?.hide()

        btnTambah = findViewById(R.id.btnTambah)
        btnPesanan = findViewById(R.id.btnPesanan)
        recycler = findViewById(R.id.rvProdukMitra)
        tvTotalProduk = findViewById(R.id.tvTotalProduk)
        tvTerjual = findViewById(R.id.tvTerjual)
        tvNamaMitra = findViewById(R.id.tvNamaMitra)

        recycler.layoutManager = LinearLayoutManager(this)

        val session = SessionManager(this)
        tvNamaMitra.text = session.getNamaUser()

        val btnLogoutMitra = findViewById<android.widget.Button>(R.id.btnLogoutMitra)
        btnLogoutMitra.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Keluar")
                .setMessage("Apakah Anda yakin ingin keluar dari akun Mitra?")
                .setPositiveButton("Ya, Keluar") { _, _ ->
                    session.logout()
                    Toast.makeText(this, "Berhasil Logout", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }

        btnTambah.setOnClickListener {
            startActivity(Intent(this, TambahProdukActivity::class.java))
        }

        btnPesanan.setOnClickListener {
            startActivity(Intent(this, MitraOrderActivity::class.java))
        }

        loadProduk()
    }

    private fun hapusProduk(idProduk: String) {
        ApiClient.instance.deleteProduct(idProduk)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MitraDashboardActivity, "Product sudah dihapus", Toast.LENGTH_SHORT).show()
                        loadProduk()
                    } else {
                        Toast.makeText(this@MitraDashboardActivity, "Gagal menghapus produk", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    Toast.makeText(this@MitraDashboardActivity, "Koneksi Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onResume() {
        super.onResume()
        loadProduk()
    }

    private fun loadProduk() {
        val idMitra = SessionManager(this).getIdUser()

        ApiClient.instance.getProducts(idMitra)
            .enqueue(object : Callback<ProductResponse> {
                override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        val list = response.body()?.data ?: emptyList()

                        tvTotalProduk.text = list.size.toString()
                        tvTerjual.text = list.sumOf { it.jumlahTerjual }.toString()

                        adapter = ProductAdapter(
                            context = this@MitraDashboardActivity,
                            list = list,
                            layoutRes = R.layout.item_product_mitra,
                            onEdit = { product ->
                                val intent = Intent(this@MitraDashboardActivity, TambahProdukActivity::class.java)
                                intent.putExtra("edit", true)
                                intent.putExtra("id_produk", product.idProduk)
                                intent.putExtra("nama_produk", product.namaProduk)
                                intent.putExtra("harga", product.harga)
                                intent.putExtra("stok", product.stok)
                                intent.putExtra("deskripsi", product.deskripsi)
                                intent.putExtra("gambar", product.gambar)
                                intent.putExtra("id_category", product.idCategory)
                                startActivity(intent)
                            },
                            onDelete = { product ->
                                AlertDialog.Builder(this@MitraDashboardActivity)
                                    .setTitle("Hapus Produk")
                                    .setMessage("Yakin ingin menghapus ${product.namaProduk}?")
                                    .setPositiveButton("Ya") { _, _ ->
                                        hapusProduk(product.idProduk)
                                    }
                                    .setNegativeButton("Batal", null)
                                    .show()
                            }
                        )
                        recycler.adapter = adapter
                    } else {
                        Toast.makeText(this@MitraDashboardActivity, "Produk gagal dimuat", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    Toast.makeText(this@MitraDashboardActivity, "Koneksi Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
