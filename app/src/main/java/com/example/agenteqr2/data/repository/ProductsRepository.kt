package com.example.agenteqr2.data.repository

import com.example.agenteqr2.data.remote.ProductsApiService
import com.example.agenteqr2.data.remote.model.Product
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsApiService: ProductsApiService
) {
    suspend fun getProducts(userId: String, accessToken: String): List<Product> {
        val response = productsApiService.getProducts(
            userId = userId,
            authHeader = "Bearer $accessToken"
        )

        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error al obtener los productos: ${response.errorBody()?.string()}")
        }
    }
}