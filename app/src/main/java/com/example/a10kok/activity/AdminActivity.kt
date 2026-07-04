package com.example.a10kok.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a10kok.R
import com.example.a10kok.adapter.MitraAdapter
import com.example.a10kok.api.ApiClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.a10kok.model.Mitra
import com.example.a10kok.response.BaseResponse
import com.example.a10kok.response.MitraResponse
import com.example.a10kok.response.ProductResponse
import com.example.a10kok.session.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var tvTotalMitra: TextView
    private lateinit var tvTotalProduk: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        supportActionBar?.hide()

        rv = findViewById(R.id.rvMitra)
        rv.layoutManager = LinearLayoutManager(this)

        tvTotalMitra = findViewById(R.id.tvTotalMitra)
        tvTotalProduk = findViewById(R.id.tvTotalProduk)

        val btnLogout = findViewById<AppCompatButton>(R.id.btnLogoutAdmin)
        btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Keluar")
                .setMessage("Apakah Anda yakin ingin keluar dari akun Admin?")
                .setPositiveButton("Ya, Keluar") { _, _ ->
                    SessionManager(this).logout()
                    Toast.makeText(this, "Berhasil Logout", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }

        val fabAddMitra = findViewById<FloatingActionButton>(R.id.fabAddMitra)
        fabAddMitra.setOnClickListener {
            showAddMitraDialog()
        }

        loadMitra()
        loadTotalProduk()
    }

    private fun showAddMitraDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_add_mitra, null)
        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(true)
            .create()

        val etNama = view.findViewById<EditText>(R.id.etNama)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val etNoHp = view.findViewById<EditText>(R.id.etNoHp)
        val etAlamat = view.findViewById<EditText>(R.id.etAlamat)
        val btnBatal = view.findViewById<Button>(R.id.btnBatal)
        val btnSimpan = view.findViewById<Button>(R.id.btnSimpan)

        btnBatal.setOnClickListener { dialog.dismiss() }
        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val noHp = etNoHp.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()

            if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Nama, Email, dan Password wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnSimpan.isEnabled = false
            ApiClient.instance.registerMitra(nama, email, password, noHp, alamat)
                .enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                        btnSimpan.isEnabled = true
                        if (response.isSuccessful && response.body()?.success == true) {
                            Toast.makeText(this@AdminActivity, "Mitra berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                            loadMitra()
                        } else {
                            Toast.makeText(this@AdminActivity, response.body()?.message ?: "Gagal menambah mitra", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        btnSimpan.isEnabled = true
                        Toast.makeText(this@AdminActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
        dialog.show()
    }

    private fun loadMitra() {
        ApiClient.instance.getMitra()
            .enqueue(object : Callback<MitraResponse> {
                override fun onResponse(call: Call<MitraResponse>, response: Response<MitraResponse>) {
                    if (response.isSuccessful && response.body()?.data != null) {
                        val mitraList = response.body()!!.data
                        tvTotalMitra.text = mitraList.size.toString()
                        rv.adapter = MitraAdapter(mitraList) { mitra, status ->
                            updateStatus(mitra, status)
                        }
                    }
                }
                override fun onFailure(call: Call<MitraResponse>, t: Throwable) {
                    Toast.makeText(this@AdminActivity, "Gagal koneksi", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun loadTotalProduk() {
        ApiClient.instance.getProducts()
            .enqueue(object : Callback<ProductResponse> {
                override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        tvTotalProduk.text = response.body()!!.data.size.toString()
                    }
                }
                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {}
            })
    }

    private fun updateStatus(mitra: Mitra, status: String) {
        ApiClient.instance.updateStatusMitra(mitra.id_user, status)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    Toast.makeText(this@AdminActivity, "Status berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    loadMitra()
                }
                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    Toast.makeText(this@AdminActivity, "Gagal update status", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
