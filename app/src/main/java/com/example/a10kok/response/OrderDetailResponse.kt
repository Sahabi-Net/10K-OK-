package com.example.a10kok.response

import com.example.a10kok.model.Order
import com.google.gson.annotations.SerializedName

data class OrderDetailResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: Order?
)
