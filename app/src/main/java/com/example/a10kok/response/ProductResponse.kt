package com.example.a10kok.response

import com.example.a10kok.model.Product

data class ProductResponse(
    val success: Boolean,
    val data: List<Product>
)