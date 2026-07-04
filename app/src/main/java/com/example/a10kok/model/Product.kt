package com.example.a10kok.model

import com.google.gson.annotations.SerializedName

data class Product(

    @SerializedName("id_produk")
    val idProduk: String,

    @SerializedName("nama_produk")
    val namaProduk: String,

    @SerializedName("deskripsi")
    val deskripsi: String,

    @SerializedName("harga")
    val harga: Int,

    @SerializedName("stok")
    val stok: Int,

    @SerializedName("gambar")
    val gambar: String,

    @SerializedName("rating")
    val rating: Float,

    @SerializedName("jumlah_terjual")
    val jumlahTerjual: Int,

    @SerializedName("status_produk")
    val statusProduk: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("id_mitra")
    val idMitra: String,

    @SerializedName("id_category")
    val idCategory: String,

    @SerializedName("nama_mitra")
    val namaMitra: String,

    @SerializedName("nama_category")
    val namaCategory: String

)