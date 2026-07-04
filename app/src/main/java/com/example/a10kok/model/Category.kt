package com.example.a10kok.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id_category")
    val id_category: String,

    @SerializedName("nama_category")
    val nama_category: String
)