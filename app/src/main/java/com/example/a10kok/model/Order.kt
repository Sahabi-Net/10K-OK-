package com.example.a10kok.model

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("id_order")
    val id_order: String,
    @SerializedName("kode_invoice")
    val kode_invoice: String?,
    @SerializedName("nama")
    val nama: String?,
    @SerializedName("nama_produk")
    val nama_produk: String?,
    @SerializedName("total")
    val total: Int,
    @SerializedName("status")
    val status: String?,
    @SerializedName("created_at")
    val created_at: String?,
    @SerializedName("metode_pengambilan")
    val metode_pengambilan: String?,
    @SerializedName("qty")
    val qty: Int = 0,
    @SerializedName("subtotal")
    val subtotal: Int = 0,
    @SerializedName("alamat_pengiriman")
    val alamat_pengiriman: String? = null,
    @SerializedName("items")
    val items: List<OrderItem> = emptyList(),
    @SerializedName("payment")
    val payment: Payment? = null
)