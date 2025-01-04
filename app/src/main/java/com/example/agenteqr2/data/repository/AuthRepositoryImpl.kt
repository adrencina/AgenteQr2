package com.example.agenteqr2.data.repository

import com.example.agenteqr2.core.Config
import com.example.agenteqr2.data.remote.ApiService
import com.example.agenteqr2.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {

    suspend fun exchangeAuthCodeForToken(authCode: String): String {
        val clientId = Config.CLIENT_ID
        val clientSecret = Config.CLIENT_SECRET
        val redirectUri = Config.REDIRECT_URI

        val response = apiService.exchangeAuthCode(
            clientId = clientId,
            clientSecret = clientSecret,
            redirectUri = redirectUri,
            code = authCode
        )

        if (response.isSuccessful) {
            return response.body()?.accessToken ?: throw Exception("Token vacío")
        } else {
            throw Exception("Error: ${response.message()}")
        }
    }
}