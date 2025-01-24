package com.example.agenteqr2.data.remote.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("price") val price: Double,
    @SerializedName("stock") val stock: Int
)