package com.example.a10kok.response

import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String
)
