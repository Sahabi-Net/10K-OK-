package com.example.a10kok.helper

object GenerateId {
    fun produk(): String {
        return "PRD" + System.currentTimeMillis()
    }
}