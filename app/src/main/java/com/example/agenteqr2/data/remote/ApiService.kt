package com.example.agenteqr2.data.remote

import com.example.agenteqr2.data.remote.model.AuthResponse
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Field

interface ApiService {

    @FormUrlEncoded
    @POST("/oauth/token")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String
    ): Response<AuthResponse>
}
