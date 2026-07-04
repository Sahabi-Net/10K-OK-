package com.example.a10kok.model

import com.google.gson.annotations.SerializedName

data class OrderItem(
    @SerializedName("id_produk")
    val id_produk: String?,
    @SerializedName("nama_produk")
    val nama_produk: String?,
    @SerializedName("qty")
    val qty: Int,
    @SerializedName("subtotal")
    val subtotal: Int
)
