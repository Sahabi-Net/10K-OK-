package com.example.a10kok.response

import com.google.gson.annotations.SerializedName

data class CheckoutResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: CheckoutData?
)

data class CheckoutData(
    @SerializedName("id_order")
    val id_order: String,
    @SerializedName("kode_invoice")
    val kode_invoice: String,
    @SerializedName("total")
    val total: Int
)
