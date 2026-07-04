package com.example.a10kok.model

import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("status_pembayaran")
    val status_pembayaran: String?,
    @SerializedName("metode_pembayaran")
    val metode_pembayaran: String?
)
