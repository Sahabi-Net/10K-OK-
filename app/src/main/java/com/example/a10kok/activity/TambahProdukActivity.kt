package com.example.a10kok.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.a10kok.R
import com.example.a10kok.api.ApiClient
import com.example.a10kok.model.Category
import com.example.a10kok.response.BaseResponse
import com.example.a10kok.response.CategoryResponse
import com.example.a10kok.session.SessionManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import com.example.a10kok.response.UploadResponse

class TambahProdukActivity : AppCompatActivity() {
    private var kategoriList: List<Category> = emptyList()
    private var imageUri: Uri? = null
    private var namaFileGambar = ""

    private var isEdit = false
    private var editIdProduk = ""
    private var editIdCategory = ""

    private lateinit var imgPreview: ImageView
    private lateinit var spinner: Spinner
    private lateinit var btnSimpan: Button
    private lateinit var btnGambar: Button
    private lateinit var etNama: TextInputEditText
    private lateinit var etHarga: TextInputEditText
    private lateinit var etStok: TextInputEditText
    private lateinit var etDeskripsi: TextInputEditText
    private lateinit var tvTitle: TextView
    private lateinit var tvSubtitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_produk)

        supportActionBar?.hide()

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        imgPreview = findViewById(R.id.imgPreview)
        spinner = findViewById(R.id.spKategori)
        btnSimpan = findViewById(R.id.btnSimpan)
        btnGambar = findViewById(R.id.btnPilihGambar)
        etNama = findViewById(R.id.etNama)
        etHarga = findViewById(R.id.etHarga)
        etStok = findViewById(R.id.etStok)
        etDeskripsi = findViewById(R.id.etDeskripsi)
        tvTitle = findViewById(R.id.tvTitle)
        tvSubtitle = findViewById(R.id.tvSubtitle)

        isEdit = intent.getBooleanExtra("edit", false)

        if (isEdit) {
            tvTitle.text = "Edit Produk"
            tvSubtitle.text = "Perbarui informasi produk Anda"
            btnSimpan.text = "Update Produk"
            
            editIdProduk = intent.getStringExtra("id_produk") ?: ""
            editIdCategory = intent.getStringExtra("id_category") ?: ""
            etNama.setText(intent.getStringExtra("nama_produk"))
            etHarga.setText(intent.getStringExtra("harga"))
            etStok.setText(intent.getStringExtra("stok"))
            etDeskripsi.setText(intent.getStringExtra("deskripsi"))
            
            val gambar = intent.getStringExtra("gambar")
            if (!gambar.isNullOrEmpty()) {
                Glide.with(this)
                    .load("http://10.0.2.2/10kok_api/uploads/products/$gambar")
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imgPreview)
            }
        }

        loadKategori()

        btnGambar.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        btnSimpan.setOnClickListener {
            uploadProduk()
        }
    }

    private fun loadKategori() {
        ApiClient.instance.getCategory()
            .enqueue(object : Callback<CategoryResponse> {

                override fun onResponse(
                    call: Call<CategoryResponse>,
                    response: Response<CategoryResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body?.success == true) {
                            val list = body.data ?: emptyList()
                            kategoriList = list

                            val namaKategori = list.map { it.nama_category }
                            val adapter = ArrayAdapter(
                                this@TambahProdukActivity,
                                android.R.layout.simple_spinner_item,
                                namaKategori
                            )
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            spinner.adapter = adapter
                            
                            // Set selection if edit mode
                            if (isEdit && editIdCategory.isNotEmpty()) {
                                val position = kategoriList.indexOfFirst { it.id_category == editIdCategory }
                                if (position >= 0) {
                                    spinner.setSelection(position)
                                }
                            }

                        } else {
                            val msg = body?.message ?: "Format data PHP salah"
                            Toast.makeText(this@TambahProdukActivity, "Gagal: $msg", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this@TambahProdukActivity, "Server Error Code: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                    Toast.makeText(
                        this@TambahProdukActivity,
                        "Koneksi Gagal: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun uploadGambar(uri: Uri) {

        val file = File(uri.path ?: return)

        val requestFile =
            file.asRequestBody("image/*".toMediaTypeOrNull())

        val body =
            MultipartBody.Part.createFormData(
                "gambar",
                file.name,
                requestFile
            )

        ApiClient.instance.uploadImage(body)
            .enqueue(object : Callback<UploadResponse> {

                override fun onResponse(
                    call: Call<UploadResponse>,
                    response: Response<UploadResponse>
                ) {

                    if(response.isSuccessful){

                        namaFileGambar =
                            response.body()?.data?.filename ?: ""

                        Toast.makeText(
                            this@TambahProdukActivity,
                            "Upload gambar berhasil",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }

                override fun onFailure(
                    call: Call<UploadResponse>,
                    t: Throwable
                ) {

                    Toast.makeText(
                        this@TambahProdukActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()

                }

            })

    }

    private fun uploadProduk() {
        val nama = etNama.text.toString().trim()
        val harga = etHarga.text.toString().trim()
        val stok = etStok.text.toString().trim()
        val deskripsi = etDeskripsi.text.toString().trim()

        if (nama.isEmpty() || harga.isEmpty()) {
            Toast.makeText(this, "Nama dan harga wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }
        if (kategoriList.isEmpty()) {
            Toast.makeText(this, "Kategori belum dimuat", Toast.LENGTH_SHORT).show()
            return
        }

        val idMitra = SessionManager(this).getIdUser()
        val kategori = kategoriList[spinner.selectedItemPosition]

        val callback = object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(
                        this@TambahProdukActivity,
                        if (isEdit) "Produk berhasil diupdate" else "Produk berhasil ditambahkan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@TambahProdukActivity,
                        response.body()?.message ?: "Gagal memproses produk",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Toast.makeText(
                    this@TambahProdukActivity,
                    t.message ?: "Gagal koneksi",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        if (isEdit) {
            ApiClient.instance.updateProduct(
                editIdProduk,
                kategori.id_category,
                nama,
                deskripsi,
                harga,
                stok,
                namaFileGambar
            ).enqueue(callback)
        } else {
            val idProduk = System.currentTimeMillis().toString()
            ApiClient.instance.tambahProduk(
                idProduk,
                idMitra,
                kategori.id_category,
                nama,
                deskripsi,
                harga,
                stok,
                namaFileGambar
            ).enqueue(callback)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode:Int,
        resultCode:Int,
        data:Intent?
    ){
        super.onActivityResult(requestCode,resultCode,data)

        if(resultCode==RESULT_OK){

            imageUri=data?.data

            imgPreview.setImageURI(imageUri)

            imageUri?.let{

                uploadGambar(it)

            }

        }

    }
}