package com.example.agenteqr2.server

import com.example.agenteqr2.core.Config
import com.example.agenteqr2.server.models.TokenResponse
import io.ktor.client.call.body
import io.ktor.client.request.forms.*
import io.ktor.http.*

object AuthService {

    suspend fun exchangeAuthCodeForToken(authCode: String): String? {
        val response: TokenResponse = HttpClientProvider.httpClient.submitForm(
            url = Config.TOKEN_URL,
            formParameters = Parameters.build {
                append("client_id", Config.CLIENT_ID)
                append("client_secret", Config.CLIENT_SECRET)
                append("grant_type", "authorization_code")
                append("code", authCode)
                append("redirect_uri", Config.REDIRECT_URI)
            }
        ).body()

        return response.accessToken
    }
}