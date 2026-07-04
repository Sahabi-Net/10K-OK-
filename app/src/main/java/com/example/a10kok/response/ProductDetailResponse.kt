package com.example.a10kok.response

import com.example.a10kok.model.Product

data class ProductDetailResponse(
    val success: Boolean,
    val data: Product
)