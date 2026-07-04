package com.example.a10kok.response

import com.example.a10kok.model.Category
import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<Category>
)