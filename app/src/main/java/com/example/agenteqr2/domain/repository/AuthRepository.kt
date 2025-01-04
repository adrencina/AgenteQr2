package com.example.agenteqr2.domain.repository

interface AuthRepository {
    suspend fun authenticate(clientId: String, clientSecret: String): String
}
