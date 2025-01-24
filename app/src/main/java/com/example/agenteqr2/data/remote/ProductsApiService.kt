package com.example.agenteqr2.data.remote

import com.example.agenteqr2.data.remote.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ProductsApiService {
    @GET("v1/{user_id}/products")
    suspend fun getProducts(
        @Path("user_id") userId: String,
        @Header("Authorization") authHeader: String
    ): Response<List<Product>>
}