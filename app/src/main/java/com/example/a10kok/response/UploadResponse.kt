package com.example.a10kok.response

data class UploadResponse(
    val success:Boolean,
    val message:String,
    val data:UploadData
)

data class UploadData(
    val filename:String
)