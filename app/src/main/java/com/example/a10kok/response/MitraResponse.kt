package com.example.a10kok.response

import com.example.a10kok.model.Mitra

data class MitraResponse(
    val success:Boolean,
    val data:List<Mitra>
)