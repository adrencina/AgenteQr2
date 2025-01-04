package com.example.agenteqr2.data.repository

import com.example.agenteqr2.data.remote.ApiService
import com.example.agenteqr2.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {

    override suspend fun authenticate(clientId: String, clientSecret: String): String {
        // Construir el cuerpo de la solicitud para obtener el token
        val response = apiService.getAccessToken(
            clientId = clientId,
            clientSecret = clientSecret,
            grantType = "client_credentials"
        )

        if (response.isSuccessful) {
            return response.body()?.accessToken ?: throw Exception("Token vacío")
        } else {
            throw Exception("Error: ${response.message()}")
        }
    }
}
